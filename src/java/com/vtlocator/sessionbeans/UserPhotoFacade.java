/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.UserPhoto;
import java.util.List;

/**
 * Contains functions to access the table of User Photos using SQL commands.
 * @author Onder
 */
@Stateless
public class UserPhotoFacade extends AbstractFacade<UserPhoto> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserPhotoFacade() {
        super(UserPhoto.class);
    }
    
    // The following findPhotosByUserID method is added to the generated code.
    /**
     * Returns all photos that have a foreign key with the given user.
     * @param userID User object to look up
     * @return A list of photos
     */
    public List<UserPhoto> findPhotosByUserID(Integer userID) {
        return (List<UserPhoto>) em.createNamedQuery("UserPhoto.findPhotosByUserId")
                .setParameter("userId", userID)
                .getResultList();
    }
}
