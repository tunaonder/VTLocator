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
    
    public List<Item> getAllRecentItems() {
        //select * from Item order by created_at desc limit 3;
        return em.createQuery("SELECT i FROM Item i ORDER BY i.createdAt desc").getResultList();    
    }
    
    public List<Item> getItemsForUser(int userId) {
        return em.createNamedQuery("Item.findByUserCreatorOrdered")
                .setParameter("userId", userId)
                .getResultList();
    }
}
