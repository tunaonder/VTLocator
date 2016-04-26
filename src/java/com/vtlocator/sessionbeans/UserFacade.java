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

//User Facade class is used to get the user object with the provided information from database
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
    
    //Find user by its id
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    //Find User by its' email
    public User findByEmail(String email) {
        //If it there is no such an email return null
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
