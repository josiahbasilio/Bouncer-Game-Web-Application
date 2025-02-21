/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.basi041092251.bouncer.entity.service;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * This class instead of writing the same database operations for each entity,
 * this abstract class provides reusable methods for creating, updating, 
 * deleting, and retrieving data. 
 * @author basil
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

     /**
     * Constructor that initializes the entity type for the facade.
     * This allows the generic facade to work with different entity classes.
     * 
     * @param entityClass The class type of the entity for ex: Bouncer.class
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // Subclasses must implement this method to provide an EntityManager 
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
     * Finds and returns an entity by its id.
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
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

     /**
     * Retrieves a specific range of records from the database.
     * @param range An array with two integers representing the start and end indices.
     * @return A list of entities within the specified range.
     */
    public List<T> findRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

     /**
     * Counts the total number of records for this entity type in the database.
     * @return The total number of records.
     */
    public int count() {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        jakarta.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
