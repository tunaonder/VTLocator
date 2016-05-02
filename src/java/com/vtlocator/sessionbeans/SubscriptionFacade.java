/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.vtlocator.jpaentityclasses.Subscription;
import com.vtlocator.jpaentityclasses.User;
import java.util.List;

/**
 * Class to interact with the Subscription table in the db.
 * @author Onder
 */
@Stateless
public class SubscriptionFacade extends AbstractFacade<Subscription> {

    /**
     * Entity manager for Subscription
     */
    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    /**
     * Gets the entity manager
     * @return em
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Default constructor
     */
    public SubscriptionFacade() {
        super(Subscription.class);
    }
 
    /**
     * Gets all subscriptions by category
     * @param category type of item (ex: PHONE)
     * @return list of subscriptions
     */
    public List<Subscription> getFromCategory(String category) {
        return em.createNamedQuery("Subscription.findByCategory").setParameter("category", category).getResultList();
    }
    
    /**
     * Gets the subscription associated with id
     * @param id id of subscription
     * @return subscription
     */
    public Subscription getById(int id) {
         List<Subscription> users = em.createNamedQuery("Subscription.findById").setParameter("id", id).getResultList();
         if (!users.isEmpty()) {
             return users.get(0);
         }
         return null;
    }
    
    /**
     * Deletes subscription with id of parameter
     * @param id subscription to delete
     */
    public void deleteBySub(int id) {
        Subscription sub = this.getById(id);
        em.remove(sub);
    }
    
    /**
     * Gets all subscriptions for a particular user
     * @param user user to find subscriptions for
     * @return list of subscriptions
     */
    public List<Subscription> getByUserId(User user) {
        return em.createQuery("SELECT s FROM Subscription s WHERE s.subscriber = :subscriber").setParameter("subscriber", user).getResultList();
    }
}
