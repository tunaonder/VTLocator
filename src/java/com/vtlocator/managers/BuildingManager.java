/*
 * Created by Sait Tuna Onder on 2016.04.07  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Onder
 */
@ManagedBean
@SessionScoped
@Named(value = "buildingManager")
public class BuildingManager implements Serializable {

    //Selected Building to display Building Information
    private String selectedBuildingName;
    //Selected Building to get direction from. Route will be from here to selectedBuildingName
    private String selectedStartPoint;

    //List of All Buildings Names From Restful Service
    private List<String> buildingNamesJSON;
    
    //Restful Requests Base URL
    private final String baseUrl = "http://localhost:8080/VTBuildingsData/webresources/buildings/";

    //Building Values
    private double lat;
    private double lng;
    private String imageUrl;
    private String description;

    //Second Building Values
    private double lat2;
    private double lng2;

    //Center Of The Map (Virginia Tech Coordinates)
    private double vtLat = 37.227264;
    private double vtLong = -80.420745;


    private boolean directionPressed;

    @PostConstruct
    public void init() {

        //Virginia Tech Logo is the Default Image
        imageUrl = "https://lh5.googleusercontent.com/-A6mD3SlNkSM/AAAAAAAAAAI/AAAAAAAAAXE/yyIqI5mrcOk/s0-c-k-no-ns/photo.jpg";
        
        selectedBuildingName = "";
        selectedStartPoint = "";

        
        lat = 0;
        lng = 0;
        lat2 = 0;
        lng2 = 0;
        description = "";
        directionPressed = false;

        
        JSONArray json;
        buildingNamesJSON = new ArrayList<>();
        try {
            //Get Names Of all Buildings from Restful Service
            String url = baseUrl+"names";
            json = new JSONArray(readJSON(url));
            
            int counter = 0;
            //Get Names of Buildings From JSON Data and make a building names List
            while (json != null && json.length() > counter) {
                buildingNamesJSON.add(json.getJSONObject(counter).get("name").toString());
                counter++;
            }
        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //This method Returns JSON data as String from Given URL
        public String readJSON(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();

            int read;
            char[] chars = new char[10240];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public List<String> getBuildingNamesJSON() {
        return buildingNamesJSON;
    }

    public void setBuildingNamesJSON(List<String> buildingNamesJSON) {
        this.buildingNamesJSON = buildingNamesJSON;
    }



    public String getSelectedBuildingName() {
        return selectedBuildingName;
    }

    public void setSelectedBuildingName(String selectedBuildingName) {
        this.selectedBuildingName = selectedBuildingName;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getVtLat() {
        return vtLat;
    }

    public void setVtLat(double vtLat) {
        this.vtLat = vtLat;
    }

    public double getVtLong() {
        return vtLong;
    }

    public void setVtLong(double vtLong) {
        this.vtLong = vtLong;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDirectionPressed() {
        return directionPressed;
    }

    public void setDirectionPressed(boolean directionPressed) {
        this.directionPressed = directionPressed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectedStartPoint() {
        return selectedStartPoint;
    }

    public void setSelectedStartPoint(String selectedStartPoint) {
        this.selectedStartPoint = selectedStartPoint;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLng2() {
        return lng2;
    }

    public void setLng2(double lng2) {
        this.lng2 = lng2;
    }


    //Gets All Data From Selected Json Object and Parse it. Therefore, view can be updated with the information of selected building
    public void displayBuildingInformation() throws IOException {

        JSONObject json;
        try {
            //Replace Space with %20
            String modifiedBuildingName = selectedBuildingName.replaceAll(" ", "%20");
            //Replace Slah character with %2F
            modifiedBuildingName = modifiedBuildingName.replace("/", "%2F");
            //Create Restful Request Url
            String restfulUrl = baseUrl+modifiedBuildingName;
            //Return Result As JSON
            json = new JSONObject(readJSON(restfulUrl));
            //Get Specific Data from Returned Json
            lat = json.getDouble("latitude");
            lng = json.getDouble("longitude");
            imageUrl = json.getString("image");
            //Url Has a description as txt. Read all String from Url and add it to description Variable
            URL url = new URL(json.getString("description"));
            try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String str;
                while ((str = in.readLine()) != null) {
                    description = str;
                }
            }

        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void directionButtonPressed() {
        System.out.print(directionPressed);
        directionPressed = true;

    }

    
    //This Methods sets the Coordinates of starting point for routes
    public void createStartPoint() {

        JSONObject json;
        try {
            //URL REPLACEMENTS
             //Replace Space with %20
            String modifiedBuildingName = selectedStartPoint.replaceAll(" ", "%20");
            //Replace Slah character with %2F
            modifiedBuildingName = modifiedBuildingName.replace("/", "%2F");
            //Create Restful Request Url
            String restfulUrl = baseUrl+modifiedBuildingName;
            json = new JSONObject(readJSON(restfulUrl));
            lat2 = json.getDouble("latitude");
            lng2 = json.getDouble("longitude");
        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refreshForm() {
        directionPressed = false;

    }

}
