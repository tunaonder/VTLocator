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
    
    /**
     * Deletes an item with given itemID
     * @param id itemID to delete
     */
    public void deleteByItemPhotoID(int id) {
        ItemPhoto photo = (ItemPhoto) em.createQuery("SELECT i FROM ItemPhoto i WHERE i.id = :photoId")
                .setParameter("photoId", id)
                .getResultList().get(0);
        em.refresh(photo);
        em.remove(photo);
    }
    
}

