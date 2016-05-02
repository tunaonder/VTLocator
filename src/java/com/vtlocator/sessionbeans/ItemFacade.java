/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.Item;
import java.util.List;

/**
 * This class is used to interact with the Item table in the database.
 * @author Onder
 */
@Stateless
public class ItemFacade extends AbstractFacade<Item> {

    /**
     * This is the entity manager for Item.
     */
    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    /**
     * This returns the entity manager for Item
     * @return em
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Default constructor
     */
    public ItemFacade() {
        super(Item.class);
    }
    
    /**
     * Gets the item based on id passed in
     * @param id id of the item in db
     * @return Item
     */
    public Item getItem(int id) {
        return em.find(Item.class, id);
    }
    
    /**
     * Gets all items in the database
     * @return list of items
     */
    public List<Item> getAllItems() {
        return em.createNamedQuery("Item.findAll")
                .getResultList();    
    }
    
    /**
     * Gets all items in the database in order of recency (only 3)
     * @return list of items
     */
    public List<Item> getRecentItems() {
        //select * from Item order by created_at desc limit 3;
        return em.createQuery("SELECT i FROM Item i ORDER BY i.createdAt desc").setMaxResults(3).getResultList();   
    }
    
    /**
     * This does a fulltext query on the database with the query and filter.
     * @param query type of query
     * @param filter type of filter to be applied
     * @return list of results (items)
     */
    public List<Item> fullTextQuery(String query, String filter) {
        String sqlQuery = "SELECT i FROM Item i WHERE (";
        for (String subQuery : query.split("\\s+")) {
            sqlQuery += ("(i.name LIKE '%" + subQuery + "%' OR i.description LIKE '%" + subQuery + "%') OR ");
        }
        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 4);
        sqlQuery += ")";
        
        System.out.println(filter);
        if (!filter.equals("ALL")) {
            sqlQuery += " AND i.category = '" + filter + "'";
        }
        System.out.println(sqlQuery);
        return getEntityManager().createQuery(sqlQuery).getResultList();
    }
    
    /**
     * Gets all recent items
     * @return list of items
     */
    public List<Item> getAllRecentItems() {
        return em.createQuery("SELECT i FROM Item i ORDER BY i.createdAt desc").getResultList();    
    }
    
    /**
     * Gets all items that were created by the user passed in
     * @param userId the poster of the items
     * @return  list of items
     */
    public List<Item> getItemsForUser(int userId) {
        return em.createNamedQuery("Item.findByUserCreatorOrdered")
                .setParameter("createdBy", userId)
                .getResultList();
    }
    
    /**
     * Returns a list of items posted by userID
     * @param userID userID of uploader
     * @return List of items
     */
    public List<Item> findItemsByUserID(Integer userID) {
        return (List<Item>) em.createQuery("SELECT i FROM Item i WHERE i.createdBy.id = :createdByID ORDER BY i.createdAt desc")
                .setParameter("createdByID", userID)
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
