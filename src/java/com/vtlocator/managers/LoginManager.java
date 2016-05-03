/*
 * Created by VTLocator Group on 2016.03.15  * 
 * Copyright © 2016 VTLocator. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.User;
import com.vtlocator.jsfclasses.util.CipherService;
import com.vtlocator.sessionbeans.UserFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * This class confirms that the users entered username and password match 
 * what's stored on the DB and creates a session for the user
*/
@ManagedBean(name = "loginManager")
@SessionScoped
public class LoginManager implements Serializable {

  private String email;
  private String password;
  private String errorMessage;
  
  /**
   * The instance variable 'userFacade' is annotated with the @EJB annotation.
   * This means that the GlassFish application server, at runtime, will inject in
   * this instance variable a reference to the @Stateless session bean UserFacade.
   */
  @EJB
  private UserFacade userFacade;

  /**
   * CipherService declaration and instantiation to be used by this class.
   */
  private CipherService cipherService = new CipherService();
  /**
   * Creates a new instance of LoginManager
   */
  public LoginManager() {
  }

  /**
   * @return the email
   */
  public String getEmail() {
        return email;
  }
  
  /**
   * @param email the email address
   */
  public void setEmail(String email) {
        this.email = email;
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
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * This method logs in the given user.
   * @return redirect string depending on success of failure of auth
   */
  public String loginUser() {
    User user = userFacade.findByEmail(getEmail());
    //If there isnt such a user in database return ""
    if (user == null) {
      errorMessage = "Invalid email or password!";
      return "";
    } else {
        //Check if the hash of the provided password is same with the actual password in the DB
        if (user.getEmail().equals(getEmail()) 
            && user.getPassword().equals(cipherService.hash(getPassword()))) {
        errorMessage = "";
        //Start the session map
        initializeSessionMap(user);
        //Navigate to buildings
        return "buildings"; 
      }
      //If given information is not correct give an error message
      errorMessage = "Invalid email or password!";
      return "";
    }
  }

  /**
   * Add necessary information of user into current session map
   * @param user the user to initialize to the session map
   */
  public void initializeSessionMap(User user) {
    FacesContext.getCurrentInstance().getExternalContext().
            getSessionMap().put("first_name", user.getFirstName());
    FacesContext.getCurrentInstance().getExternalContext().
            getSessionMap().put("last_name", user.getLastName());
    FacesContext.getCurrentInstance().getExternalContext().
            getSessionMap().put("email", email);
    FacesContext.getCurrentInstance().getExternalContext().
            getSessionMap().put("user_id", user.getId());
  }

}