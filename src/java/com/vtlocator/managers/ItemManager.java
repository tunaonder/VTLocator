/*
 * Created by Sean Goodrich on 2016.03.15  * 
 * Copyright © 2016 Sean Goodrich. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.Item;
import com.vtlocator.jpaentityclasses.ItemPhoto;
import com.vtlocator.jpaentityclasses.Subscription;
import com.vtlocator.jpaentityclasses.User;
import com.vtlocator.jpaentityclasses.UserPhoto;
import com.vtlocator.jsfclasses.util.MessageClient;
import com.vtlocator.sessionbeans.ItemFacade;
import com.vtlocator.sessionbeans.ItemPhotoFacade;
import com.vtlocator.sessionbeans.SubscriptionFacade;
import com.vtlocator.sessionbeans.UserFacade;
import com.vtlocator.sessionbeans.UserPhotoFacade;
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
    
    @EJB
    private UserFacade userFacade;
    
    @EJB
    private SubscriptionFacade subscriptionFacade;
    
    private String name;
    private BigDecimal latitudeFound = new BigDecimal(0);
    private BigDecimal longitudeFound = new BigDecimal(0);
    private Date dateFound;
    private String category;
    private String statusMessage;
    private Collection<ItemPhoto> itemPhotoCollection;
    private List<Item> recent = null;
    private Item detailItem;
    
    private boolean itemOwner = false;
    

    public Item getDetailItem() {
        return detailItem;
    }

    public void setDetailItem(Item detailItem) {
        this.detailItem = detailItem;
    }

    public List<Item> getRecent() {
        recent = itemFacade.getRecentItems();
        return recent;
    }

    public void setRecent(List<Item> recent) {
        this.recent = recent;
    }
    
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
     * The instance variable 'photoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean PhotoFacade.
     */
    @EJB
    private UserPhotoFacade photoFacade;
    
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
        
        if (this.longitudeFound.equals(new BigDecimal(0)) || this.latitudeFound.equals(new BigDecimal(0))) {
            statusMessage = "Please click a point on the map to specify the location found.";
            return "";
        }
        try {

            Item item = new Item();
            item.setCategory(category);
            item.setDateFound(dateFound);
            item.setLatitudeFound(this.latitudeFound);
            item.setLongitudeFound(this.longitudeFound);
            item.setName(name);
            item.setDescription(description);
            String email = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("email");
            User user = userFacade.findByEmail(email);                
            item.setCreatedBy(user);
            item.setItemPhotoCollection(itemPhotoCollection);

            itemFacade.create(item);
            notifyForCategory(item);

        } catch (EJBException e) {
            //email = "";
            statusMessage = "Something went wrong while creating your account!";
            return "";
        }
         // 
        return "index"; // TODO
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
    
    public String detailPage(int id) {
        detailItem = itemFacade.getItem(id);
        return "detailedItemView";
    }
    
    public String finderPhoto(User finder) {
        if (finder == null) {
            return "user-placeholder.jpg";
        }
        List<UserPhoto> photoList = photoFacade.findPhotosByUserID(finder.getId());
        if (photoList.isEmpty()) {
            return "user-placeholder.jpg";
        }
        return photoList.get(0).getFilename();
    }
    
    
    public void notifyForCategory(Item item) {
        System.out.println(item.getCategory());
        List<Subscription> subscribed = subscriptionFacade.getFromCategory(item.getCategory());
        for (int i = 0; i < subscribed.size(); i++) {
            //this line stops the system from sending a text to the person who posted the item
            if (!item.getCreatedBy().getId().equals(subscribed.get(i).getSubscriber().getId())) {
                String message = "Hi " + subscribed.get(i).getSubscriber().getFirstName() +  ", an item" +
                        " in the category '" + item.getCategory().toLowerCase() + "' was found and posted on VTLocator." +
                        " This item was found by " + item.getCreatedBy().getFirstName() + " " + item.getCreatedBy().getLastName() +
                        ". Go on VTLocator to find more information about this item.";
                MessageClient.sendMessage(subscribed.get(i).getSubscriber().getPhoneNumber(), message);
            }
        }
    }

    public boolean getItemOwner() {
        return itemOwner = isOwner();
    }

    public void setitemOwner(boolean passedBool) {
        this.itemOwner = isOwner();
    }
    
    private boolean isOwner() {
        // Check if item is in view.
        if (detailItem != null) {
            User createdBy = detailItem.getCreatedBy();

            User currentUser = userFacade.getUser((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
            
            boolean userCheck = currentUser != null & createdBy != null;
            
            
            if (userCheck) {
//                System.out.println("email of currentUser: " + currentUser.getEmail());
//                System.out.println("email of createdBy: " + createdBy.getEmail());
                if (createdBy.equals(currentUser)) {
//                if (currentUser.getEmail().equals(createdBy.getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }
}