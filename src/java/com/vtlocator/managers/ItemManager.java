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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/*
Used to fetch and edit the user's information
The xhtml files do not interact with the CustomerFacade, they interact with this.
*/
 
@Named(value = "itemManager") // what to use to refer to this class
@SessionScoped // this class will leave scope when the browser ends the session
@ManagedBean
/**
 *
 * @author Sean
 */
public class ItemManager implements Serializable {
 
    @EJB
    private UserFacade userFacade;
    @EJB
    private SubscriptionFacade subscriptionFacade;
    @EJB
    private ItemFacade itemFacade;
    @EJB
    private ItemPhotoFacade itemPhotoFacade;
    @EJB
    private UserPhotoFacade photoFacade;
    
    private Item selected;
    private String name;
    private BigDecimal latitudeFound = new BigDecimal(0);
    private BigDecimal longitudeFound = new BigDecimal(0);
    private Date dateFound = new Date();
    private String category;
    private String statusMessage;
    private Collection<ItemPhoto> itemPhotoCollection;
    private List<Item> recent = null;
    private List<Item> userItems = null;
    private Item detailItem; 
    private boolean itemOwner = false;
    private List<Item> allRecent = null;
    private List<Item> allItems = null;
    private List<ItemPhoto> photosForItem;
    private UploadedFile file;
    private List<UploadedFile> fileList;
    private String message = "";
    private String description;
    private String notifyNumber;
    
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

    public List<UploadedFile> getFileList() {
        return fileList;
    }

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

    public List<Item> getUserItems() {
        int currentUserID = ((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        userItems = itemFacade.findItemsByUserID(currentUserID);
        return userItems;
    }

    public void setUserItems(List<Item> userItems) {
        this.userItems = userItems;
    }
    
    public List<Item> getAllRecent() {
        recent = itemFacade.getAllRecentItems();
        return allRecent;
    }

    public void setAllRecent(List<Item> allRecent) {
        this.allRecent = allRecent;
    }

    public List<Item> getAllItems() {
        allItems = itemFacade.getAllRecentItems();
        return allItems;
    }

    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }

    public List<ItemPhoto> getPhotosForItem(int itemId) {
        photosForItem = itemPhotoFacade.findItemPhotosByItemID(itemId);
        return photosForItem;
    }
    
    public List<Item> getItemsForUser(int userId) {
        return itemFacade.getItemsForUser(userId);
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
   
    

    
    
    /**
     * Creates a new instance of ItemManager
     */
    public ItemManager() {
        fileList = new ArrayList<UploadedFile>();
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
    
    public String ellipsesDescription(String str) {
        if (str != null && str.length() > 45) {
            int endIndex = str.indexOf(' ', 40);
            if (endIndex == -1) {
                endIndex = 40;
            }
            return str.substring(0, endIndex) + "...";
        }
        return str;
    }
    
    public String ellipsesTitle(String str) {
        if (str != null && str.length() > 16) {
            int check = str.indexOf(' ', 13);
            if (check == -1) {
                check = 13;
            }
            return str.substring(0, check) + "...";
        }
        return str;
    }

    /**
     * Create an item object using form data.
     * Moves all uploaded photos to database
     * @return 
     */
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
            this.selected = item;
            uploadMultiple();
            clearCreateItemForm();

        } catch (EJBException e) {
            e.printStackTrace();
            statusMessage = "Something went wrong while creating your item!";
            return "";
        }

        return "manageItems?faces-redirect=true"; // after creating an item, navigate to manageItems
    }
    
    /**
     * Clears all data on the createItem page.
     */
    public void clearCreateItemForm() {
        fileList.clear();
        description = "";
        name = "";
        category = "HOKIE_PASSPORT";
        dateFound = new Date(); 
        latitudeFound = new BigDecimal(0);
        longitudeFound = new BigDecimal(0);
        statusMessage = null;
    }
    
    public String cancelCreate() {
        clearCreateItemForm();
        
        return "lostAndFound?faces-redirect=true";
    }
    
    
        // Will edit an item object
    public String editItem() {
        try {
            if (detailItem != null) {
                Item item = detailItem;
                item.setCategory(this.category);
                item.setDateFound(detailItem.getDateFound());
                item.setLatitudeFound(this.latitudeFound);
                item.setLongitudeFound(this.longitudeFound);
                item.setName(detailItem.getName());
                item.setDescription(detailItem.getDescription());
                item.setId(detailItem.getId());
//                item.setCreatedBy(user); // cannot change who created
                item.setItemPhotoCollection(itemPhotoCollection); // leave photo collection unchanged

                detailItem = item;
                itemFacade.edit(detailItem);
                notifyForCategory(item);
                this.selected = item;
                uploadMultiple();
                clearCreateItemForm();
            }
            
        } catch (EJBException e) {
            //email = "";
            statusMessage = "Something went wrong while creating your item!";
            return "";
        }

        return "manageItems?faces-redirect=true"; // after editing an item, navigate to manageItems
    }

    /**
     * Returns the uploaded file
     * @return 
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * Obtains the uploaded file
     * @param file 
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * Obtains the message
     * @param message A string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Obtains the message
     * @param message A string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Moves uploaded files on the bean to the database.
     * @return Uploaded files
     */
    public String upload() {
        if (file.getSize() != 0) {
            copyFile(file);
            message = "";
            return "profile?faces-redirect=true";
        } else {
            message = "You need to upload a file first!";
            return "";
        }
    }
    
    /**
     * Moves uploaded files on the bean to the database.
     * @return Uploaded files
     */
    public String uploadMultiple() {
        for (UploadedFile aFile : fileList) {
            if (aFile.getSize() != 0) {
                copyFile(aFile);
            }
        }
        if (fileList.isEmpty() || fileList.get(0).getSize() == 0) {
            message = "You need to upload a file first!";
            return "";
        }
        else {
            message = "";
            return "profile?faces-redirect=true";
        }
    }
    
    /**
     * Redirect to profile
     * @return profile view
     */
    public String cancel() {
        message = "";
        return "profile?faces-redirect=true";
    }
    
    /**
     * Method that is fired when Primefaces p:fileUpload uploads a file.
     * Called once for each file if multiple files are uploaded.
     * Adds each file to the list.
     * @param event Uploaded file
     */
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        fileList.add(event.getFile());
    }

