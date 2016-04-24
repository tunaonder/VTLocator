/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Onder
 */
@Stateless
public class ItemFacade extends AbstractFacade<Item> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemFacade() {
        super(Item.class);
    }
    
    public Item getItem(int id) {
        return em.find(Item.class, id);
    }
    
    public List<Item> getRecentItems() {
        //select * from Item order by created_at desc limit 3;
        return em.createQuery("SELECT i FROM Item i ORDER BY i.createdAt desc").setMaxResults(3).getResultList();
        
    }
    
    /**
     * Returns a list of items posted by userID
     * @param userID userID of uploader
     * @return List of items
     */
    public List<Item> findItemsByUserID(Integer userID) {
        return (List<Item>) em.createQuery("SELECT i FROM Item i WHERE i.createdBy = :createdBy")
                .setParameter("createdBy", userID)
                .getResultList();
    }
    
    /**
     * Deletes an item with given itemID
     * @param id itemID to delete
     */
    public void deleteByItemID(int id) {
        Item item = (Item) em.createQuery("SELECT i FROM Item i WHERE i.id = :id")
                .setParameter("id", id)
                .getResultList().get(0);
        System.out.println("item name is : " + item.getName());
        em.refresh(item);
        em.remove(item);
    }
}
