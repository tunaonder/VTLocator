/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Onder
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //Get entity manager
    protected abstract EntityManager getEntityManager();

    //Create New Entity
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    //Edit the given entity
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    //Remove Entity
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    //Find an object with its id
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    //Find all objects from given entity class and return as a list
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    //Find all objects in a specific range
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    //Return the number of all entities in the corresponding class
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
