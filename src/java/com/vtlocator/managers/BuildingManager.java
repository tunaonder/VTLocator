/*
 * Created by Sait Tuna Onder on 2016.04.07  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.Building;
import com.vtlocator.sessionbeans.BuildingFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

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
    private Building selected;
    
     private String size;

    //These HashMaps are used to have price information for all specific sizes
    private Map<String, String> sizes;
    
    
    @PostConstruct
    public void init() {
        items = getItems();
        selected = items.get(0);
        System.out.println(selected.getName());
        
       //Sizes are in a  Hash Map
        sizes = new HashMap<>();
        sizes.put("Small", "Small");
        sizes.put("Medium", "Medium");
        sizes.put("Large", "Large");
        
        size = sizes.get("Small");

    
    }
    
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Map<String, String> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, String> sizes) {
        this.sizes = sizes;
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

    public Building getSelected() {
        return selected;
    }

    public void setSelected(Building selected) {
        this.selected = selected;
    }
    
    
    
    
}


    