/*
 * Created by Sait Tuna Onder on 2016.04.07  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.Building;
import com.vtlocator.sessionbeans.BuildingFacade;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author Onder
 */

@ManagedBean
@SessionScoped
@Named (value = "buildingManager")
public class BuildingManager implements Serializable{
    
    //This facade is used to connect Controller to the database
    @EJB
    private com.vtlocator.sessionbeans.BuildingFacade ejbFacade;
    private List<Building> items = null;
    private String selectedBuildingName;

    //Building Names Hash Map to Display at Building Menu
    private HashMap<String, String> buildingNames;
     private HashMap<String, String> buildingNames2;
    
    private MapModel emptyModel;
      
    private String title;
      
    private double lat;
      
    private double lng;
    
    private double vtLat = 37.227264;
    private double vtLong = -80.420745;
    
    @PostConstruct
    public void init() {
        items = getItems();
        
       //Sizes are in a  Hash Map
        buildingNames = new HashMap<>();
        buildingNames2 = new HashMap<>();
        for (int i = 0; i<items.size(); i++){
            buildingNames.put(items.get(i).getName(), items.get(i).getName());
        }
        buildingNames2 = sortByValues(buildingNames);
        
        System.out.println(buildingNames2.get("Agnew Hall"));
       // selectedBuildingName = items.get(0).getName();

        
         emptyModel = new DefaultMapModel();
    
    }
    
    //Calls companyFacade and gets items from the database
    public List<Building> getItems() {
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
     
  public void displayBuildingLocation(){
      if( selectedBuildingName !=null && ! selectedBuildingName.equals("")){
          selectedBuildingName = buildingNames.get(selectedBuildingName);
      }
      
      System.out.println(selectedBuildingName);
            
     
      
  }
  
  public MapModel getEmptyModel() {
        return emptyModel;
    }
      
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
  
  public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), title);
        emptyModel.addOverlay(marker);
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
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

    public HashMap<String, String> getBuildingNames2() {
        return buildingNames2;
    }

    public void setBuildingNames2(HashMap<String, String> buildingNames2) {
        this.buildingNames2 = buildingNames2;
    }
    
    
  
  
}
    
    
    
    
    


    