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
import javax.faces.context.FacesContext;
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
    private final String baseUrl = "http://jupiter.cs.vt.edu/VTBuildingsData/webresources/buildings/";

    //Building Values
    private double lat;
    private double lng;
    private String imageUrl;
    private String description;

    //Second Building Values to get Direction
    private double lat2;
    private double lng2;

    //Check if GET DIRECTION button pressed. This is used to render page when button is clicked
    private boolean directionPressed;

    //Selected building category from building category list
    private String buildingCategory;
    //List of All categories from Restul service
    private List<String> buildingCategoriesJSON;

    //Name of the selected category
    private String jsonCategory;

    //Direction Button
    private boolean directionAvailable;
    private boolean categoryAvailable;

    @PostConstruct
    public void init() {
        
        //Initilize All Variables
        setInitialValues();

        //-----------------Send get request for building names list------------------
        //Read all building names from  ../VTBuildingsData/webresources/buildings/names
        JSONArray json;
        buildingNamesJSON = new ArrayList<>();
        try {
            //Get Names Of all Buildings from Restful Service
            String url = baseUrl + "names";
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
        //--------------------------------------------------------------------------

        //-----------------Send get request for building categories list-----------------------
        //Read all building categories from  ../VTBuildingsData/webresources/buildings/categories
        buildingCategoriesJSON = new ArrayList<>();
        try {
            //Get Names Of all Buildings from Restful Service
            String url = baseUrl + "categories";
            json = new JSONArray(readJSON(url));

            int counter = 0;
            //Get Categories of Buildings From JSON Data and make a building names List
            while (json != null && json.length() > counter) {
                buildingCategoriesJSON.add(json.getJSONObject(counter).get("category").toString());
                counter++;
            }
        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setInitialValues() {

        imageUrl = null;
        categoryAvailable = false;
        directionAvailable = false;
        selectedBuildingName = "";
        selectedStartPoint = "";
        buildingCategory = "";
        jsonCategory = "";
        lat = 0;
        lng = 0;
        lat2 = 0;
        lng2 = 0;
        description = "";
        directionPressed = false;
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

    public String getBuildingCategory() {
        return buildingCategory;
    }

    public void setBuildingCategory(String buildingCategory) {
        this.buildingCategory = buildingCategory;
    }

    public List<String> getBuildingCategoriesJSON() {
        return buildingCategoriesJSON;
    }

    public void setBuildingCategoriesJSON(List<String> buildingCategoriesJSON) {
        this.buildingCategoriesJSON = buildingCategoriesJSON;
    }

    public String getJsonCategory() {
        return jsonCategory;
    }

    public void setJsonCategory(String jsonCategory) {
        this.jsonCategory = jsonCategory;
    }

    public boolean isDirectionAvailable() {
        return directionAvailable;
    }

    public void setDirectionAvailable(boolean directionAvailable) {
        this.directionAvailable = directionAvailable;
    }

    public boolean isCategoryAvailable() {
        return categoryAvailable;
    }

    public void setCategoryAvailable(boolean categoryAvailable) {
        this.categoryAvailable = categoryAvailable;
    }

    //Gets All Data From Selected Json Object and Parse it. Therefore, view can be updated with the information of selected building
    public void displayBuildingInformation() throws IOException {

        //If category is choosen from the category dropdown, set it to its initial stage.
        buildingCategory = "";
        //Make Category button unclickable.
        categoryAvailable = false;

        //--------------Send a get request with the name of selected building---------------------------------
        JSONObject json;
        try {
            //Replace Space with %20
            String modifiedBuildingName = selectedBuildingName.replaceAll(" ", "%20");
            //Replace Slash character with %2F
            modifiedBuildingName = modifiedBuildingName.replace("/", "%2F");
            //Create Restful Request Url
            String restfulUrl = baseUrl + modifiedBuildingName;
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
        //-----------------------------------------------------------------------------------------------------

    }

    //When Direction Button is Clicked set a variable true. So that side bar interface can be rendered to interface with direction functinality components.
    public void directionButtonPressed() {
        directionPressed = true;

    }
    
    //When User click the refresh button, take the side bar interface to its initial stage
    public void refreshForm() {
        directionPressed = false;

    }

    //Sets the Coordinates of starting point for routes
    public void createStartPoint() {

        JSONObject json;
        try {
            //URL REPLACEMENTS
            //Replace Space with %20
            String modifiedBuildingName = selectedStartPoint.replaceAll(" ", "%20");
            //Replace Slah character with %2F
            modifiedBuildingName = modifiedBuildingName.replace("/", "%2F");
            //Create Restful Request Url
            String restfulUrl = baseUrl + modifiedBuildingName;
            json = new JSONObject(readJSON(restfulUrl));
            lat2 = json.getDouble("latitude");
            lng2 = json.getDouble("longitude");
        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //Sets the Coordinates of destination point for routes
    public void createDestinationPoint() {
        JSONObject json;
        try {
            //Replace Space with %20
            String modifiedBuildingName = selectedBuildingName.replaceAll(" ", "%20");
            //Replace Slah character with %2F
            modifiedBuildingName = modifiedBuildingName.replace("/", "%2F");
            //Create Restful Request Url
            String restfulUrl = baseUrl + modifiedBuildingName;
            //Return Result As JSON
            json = new JSONObject(readJSON(restfulUrl));
            //Get Specific Data from Returned Json
            lat = json.getDouble("latitude");
            lng = json.getDouble("longitude");
        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Take the all json data for a selected category into a variable
    //This json data is parsed in java script
    //It is trasfered to java script with hidden input jsonResult
    public void searchByCategory() {

        //When a category is selected, set the values of selected building to its initial stage.
        selectedBuildingName = "";
        directionAvailable = false;
        description = "";
        imageUrl = null;
        try {
            //Add categories sub Path. And Replace Space with %20
            String modifiedCategoryName = "categories/" + buildingCategory.replaceAll(" ", "%20");

            //Create Restful Request Url
            String restfulUrl = baseUrl + modifiedCategoryName;
            jsonCategory = readJSON(restfulUrl);

        } catch (JSONException ex) {
        } catch (Exception ex) {
            Logger.getLogger(BuildingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //If a Building is not selected make get directions button unclickable
    public void changeDirectionAvailableStatus() {
        if (selectedBuildingName.equals("")) {
            directionAvailable = false;
        } else {
            directionAvailable = true;
        }

    }

    //If a category is not selected make display buildings button unclickable
    public void changeCategoryAvailableStatus() {
        if (buildingCategory.equals("")) {
            categoryAvailable = false;
        } else {
            categoryAvailable = true;
        }

    }

    //Gets All Data From Selected Json Object and Parse it. Therefore, view can be updated with the information of selected building
    //This method will be called when user clicks a marker
    public void displayBuildingInformationFromMarker() throws IOException {

        //Get the Name of selected building from html
        String value = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("dropDownForm:clickedBuildingMarker");

        //If category is choosen from the category dropdown, set it to its initial stage.
        buildingCategory = "";
        categoryAvailable = false;

        //--------------Send a get request with the name of selected building---------------------------------
        JSONObject json;
        try {
            //Replace Space with %20
            String modifiedBuildingName = value.replaceAll(" ", "%20");
            //Replace Slash character with %2F
            modifiedBuildingName = modifiedBuildingName.replace("/", "%2F");
            //Create Restful Request Url
            String restfulUrl = baseUrl + modifiedBuildingName;
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
        //-----------------------------------------------------------------------------------------------------

    }

}
