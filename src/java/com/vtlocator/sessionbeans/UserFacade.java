/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpaentityclasses.User;

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
    
}
