/*
 * Created by Michael Steele on 2016.03.15  * 
 * Copyright © 2016 Michael Steele. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.UserPhoto;
import com.vtlocator.jpaentityclasses.User;
import com.vtlocator.sessionbeans.UserPhotoFacade;
import com.vtlocator.sessionbeans.UserFacade;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

/*
Used to fetch and edit the user's information
The xhtml files do not interact with the CustomerFacade, they interact with this.
*/
 
@Named(value = "accountManager") // what to use to refer to this class
@SessionScoped // this class will leave scope when the browser ends the session
/**
 *
 * @author Michael
 */
public class AccountManager implements Serializable {
 
    // Instance Variables (Properties)
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone_number;
    private String statusMessage;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    private int security_question;
    private String security_answer;
        
    private Map<String, Object> security_questions; // stored as a map
    
    private User selected;
    
    /**
     * The instance variable 'userFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private UserFacade userFacade;

    /**
     * The instance variable 'photoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean PhotoFacade.
     */
    @EJB
    private UserPhotoFacade photoFacade;

    /**
     * Creates a new instance of AccountManager
     */
    public AccountManager() {
    }

    /**
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public int getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(int security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    // Returns all security questions in the map
    public Map<String, Object> getSecurity_questions() {
        if (security_questions == null) {
            security_questions = new LinkedHashMap<>();
            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                security_questions.put(Constants.QUESTIONS[i], i);
            }
        }
        return security_questions;
    }

   

    

  

    public User getSelected() {
        if (selected == null) {
            selected = userFacade.find(FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user_id"));
        }
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    // Will create a customer object
    public String createAccount() {
        
        // Check to see if a user already exists with the email given.
        User aUser = userFacade.findByEmail(email);
        
        if (aUser != null) {
            email = "";
            return "";
        }

        if (statusMessage.isEmpty()) { // set all fields in cust obj
            try {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);                
                user.setSecurityQuestion(security_question);
                user.setSecurityAnswer(security_answer);
                user.setEmail(email);
                user.setPhoneNumber(phone_number);
                user.setPassword(password);
                userFacade.create(user);
                initializeSessionMap();
            } catch (EJBException e) {
                email = "";
                statusMessage = "Something went wrong while creating your account!";
                return "";
            }
             // 
            return "buildings"; // navigate to profile
        }
        return "";
    }

    // Updates the info on the account
    public void updateFirstName() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User editUser = userFacade.getUser(user_id);
        if (this.selected != null && this.selected.getFirstName() != null) {
            try {
                editUser.setFirstName(this.selected.getFirstName());
                userFacade.edit(editUser);
            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing your profile!";
            }
        }  
    }
    // Updates the info on the account
    public void updateLastName() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User editUser = userFacade.getUser(user_id);
        if (this.selected != null && this.selected.getLastName() != null) {
            try {
                editUser.setLastName(this.selected.getLastName());
                userFacade.edit(editUser);
            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing your profile!";
            }
        }
    }
    // Updates the info on the account
    public void updatePhoneNumber() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User editUser = userFacade.getUser(user_id);
        if (this.selected != null && this.selected.getPhoneNumber() != null) {
            try {
                editUser.setPhoneNumber(this.selected.getPhoneNumber());
                userFacade.edit(editUser);
            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing your profile!";
            }
        }
    }
    
    // Updates the info on the account
    public void updateSecurityAnswer() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User editUser = userFacade.getUser(user_id);
        if (this.selected != null && this.selected.getSecurityAnswer() != null) {
            try {
                editUser.setSecurityAnswer(this.selected.getSecurityAnswer());
                userFacade.edit(editUser);
            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing your profile!";
            }
        }
    }
    
    
    
    // Checks to see if the user's name and password is correct
    public void validateInformation(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        // Get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String pwd = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();

        // Get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        if (pwd.isEmpty() || confirmPassword.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!pwd.equals(confirmPassword)) {
            statusMessage = "Passwords must match!";
        } else {
            statusMessage = "";
        }   
    }

    // Creates a session for this user
    public void initializeSessionMap() {
        User user = userFacade.findByEmail(getEmail()); 
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("email", email);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }

    // Confirms if the enter password is correct
    private boolean correctPasswordEntered(UIComponent components) {
        UIInput uiInputVerifyPassword = (UIInput) components.findComponent("verifyPassword");
        String verifyPassword = uiInputVerifyPassword.getLocalValue() == null ? ""
                : uiInputVerifyPassword.getLocalValue().toString();
        if (verifyPassword.isEmpty()) {
            statusMessage = "";
            return false;
        } else {
            if (verifyPassword.equals(password)) {
                return true;
            } else {
                statusMessage = "Invalid password entered!";
                return false;
            }
        }
    }

    // removes this session
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        email = firstName = lastName = password = email = statusMessage = phone_number = "";
        security_answer = "";
        security_question = 0;
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true"; // TODO don't know address yet
    }
   
    // returns the user's photo file name
    public String userPhoto() {
        String email = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("email");
        User user = userFacade.findByEmail(email);
        List<UserPhoto> photoList = photoFacade.findPhotosByUserID(user.getId());
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getExtension();
    }

}