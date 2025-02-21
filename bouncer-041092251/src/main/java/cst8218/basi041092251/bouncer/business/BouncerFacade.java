/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package cst8218.basi041092251.bouncer.business;

import cst8218.basi041092251.bouncer.entity.Bouncer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * It extends AbstractFacade Bouncer, inheriting common database 
 * operations like create, update, delete, and fetch.
 * The Stateless annotation indicates that this is an Enterprise JavaBean (EJB),
 * meaning it provides transaction management and allows multiple clients to access 
 * the database without maintaining session states.
 * @author basil
 */
@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {
    @PersistenceContext(unitName = "bouncer_persistence_unit")
    private EntityManager em; // Manages database interactions for Bouncer entities

    /**
     * Provides the EntityManager instance for database operations.
     * This method is required by the AbstractFacade class.
     * @return EntityManager that interacts with the persistence unit.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Constructor that passes the Bouncer entity type to the parent class.
     * This allows AbstractFacade to handle Bouncer-specific database operations.
     */
    public BouncerFacade() {
        super(Bouncer.class);
    }
    
}
