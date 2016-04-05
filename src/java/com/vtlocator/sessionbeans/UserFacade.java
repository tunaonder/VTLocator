/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.User;

/**
 *
 * @author Onder
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
     //-----------------------------------------------------
    //The following methods are added to the generated code
    //-----------------------------------------------------
    
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    public User findByEmail(String email) {
        if (em.createNamedQuery("User.findByEmail")
                .setParameter("email", email)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (User) (em.createNamedQuery("User.findByEmail")
                .setParameter("email", email)
                .getSingleResult());        
        }
    }
    
}
