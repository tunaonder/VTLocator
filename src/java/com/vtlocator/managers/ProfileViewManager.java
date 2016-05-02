/*
 * Created by VTLocator Group on 2016.03.15  *
 * Copyright © 2016 VTLocator Group. All rights reserved. *
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/*
Gets the logged in user
*/

@Named(value = "profileViewManager")
@SessionScoped
/**
 * ProfileViewManager allows the user to interact with the specific user profile within the same session.
 * @author VTLocator Group
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

  /**
   * viewProfile is triggered by an action attribute that redirects the user to a profile page
   * @return String "Profile"
   */
  public String viewProfile() {
    return "Profile";
  }

  /**
   * Gets the user
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * If a logged in user exists, the user's id is stored in FacesContext.
   * Get the id if exists, then call userFacade's em (entitiy manager) to return logged in user.
   * @return User if user is not logged in, returns null
   */
  public User getLoggedInUser() {
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    if (context != null) {
        Object user_id = context.getSessionMap().get("user_id");
        if (user_id != null) {
            return userFacade.find(user_id);
        }
    }
    return null;
  }

  /**
   * Sets the user
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

}