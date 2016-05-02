/*
 * Created by Michael Steele on 2016.03.15  * 
 * Copyright © 2016 Michael Steele. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.UserPhoto;
import com.vtlocator.jpaentityclasses.User;
import com.vtlocator.sessionbeans.UserPhotoFacade;
import com.vtlocator.sessionbeans.UserFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

@Named(value = "fileManager")
@ManagedBean  
@SessionScoped
/**
 * - Allows users to upload a photo to the users profile.
 * - Confirms the uploaded photo is valid.
 * - Stores the uploaded photo in the DB.
 * @author Michael
 */
public class FileManager {

    /**
     * An uploaded file that is not yet in the DB.
     */
    private UploadedFile file;
    /**
     * Error message to display.
     */
    private String message = "";
    
    /**
     * The instance variable 'customerFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private UserFacade customerFacade;

    /**
     * The instance variable 'photoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserPhotoFacade.
     */
    @EJB
    private UserPhotoFacade photoFacade;

    /**
     * An uploaded file that is not yet in the DB.
     * @return A file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * Obtains the uploaded file
     * @param file A file
     */
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
    
    /**
     * Redirect to profile
     * @return Redirect
     */
    public String cancel() {
        message = "";
        return "profile?faces-redirect=true";
    }

    // Takes an uploaded file
    // Copies the file, creates a thumbnail version of the file
    // Stores in the database, attached to the customer
    /**
     * Stores uploaded file in the database, attached to the user.
     * @param file Uploaded file
     * @return Error message or success message.
     */
    public FacesMessage copyFile(UploadedFile file) {
        try {
            deletePhoto();
            
            InputStream in = file.getInputstream();
            
            in.close();

            FacesMessage resultMsg;

            String email = (String) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("email");

            User user = customerFacade.findByEmail(email);

            // Insert photo record into database
            String extension = file.getContentType();
            extension = extension.startsWith("image/") ? extension.subSequence(6, extension.length()).toString() : "png";
            List<UserPhoto> photoList = photoFacade.findPhotosByUserID(user.getId());
            if (!photoList.isEmpty()) {
                photoFacade.remove(photoList.get(0));
            }

            photoFacade.create(new UserPhoto(extension, user));
            UserPhoto photo = photoFacade.findPhotosByUserID(user.getId()).get(0);
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
     * @param inputStream Stream of data from file
     * @param childName Name of file + extension
     * @return A file
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
     * deletes current users photo from the DB
     */
    public void deletePhoto() {
        FacesMessage resultMsg;
        String email = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("email");

        User user = customerFacade.findByEmail(email);

        List<UserPhoto> photoList = photoFacade.findPhotosByUserID(user.getId());
        if (photoList.isEmpty()) {
            resultMsg = new FacesMessage("Error", "You do not have a photo to delete.");
        } else {
            UserPhoto photo = photoList.get(0);
            try {
                Files.deleteIfExists(Paths.get(photo.getFilePath()));
                
                Files.deleteIfExists(Paths.get(Constants.ROOT_DIRECTORY+"tmp_file"));
                 
                photoFacade.remove(photo);
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            resultMsg = new FacesMessage("Success", "Photo successfully deleted!");
        }
        FacesContext.getCurrentInstance().addMessage(null, resultMsg);
    }
}