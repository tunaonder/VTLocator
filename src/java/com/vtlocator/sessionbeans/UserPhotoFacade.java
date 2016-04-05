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
 *
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
    
    public List<UserPhoto> findPhotosByUserID(Integer userID) {
        return (List<UserPhoto>) em.createNamedQuery("UserPhoto.findById")
                .setParameter("userId", userID)
                .getResultList();
    }
}