    /**
     * Takes an uploaded file
     * Copies the file, creates a thumbnail version of the file
     * Stores in the database, attached to the customer
     * @param file File to upload
     * @return Faces message
     */
    public FacesMessage copyFile(UploadedFile file) {
        try {
            // Do not delete because an item can have multiple photos
            //deletePhoto(); 
            
            InputStream in = file.getInputstream();
            
            in.close();

            FacesMessage resultMsg;

            // Insert photo record into database
            String extension = file.getContentType();
            extension = extension.startsWith("image/") ? extension.subSequence(6, extension.length()).toString() : "png";

            ItemPhoto photo = new ItemPhoto(extension, this.selected);
            itemPhotoFacade.create(photo);
            in = file.getInputstream();
            File uploadedFile = inputStreamToFile(in, photo.getFilename());
            resultMsg = new FacesMessage("Success!", "File Successfully Uploaded!");
            return resultMsg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FacesMessage("Upload failure!",
            "There was a problem reading the image file. Please try again with a new photo file.");
    }

    /**
     * Streams in bytes, converts the streamed bytes into a file
     * @param inputStream Data of an uploaded image
     * @param childName File name of an uploaded file
     * @return The created file
     * @throws IOException 
     */
    private File inputStreamToFile(InputStream inputStream, String childName)
            throws IOException {
        // Read in the series of bytes from the input stream
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        // Write the series of bytes on file.
        File targetFile = new File(Constants.ROOT_DIRECTORY, childName);

        OutputStream outStream;
        outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        outStream.close();

        // Save reference to the current image.
        return targetFile;
    }
    
    /**
     * Sets the item to view and loads the view item page.
     * @param id Id of an item.
     * @return Page to direct to.
     */
    public String detailPage(int id) {
        detailItem = itemFacade.getItem(id);
        return "detailedItemView";
    }
    
    /**
     * Gets the profile photo of the user who posted the item.
     * @param finder User who posted item
     * @return File name of users photo
     */
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
    
    /**
     * Get the first image associated with an item, we use that image as
     * the item profile image.
     * @param id Id of an item
     * @return An image file name.
     */
    public String getMainImageByItemId(int id) {
        List<ItemPhoto> photoList = itemPhotoFacade.findItemPhotosByItemID(id);
        if (photoList.isEmpty()) {
            return "item-placeholder.jpg";
        }
        return photoList.get(0).getFilename();
    }
  
    /**
     * Sends a text to subscribed users when an item in their category is posted.
     * Uses the Java Messaging Service.
     * @param item A recently posted item to notify users about.
     */
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

    /**
     * Get if owner of item
     * @return True if owner
     */
    public boolean getItemOwner() {
        return itemOwner = isOwner();
    }

    /**
     * Sets owner user of item.
     * @param passedBool 
     */
    public void setitemOwner(boolean passedBool) {
        this.itemOwner = isOwner();
    }
    
    /**
     * Checks if the user created the current lost item post.
     * @return True if the item was lost by the current user.
     */
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
    
    private void deletePost(Item detailItem) {
        int itemID;
        if (detailItem != null) {
            itemID = detailItem.getId();
            itemFacade.deleteByItemID(itemID);
        }
                
    }
    
    /**
     * Notifies a user about item
     * @param item item to notify
     * @param user user to notify
     */
    private void notifyUser(Item item, User userToNotify, User userThatNotifies) {
        String message = "Hi " + userToNotify.getFirstName() + ", the item you have posted on VTLocator ("
                + item.getName() + ") is being claimed by [" + userThatNotifies.getFirstName() + " " + userThatNotifies.getLastName()
                + "]. \nPlease call " + userThatNotifies.getPhoneNumber() + " to arrange a pickup time/place for the item.";
        
        MessageClient.sendMessage(userToNotify.getPhoneNumber(), message);
        System.out.println(message);
    }
    
    /**
     * Uploader removes post
     */
    public String resolve() {
        if (detailItem != null) {
            User createdBy = detailItem.getCreatedBy();

            if (createdBy != null) {
                deletePost(detailItem);
                FacesContext.getCurrentInstance().addMessage("belonging-growl", new FacesMessage("Resolved. Your post has been marked resolved and will now be deleted."));
                return "lostAndFound";
            }
        }
        return "lostAndFound";
    }
    
    
    /** 
     * Currently logged in user notifies uploader
     */
    public String claim() {
        if (detailItem != null) {
            User createdBy = detailItem.getCreatedBy();
            if (createdBy != null) {
                User currentUser = userFacade.getUser((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
                if (currentUser != null) {
                    notifyUser(detailItem, createdBy, currentUser);
                    FacesContext.getCurrentInstance().addMessage("belonging-growl", new FacesMessage("Claimed. Your notification has been sent to the uploader."));
                    return "lostAndFound";
                }
            }
        }
        
        return "lostAndFound";
    }
    
    /**
     * Uploader for item can edit.
     * @return 
     */
    public String edit() {
        if (detailItem != null) {
            User createdBy = detailItem.getCreatedBy();
            if (createdBy != null) {
                return "editItemView?faces-redirect=true";
            }
        }
        return "lostAndFound";
    }
        
    /**
     * delete method called from manageItems.xhtml 
     * Deletes an item
     * @param itemID item id to delete
     * @throws IOException 
     */
    public void delete(String itemID) throws IOException {
        User currentUser = userFacade.getUser((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
//        int item_id = Integer.parseInt(itemID);
        int item_id = Integer.parseInt(itemID);
        itemFacade.deleteByItemID(item_id);
        FacesContext.getCurrentInstance().addMessage("belonging-growl", new FacesMessage("Your item has been deleted."));
        // Force page refresh
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    /**
     * User marks item as found. Removes the item from database.
     * @param itemID item id to resolve
     * @throws IOException 
     */
    public void resolve(String itemID) throws IOException {
        int item_id = Integer.parseInt(itemID);
        itemFacade.deleteByItemID(item_id);
        FacesContext.getCurrentInstance().addMessage("belonging-growl", new FacesMessage("Resolved. Your post has been marked resolved and will now be deleted."));
        // Force page refresh
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public String edit(int itemID) {
        detailItem = itemFacade.getItem(itemID);
        return "editItemView";
    }

    public String getNotifyNumber() {
        return notifyNumber;
    }

    public void setNotifyNumber(String notifyNumber) {
        this.notifyNumber = notifyNumber;
    }
    
    public void notifyFriend() {
        User currentUser = userFacade.getUser((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        User createdBy = detailItem.getCreatedBy();
        if (this.notifyNumber != null) {
            String notifyMessage = "Hi " + ", " + currentUser.getFirstName() + " " + currentUser.getLastName() +
                    " thinks that an item posted on VTLocator (" + detailItem.getName() + ") by " +
                    createdBy.getFirstName() + " " + createdBy.getLastName() + " may be yours. " +
                    " If you would like more information on this item, please go on VTLocator. ";        
            if (MessageClient.sendMessage(notifyNumber, notifyMessage)) {
                FacesContext.getCurrentInstance().addMessage("belonging-growl", new FacesMessage("Your message has been sent."));
            }
        }
    }

    /**
     * Deletes the item photo with the given id.
     * @param photoId
     * @throws IOException 
     */
    public void deleteItemPhotoById(String photoId) throws IOException {
        int photo_id = Integer.parseInt(photoId);
        itemPhotoFacade.deleteByItemPhotoID(photo_id);
        FacesContext.getCurrentInstance().addMessage("belonging-growl", new FacesMessage("Photo removed."));
        // Force page refresh
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    } 
}