/*
 * Created by VTLocator Group on 2016.04.04  *
 * Copyright © 2016 VTLocator Group. All rights reserved. *
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.ParkingLot;
import java.util.List;

/**
 * ParkingLotFacade class communicates with the entity manager to return specific lots
 * @author VTLocator Group
 */
@Stateless
public class ParkingLotFacade extends AbstractFacade<ParkingLot> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor for ParkingLotFacade, calls AbstractFacade()
     */
    public ParkingLotFacade() {
        super(ParkingLot.class);
    }

    /**
     * getLotsByPermission receives permission (ANY, FACULTY, ...) and returns a list of parking lots
     * @param  permission a string representation of parking pass permission {any, commuter, faculty, resident, metered, parking_office}
     * @return            A list of ParkingLot objects
     */
    public List<ParkingLot> getLotsByPermission(String permission) {
        return (em.createNamedQuery("ParkingLot.findByPermission")
                .setParameter("permission", permission)
                .getResultList());

    }
}
