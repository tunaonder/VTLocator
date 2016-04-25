/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.ItemPhoto;
import java.util.List;

/**
 *
 * @author Onder
 */
@Stateless
public class ItemPhotoFacade extends AbstractFacade<ItemPhoto> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemPhotoFacade() {
        super(ItemPhoto.class);
    }

    // The following findPhotosByUserID method is added to the generated code.
    
    public List<ItemPhoto> findItemPhotosByItemID(Integer itemID) {
        return (List<ItemPhoto>) em.createNamedQuery("ItemPhoto.findItemPhotosByItemId")
                .setParameter("itemId", itemID)
                .getResultList();
    }
    
}

