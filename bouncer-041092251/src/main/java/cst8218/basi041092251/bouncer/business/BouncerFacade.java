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
 *
 * @author basil
 */
@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {
    @PersistenceContext(unitName = "bouncer_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public BouncerFacade() {
        super(Bouncer.class);
    }
    
}
