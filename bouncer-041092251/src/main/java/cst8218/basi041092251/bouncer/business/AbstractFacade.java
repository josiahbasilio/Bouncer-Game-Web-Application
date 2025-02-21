/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * This abstract class the other entity-specific facades (like BouncerFacade)
 * can inherit these database methods without rewriting them, making the code 
 * more reusable and organized.
 */
package cst8218.basi041092251.bouncer.business;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

     /**
     * Constructor to initialize the entity type that this facade will manage.
     * @param entityClass The class type of the entity (e.g., Bouncer.class)
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Each subclass must provide an EntityManager instance 
     * to handle database operations.
     * @return An EntityManager for database access.
     */
    protected abstract EntityManager getEntityManager();

     /**
     * Saves a new entity to the database.
     * @param entity The object to be stored.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Updates an existing entity in the database.
     * @param entity The updated object.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

     /**
     * Deletes an entity from the database.
     * @param entity The object to be removed.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

     /**
     * Finds an entity by its id.
     * @param id The unique identifier of the entity.
     * @return The entity if found, or null if not found.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Retrieves all records of this entity type from the database.
     * @return A list containing all entities of type T.
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Retrieves a subset of records from the database based on a given range.
     * @param range An array with two integers representing the start and end indices.
     * @return A list of entities within the specified range.
     */
    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Counts the total number of records for this entity type in the database.
     * @return The total number of records.
     */
    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
