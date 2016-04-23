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
 *
 * @author Onder
 */
@Stateless
public class SubscriptionFacade extends AbstractFacade<Subscription> {

    @PersistenceContext(unitName = "VTLocatorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubscriptionFacade() {
        super(Subscription.class);
    }
 
    public List<Subscription> getFromCategory(String category) {
        return em.createNamedQuery("Subscription.findByCategory").setParameter("category", category).getResultList();
    }
    
    public Subscription getById(int id) {
         List<Subscription> users = em.createNamedQuery("Subscription.findById").setParameter("id", id).getResultList();
         if (!users.isEmpty()) {
             return users.get(0);
         }
         return null;
    }
    
    public void deleteBySub(int id) {
        Subscription sub = this.getById(id);
        em.remove(sub);
    }
    
    public List<Subscription> getByUserId(User user) {
        return em.createQuery("SELECT s FROM Subscription s WHERE s.subscriber = :subscriber").setParameter("subscriber", user).getResultList();
    }
}
