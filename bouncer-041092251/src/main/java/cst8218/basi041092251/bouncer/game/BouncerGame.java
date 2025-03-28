/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package cst8218.basi041092251.bouncer.game;

import cst8218.basi041092251.bouncer.business.BouncerFacade;
import cst8218.basi041092251.bouncer.entity.Bouncer;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author basil
 */
@Singleton  // one instance
@Startup    // initializes immediately after deployment 
public class BouncerGame {
    @Inject // inject instance, responsible for CRUD operation on Boucer entities.
    private BouncerFacade bouncerFacade;  // Injects BouncerFacade to manage database
    
    private static final int CHANGE_RATE = 60;  // Updates per second, 60FPS in games.
    private static final long SLEEP_TIME = (long) (1000 / CHANGE_RATE);  // Convert to milliseconds
    
    /**
     * Starts the game loop after deployment.
     */
    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateBouncers();
                    try {
                        Thread.sleep(SLEEP_TIME);  // Wait before next update
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
     /**
     * Retrieves all Bouncers from the database, updates their movement,
     * and saves changes back to the database.
     */
    private void updateBouncers() {
        List<Bouncer> bouncers = bouncerFacade.findAll();
        for (Bouncer bouncer : bouncers) {
            bouncer.timeStep();  // Update Bouncer movement
            bouncerFacade.edit(bouncer);  // Save changes to database
        }
    }
}
