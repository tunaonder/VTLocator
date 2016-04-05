/*
 * Created by Michael Steele on 2016.03.15  * 
 * Copyright © 2016 Michael Steele. All rights reserved. * 
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/*
Gets the logged in user
*/

@Named(value = "profileViewManager")
@SessionScoped
/**
 *
 * @author Michael
 */
public class ProfileViewManager implements Serializable {

    // Instance Variable (Property)
    private User user;
    
  /**
   * The instance variable 'userFacade' is annotated with the @EJB annotation.
   * This means that the GlassFish application server, at runtime, will inject in
   * this instance variable a reference to the @Stateless session bean UserFacade.
   */  
  @EJB
  private com.vtlocator.sessionbeans.UserFacade userFacade;

  public ProfileViewManager() {

  }

  public String viewProfile() {
    return "Profile"; //TODO need real page name
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  public User getLoggedInUser() {
    return userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

}