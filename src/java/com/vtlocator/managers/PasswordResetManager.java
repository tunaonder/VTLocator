/*
 * Created by Michael Steele on 2016.03.15  * 
 * Copyright © 2016 Michael Steele. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.User;
import com.vtlocator.jsfclasses.util.CipherService;
import com.vtlocator.sessionbeans.UserFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named(value = "passwordResetManager")
@SessionScoped
/**
 * Contains all the logic for changing the user's password in the DB.
 * Only possible after the user has entered correct security questions.
 * @author Michael
 */
public class PasswordResetManager implements Serializable{
    
    /**
     * Email entered to identify account to reset.
     */
    private String email;
    /**
     * Variable for success/error messages to display.
     */
    private String message = "";
    /**
     * Holds security question answer entered by user.
     */
    private String answer;
    /**
     * Holds new password entered by user.
     */
    private String password;
    
    /**
     * The instance variable 'customerFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean CustomerFacade.
     */
    @EJB
    private UserFacade userFacade;
    
    /**
     * CipherService declaration and instantiation to be used by this class.
     */
    private CipherService cipherService = new CipherService();
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
        
    /**
     * gets the username from the db
     * @return Page to redirect to.
     */
    public String emailSubmit() {
        User user = userFacade.findByEmail(email);
        if (user == null) {
            message = "Entered username does not exist!";
            return "enterEmail?faces-redirect=true";
        }
        else {
            message = "";
            return "securityQuestion?faces-redirect=true";
        }
    }
    
    /**
     * Submits the security questions
     * @return Page to redirect to.
     */
    public String securityquestionSubmit() {
        User user = userFacade.findByEmail(email);
        if (user.getSecurityAnswer().equals(answer)) {
            message = "";
            return "resetPassword?faces-redirect=true";
        }
        else {
            message = "Answer incorrect";
            return "securityQuestion?faces-redirect=true";
        }
    }
    
    public String getSecurityQuestion() {
        int question = userFacade.findByEmail(email).getSecurityQuestion();
        return Constants.QUESTIONS[question];
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Checks to see if the users username and password match
     * @param event Multiple information input by the user.
     */
    public void validateInformation(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String pwd = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        if (pwd.isEmpty() || confirmPassword.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!pwd.equals(confirmPassword)) {
            message = "Passwords must match!";
        } else {
            message = "";
        }   
    }   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Sets the users pass to the given password
     * @return Page to redirect to.
     */
    public String resetPassword() {
        if (message.equals("")) {
            message = "";
            User user = userFacade.findByEmail(email);
            try {
                user.setPassword(cipherService.hash(password));
                userFacade.edit(user);
                email = answer = password = "";                
            } catch (EJBException e) {
                message = "Something went wrong editing your profile, please try again!";
                return "resetPassword?faces-redirect=true";            
            }
            return "login?faces-redirect=true";            
        }
        else {
            return "resetPassword?faces-redirect=true";            
        }
    }
    
    /**
     * This function called after users entered correct security questions.
     * Allows user to set password of the recovered account.
     * @return Redirect page.
     */
    public String changePassword() {
        if (message.equals("")) {
            message = "";
            int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
            User user = userFacade.getUser(user_id);
            try {
                user.setPassword(cipherService.hash(password));
                userFacade.edit(user);
                email = answer = password = "";                
            } catch (EJBException e) {
                message = "Something went wrong editing your profile, please try again!";
                return "changePassword?faces-redirect=true";            
            }
            return "profile?faces-redirect=true";            
        }
        else {
            return "changePassword?faces-redirect=true";            
        }     
    }
            
}