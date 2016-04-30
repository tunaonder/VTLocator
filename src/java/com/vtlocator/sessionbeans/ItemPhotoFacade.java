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
 * Contains functions to access the table of Item objects using SQL commands.
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
    /**
     * Returns all photos that have a foreign key with the given item.
     * @param itemID Item object to look up
     * @return A list of photos
     */
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
        ItemPhoto photo = (ItemPhoto) em.createNamedQuery("ItemPhoto.findItemPhotosByItemId")
                .setParameter("id", id)
                .getResultList().get(0);
        em.refresh(photo);
        em.remove(photo);
    }
    
}

