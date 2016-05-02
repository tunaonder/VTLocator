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
 
    /**
     * Default empty constructor
     */
    public SearchManager() {
        
    }
    
    /**
     * The human-readable street address associated with longitude latitude point.
     */
    private String geocodedAddress;
    
    /**
     * The latitude associated with a search by location query.
     */
    private BigDecimal latitudeSearch = new BigDecimal(0);
    
    /**
     * The longitude associated with a search by location query.
     */
    private BigDecimal longitudeSearch = new BigDecimal(0);
    
    /**
     * The query in words that was made by the user.
     */
    private String query;
    
    /**
     * Specifies the type of filter on the text query
     */
    private String filterType;
    
     /**
     * The instance variable 'itemFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private ItemFacade itemFacade;
    
    /**
     * 
     */
    private List<Item> resultsList;

     /**
     * Gets the geocoded address
     * @return geocodedAddress
     */
    public String getGeocodedAddress() {
        return geocodedAddress;
    }

    /**
     * Set the geocoded address for a search
     * @param geocodedAddress the human-readable name for an address
     */
    public void setGeocodedAddress(String geocodedAddress) {
        this.geocodedAddress = geocodedAddress;
    }
    
    /**
     * Gets the latitude for a location search
     * @return latitude in bigdecimal form
     */
    public BigDecimal getLatitudeSearch() {
        return latitudeSearch;
    }

    /**
     * Sets the latitude for a location search
     * @param latitudeSearch latitude for search
     */
    public void setLatitudeSearch(BigDecimal latitudeSearch) {
        this.latitudeSearch = latitudeSearch;
    }

    /**
     * Gets the longitude for a location search
     * @return longitude in bigdecimal form
     */
    public BigDecimal getLongitudeSearch() {
        return longitudeSearch;
    }

    /**
     * Sets the longitude for a location search
     * @param longitudeSearch longitude to be searched for
     */
    public void setLongitudeSearch(BigDecimal longitudeSearch) {
        this.longitudeSearch = longitudeSearch;
    }
    
    /**
     * Gets the query string for text searches
     * @return string of query
     */
    public String getQuery () {
        return this.query;
    }
    
    /**
     * Sets the query string for text searches
     * @param query new query string
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
    /**
     * Gets filter type applied to search
     * @return string representing filter type
     */
    public String getFilterType () {
        return this.filterType;
    }
    
    /**
     * Sets the filter type for query
     * @param filterType filter type to use in search
     */
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
    
    /**
     * Gets the list of search results
     * @return list of type item
     */
    public List<Item> getResultsList() {
        return resultsList;
    }

    /**
     * Sets the list of search results
     * @param resultsList item list of results
     */
    public void setResultsList(List<Item> resultsList) {
        this.resultsList = resultsList;
    }
    
    /**
     * Runs a full text search, and returns results page
     * @return results page
     */
    public String fullTextSearch() {
        List<Item> allItems = itemFacade.fullTextQuery(this.query, this.filterType);
        resultsList = allItems;
           
        return "lostAndFoundResults";
    }
    
    /**
     * Runs a location search for items
     * @return location results page
     */
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
