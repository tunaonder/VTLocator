/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.Notification;

/**
 *
 * @author Onder
 */
@Stateless
public class NotificationFacade extends AbstractFacade<Notification> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificationFacade() {
        super(Notification.class);
    }
    
}
