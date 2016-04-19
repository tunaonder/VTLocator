/*
 * Created by Sait Tuna Onder on 2016.04.07  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.managers;


import com.vtlocator.jpaentityclasses.Building;
import com.vtlocator.sessionbeans.BuildingFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

    //This facade is used to connect Controller to the database
    @EJB
    private com.vtlocator.sessionbeans.BuildingFacade ejbFacade;
    private List<Building> items = null;
    private String selectedBuildingName;
    private String selectedStartPoint;
    
    private List<String> buildingNamesJSON;

    //Building Names Hash Map to Display at Building Menu
    private HashMap<String, String> buildingNames;

//    private MapModel mapModel;

    private String title;

    //Building Values
    private double lat;
    private double lng;
    private String imageUrl;
    private String description;
    
    //Second Building Values
    private double lat2;
    private double lng2;

    //Center Of The Map
    private double vtLat = 37.227264;
    private double vtLong = -80.420745;

    private Building tempBuilding;

    private boolean directionPressed;


    @PostConstruct
    public void init() {
     
        items = getItems();

        //Sizes are in a  Hash Map
        buildingNames = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            buildingNames.put(items.get(i).getName(), items.get(i).getName());
        }
        buildingNames = sortByValues(buildingNames);

        selectedStartPoint = "";
//        mapModel = new DefaultMapModel();
        imageUrl = "https://lh5.googleusercontent.com/-A6mD3SlNkSM/AAAAAAAAAAI/AAAAAAAAAXE/yyIqI5mrcOk/s0-c-k-no-ns/photo.jpg";
        description = "";
        selectedBuildingName = "";
        directionPressed = false;
        lat = 0;
        lng = 0;
        lat2 = 0;
        lng2 = 0;
        
//         arr = new JSONArray();
        JSONArray json;
        buildingNamesJSON = new ArrayList<String>();
        try {
            json = new JSONArray(readJSON());
            int counter = 0;
            while (json != null && json.length() > counter) {
                buildingNamesJSON.add(json.getJSONObject(counter).get("name").toString());
                counter++;
            }
//            arr.put(buildingNamesJSON);
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<String> getBuildingNamesJSON() {
        return buildingNamesJSON;
    }

    public void setBuildingNamesJSON(List<String> buildingNamesJSON) {
        this.buildingNamesJSON = buildingNamesJSON;
    }

    
    
    
    
    
    public String readJSON() throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL("http://localhost:8080/VTBuildingsData/webresources/buildings/names");
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            
            int read;
            char[] chars = new char[10240];
            while( (read = reader.read(chars)) != -1 ) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    

    //Calls companyFacade and gets items from the database
    public List<Building> getItems(){
        if (items == null) {
            items = getFacade().findAll();
        }


        return items;
    }

    private BuildingFacade getFacade() {
        return ejbFacade;
    }

    public String getSelectedBuildingName() {
        return selectedBuildingName;
    }

    public void setSelectedBuildingName(String selectedBuildingName) {
        this.selectedBuildingName = selectedBuildingName;
    }

    public HashMap<String, String> getBuildingNames() {
        return buildingNames;
    }

    public void setBuildingNames(HashMap<String, String> buildingNames) {
        this.buildingNames = buildingNames;
    }

//    public MapModel getMapModel() {
//        return mapModel;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

//    public void addMarker() {
//        Marker marker = new Marker(new LatLng(lat, lng), title);
//        mapModel.addOverlay(marker);
//
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
//    }

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

    public Building getTempBuilding() {
        return tempBuilding;
    }

    public void setTempBuilding(Building tempBuilding) {
        this.tempBuilding = tempBuilding;
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
    
    
    
    

    //Sort Buildings Alphabetacially
    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, (Object o1, Object o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
                .compareTo(((Map.Entry) (o2)).getValue()));

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public void displayBuildingLocation() throws IOException{
        //Find the building object with its name
        tempBuilding = getBuildingWithName(selectedBuildingName);
        //Get the latitude and longitude of the selected building
        lat = tempBuilding.getLatitude().doubleValue();
        lng = tempBuilding.getLongitude().doubleValue();
        imageUrl = tempBuilding.getImage();

        URL url = new URL(tempBuilding.getDescription());
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String str;
        while ((str = in.readLine()) != null) {
            description = str;
        }
        in.close();
        
        
    }

    private Building getBuildingWithName(String buildingName) {
        for (int i = 0; i < items.size(); i++) {
            if (buildingName.equals(items.get(i).getName())) {
                return items.get(i);
            }
        }

        return null;

    }

    public void directionButtonPressed() {
        System.out.print(directionPressed);
        directionPressed = true;

    }
    
    public void createStartPoint(){
        tempBuilding = getBuildingWithName(selectedStartPoint);
        lat2 = tempBuilding.getLatitude().doubleValue();
        lng2 = tempBuilding.getLongitude().doubleValue();
        System.out.print(tempBuilding.getName());
        
    }
    
    public void refreshForm(){
        directionPressed = false;
        
    }

}
