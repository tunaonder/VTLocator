/*
 * Created by Sean Goodrich on 2016.04.24  * 
 * Copyright © 2016 Sean Goodrich. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.Item;
import com.vtlocator.sessionbeans.ItemFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "searchManager") // what to use to refer to this class
@SessionScoped // this class will leave scope when the browser ends the session
/**
 *
 * @author Sean Goodrich
 */
public class SearchManager implements Serializable {
 
    public SearchManager() {
        
    }
    
    private String geocodedAddress;
    private BigDecimal latitudeSearch = new BigDecimal(0);
    private BigDecimal longitudeSearch = new BigDecimal(0);
    private String query;
    private String filterType;

    public String getGeocodedAddress() {
        return geocodedAddress;
    }

    public void setGeocodedAddress(String geocodedAddress) {
        this.geocodedAddress = geocodedAddress;
    }

    public BigDecimal getLatitudeSearch() {
        return latitudeSearch;
    }

    public void setLatitudeSearch(BigDecimal latitudeSearch) {
        this.latitudeSearch = latitudeSearch;
    }

    public BigDecimal getLongitudeSearch() {
        return longitudeSearch;
    }

    public void setLongitudeSearch(BigDecimal longitudeSearch) {
        this.longitudeSearch = longitudeSearch;
    }
    
    public String getQuery () {
        return this.query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public String getFilterType () {
        return this.filterType;
    }
    
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
    
     /**
     * The instance variable 'itemFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private ItemFacade itemFacade;
    
    private List<Item> resultsList;

    public List<Item> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<Item> resultsList) {
        this.resultsList = resultsList;
    }
    
    public String fullTextSearch() {
        List<Item> allItems = itemFacade.fullTextQuery(this.query, this.filterType);
        resultsList = allItems;
           
        return "lostAndFoundResults";
    }
    
    public String locationSearch() {
        List<Item> allItems = itemFacade.findAll();
        for (int i = 0; i < allItems.size(); i++) {
            double latItem = allItems.get(i).getLatitudeFound().doubleValue();
            double longItem = allItems.get(i).getLongitudeFound().doubleValue();
            double latSearch = latitudeSearch.doubleValue();
            double longSearch = longitudeSearch.doubleValue();
            double distance = SearchManager.distance(latItem, latSearch, longItem, longSearch, 0.0, 0.0);
            distance = distance*0.000621371192; //conversion from meters to miles
            allItems.get(i).setDistanceFromLocation(distance);
            
        }
        for (int i = 0; i < allItems.size(); i++) {
            DecimalFormat df = new DecimalFormat("#.00");
            String distanceFormatted = df.format(allItems.get(i).getDistanceFromLocation());
            allItems.get(i).setFormattedDistance(distanceFormatted);
        }
        Collections.sort(allItems);
        resultsList = allItems;
           
        return "lostAndFoundLocationResults";
    }
    
    /*
    * This function was taken from user DavidG on StackOverflow.
    * The link to the code posting is below:
    * http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
    *
    * Calculate distance between two points in latitude and longitude taking
    * into account height difference. If you are not interested in height
    * difference pass 0.0. Uses Haversine method as its base.
    * 
    * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
    * el2 End altitude in meters
    * @returns Distance in Meters
    */
   public static double distance(double lat1, double lat2, double lon1,
           double lon2, double el1, double el2) {

       final int R = 6371; // Radius of the earth

       Double latDistance = Math.toRadians(lat2 - lat1);
       Double lonDistance = Math.toRadians(lon2 - lon1);
       Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
               + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
               * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
       Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
       double distance = R * c * 1000; // convert to meters

       double height = el1 - el2;

       distance = Math.pow(distance, 2) + Math.pow(height, 2);

       return Math.sqrt(distance);
   }
    
}
