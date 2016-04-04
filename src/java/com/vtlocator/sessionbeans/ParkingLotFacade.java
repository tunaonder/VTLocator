/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpaentityclasses.ParkingLot;

/**
 *
 * @author Onder
 */
@Stateless
public class ParkingLotFacade extends AbstractFacade<ParkingLot> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParkingLotFacade() {
        super(ParkingLot.class);
    }
    
}
