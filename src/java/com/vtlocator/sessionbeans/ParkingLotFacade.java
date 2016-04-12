/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.ParkingLot;
import java.util.List;

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
    
    
    public List<ParkingLot> getLotsByPermission(String permission) {
        return (em.createNamedQuery("ParkingLot.findByPermission")
                .setParameter("permission", permission)
                .getResultList());        
        
    }
}
