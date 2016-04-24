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
    private List<Item> allRecent = null;
    private List<Item> allItems = null;
    private Item detailItem;
    private UploadedFile file;
    private List<UploadedFile> fileList;

    public List<UploadedFile> getFileList() {
        return fileList;
    }
    private String message = "";

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
            this.selected = item;
            uploadMultiple();

        } catch (EJBException e) {
            //email = "";
            statusMessage = "Something went wrong while creating your account!";
            return "";
        }
         // 
        return "manageItems";
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

    // Returns the uploaded file
    public UploadedFile getFile() {
        return file;
    }

    // Obtains the uploaded file
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    // Returns the message
    public String getMessage() {
        return message;
    }

    // Obtains the message
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * "Profile?faces-redirect=true" asks the web browser to display the
     * Profile.xhtml page and update the URL corresponding to that page.
     * @return Profile.xhtml or nothing
     */
    // If the uploaded file is not empty, it copies the file to DB and goes to profile.
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
    
    // redirect to profile
    public String cancel() {
        message = "";
        return "profile?faces-redirect=true";
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        fileList.add(event.getFile());
    }

    // Takes an uploaded file
    // Copies the file, creates a thumbnail version of the file
    // Stores in the database, attached to the customer
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

    // Streams in bytes, converts the streamed bytes into a file
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
    
    public String getMainImageByItemId(int id) {
        List<ItemPhoto> photoList = itemPhotoFacade.findItemPhotosByItemID(id);
        if (photoList.isEmpty()) {
            return "item-placeholder.jpg";
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
}