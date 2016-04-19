/*
 * Created by Sean Goodrich on 2016.03.15  * 
 * Copyright © 2016 Sean Goodrich. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.Item;
import com.vtlocator.jpaentityclasses.ItemPhoto;
import com.vtlocator.jpaentityclasses.User;
import com.vtlocator.sessionbeans.ItemFacade;
import com.vtlocator.sessionbeans.ItemPhotoFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/*
Used to fetch and edit the user's information
The xhtml files do not interact with the CustomerFacade, they interact with this.
*/
 
@Named(value = "itemManager") // what to use to refer to this class
@SessionScoped // this class will leave scope when the browser ends the session
/**
 *
 * @author Sean
 */
public class ItemManager implements Serializable {
 
    // Instance Variables (Properties)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitudeFound() {
        return latitudeFound;
    }

    public void setLatitudeFound(BigDecimal latitudeFound) {
        this.latitudeFound = latitudeFound;
    }

    public BigDecimal getLongitudeFound() {
        return longitudeFound;
    }

    public void setLongitudeFound(BigDecimal longitudeFound) {
        this.longitudeFound = longitudeFound;
    }

    public Date getDateFound() {
        return dateFound;
    }

    public void setDateFound(Date dateFound) {
        this.dateFound = dateFound;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<ItemPhoto> getItemPhotoCollection() {
        return itemPhotoCollection;
    }

    public void setItemPhotoCollection(Collection<ItemPhoto> itemPhotoCollection) {
        this.itemPhotoCollection = itemPhotoCollection;
    }
    private String name;
    private BigDecimal latitudeFound;
    private BigDecimal longitudeFound;
    private Date dateFound;
    private String category;
    private String statusMessage;
    private Collection<ItemPhoto> itemPhotoCollection;
    private Collection<User> createdBy;
    
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
   
    private Item selected;
    
    /**
     * The instance variable 'itemFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private ItemFacade itemFacade;

    /**
     * The instance variable 'itemPhotoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean PhotoFacade.
     */
    @EJB
    private ItemPhotoFacade itemPhotoFacade;

    /**
     * Creates a new instance of ItemManager
     */
    public ItemManager() {
    }

    public Item getSelected() {
        if (selected == null) {
           // selected = itemFacade.find(FacesContext.getCurrentInstance().
               // getExternalContext().getSessionMap().get("user_id"));
        }
        return selected;
    }

    public void setSelected(Item selected) {
        this.selected = selected;
    }

    // Will create an item object
    public String createItem() {
        

        if (statusMessage.isEmpty()) { // set all fields in cust obj
            try {
                
                Item item = new Item();
                item.setCategory(category);
                item.setDateFound(dateFound);
                item.setLatitudeFound(latitudeFound);
                item.setLongitudeFound(longitudeFound);
                item.setName(name);
                item.setDescription(description);
                item.setCreatedByCollection(createdBy);
                item.setItemPhotoCollection(itemPhotoCollection);
                itemFacade.create(item);
                
            } catch (EJBException e) {
                //email = "";
                statusMessage = "Something went wrong while creating your account!";
                return "";
            }
             // 
            return "index"; // TODO
            
        }
        return "";
    }
    
   
    // returns the item's photos file name
    public List<ItemPhoto> itemPhotos() {
        int id = (int) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("id");
        
        List<ItemPhoto> photoList = itemPhotoFacade.findItemPhotosByItemID(id);
        if (photoList.isEmpty()) {
            return null;
        }
        return photoList;
    }
}