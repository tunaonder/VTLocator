/*
 * Created by VTLocator Group on 2016.04.04  * 
 * Copyright © 2016 VTLocator. All rights reserved. * 
 */
package com.vtlocator.sessionbeans;

import java.util.List;
import javax.persistence.EntityManager;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Get entity manager
     * @return the entity manager instance
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Create New Entity
     * @param entity the entity to create
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Edit the given entity
     * @param entity the entity to edit
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Remove Entity
     * @param entity the entity to remove
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Find an object
     * @param id the object to find
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Find all objects from given entity class and return as a list
     * @return a List of all objects
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Find all objects in a specific range
     * @param the range for which to return entities from
     * @return a list of all entities with the specified range
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Return the number of all entities in the corresponding class
     * @return the number of entities in the corresponding table
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
