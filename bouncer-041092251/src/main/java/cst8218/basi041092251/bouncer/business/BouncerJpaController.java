/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * This class provides direct database access for managing Bouncer entities 
 * using JPA (Java Persistence API). It handles common CRUD operations such as:
 * - Creating a new Bouncer
 * - Updating an existing Bouncer
 * - Deleting a Bouncer
 * - Fetching Bouncer records from the database
 */

package cst8218.basi041092251.bouncer.business;

import cst8218.basi041092251.bouncer.business.exceptions.NonexistentEntityException;
import cst8218.basi041092251.bouncer.business.exceptions.RollbackFailureException;
import cst8218.basi041092251.bouncer.entity.Bouncer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author basil
 */
public class BouncerJpaController implements Serializable {

    /**
     * Constructor initializes the controller with a transaction manager and an entity factory.
     * @param utx The UserTransaction to handle commit/rollback operations.
     * @param emf The EntityManagerFactory used to create database connections.
     */
    public BouncerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    /**
     * Creates a new EntityManager instance to interact with the database.
     * @return A new EntityManager for executing database queries.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Adds a new Bouncer to the database.
     * @param bouncer The new Bouncer object to be stored.
     * @throws RollbackFailureException If a rollback is required due to an error.
     * @throws Exception If any unexpected issue occurs.
     */
    public void create(Bouncer bouncer) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(bouncer);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Updates an existing Bouncer in the database.
     * Ensures the Bouncer exists before attempting to update it.
     * @param bouncer The updated Bouncer object.
     * @throws NonexistentEntityException If the Bouncer does not exist.
     * @throws RollbackFailureException If a rollback is required due to an error.
     * @throws Exception If any unexpected issue occurs.
     */
    public void edit(Bouncer bouncer) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            bouncer = em.merge(bouncer);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = bouncer.getId();
                if (findBouncer(id) == null) {
                    throw new NonexistentEntityException("The bouncer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

     /**
     * Deletes a Bouncer from the database.
     * Ensures the Bouncer exists before attempting to remove it.
     * @param id The ID of the Bouncer to be deleted.
     * @throws NonexistentEntityException If the Bouncer does not exist.
     * @throws RollbackFailureException If a rollback is required due to an error.
     * @throws Exception If any unexpected issue occurs.
     */
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bouncer bouncer;
            try {
                bouncer = em.getReference(Bouncer.class, id);
                bouncer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bouncer with id " + id + " no longer exists.", enfe);
            }
            em.remove(bouncer);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Retrieves all Bouncer records from the database.
     * @return A list of all Bouncers.
     */
    public List<Bouncer> findBouncerEntities() {
        return findBouncerEntities(true, -1, -1);
    }

    /**
     * Retrieves a list of Bouncers from the database.
     * @param maxResults The maximum number of results to return.
     * @param firstResult The starting index for the query.
     * @return A list of Bouncers within the specified range.
     */
    public List<Bouncer> findBouncerEntities(int maxResults, int firstResult) {
        return findBouncerEntities(false, maxResults, firstResult);
    }

    /**
     * Retrieves Bouncers from the database, either all or a limited range.
     * @param all If true, retrieves all records, otherwise, retrieves a subset.
     * @param maxResults The maximum number of results if applicable.
     * @param firstResult The starting index if applicable.
     * @return A list of Bouncers.
     */
    private List<Bouncer> findBouncerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bouncer.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

     /**
     * Finds a specific Bouncer by its ID.
     * @param id The ID of the Bouncer.
     * @return The Bouncer object if found, otherwise null.
     */
    public Bouncer findBouncer(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bouncer.class, id);
        } finally {
            em.close();
        }
    }

    public int getBouncerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bouncer> rt = cq.from(Bouncer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
