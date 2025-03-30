/*
 
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template*/
package Tests;

import cst8218.basi041092251.bouncer.entity.Bouncer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

/**
 *
 
@author matth*/
public class BouncerTest {
    // Bouncer object.
    private Bouncer bouncer;

    // Runs before each test.
    @BeforeEach
    public void setUp() {
        // Initialize Bouncer object.
        bouncer = new Bouncer();
        bouncer.setX(100);
        bouncer.setY(50);
        bouncer.setMaxTravel(90);
        bouncer.setMvtDirection(1);
        bouncer.setCurrentTravel(10);
    }
    
    
    @Test
    @DisplayName("Bouncer should move in +X direction and update currentTravel")
    public void testPositiveDirectionMovement() {
        bouncer.timeStep();
        assertEquals(102, bouncer.getX(), "X should increase by 2 (TRAVEL_SPEED)");
    }
    
    @Test
    @DisplayName("Bouncer's movement direction should be negative.")
    public void testMaximumCurrentTravel() {
        bouncer.setMvtDirection(1);
        bouncer.setCurrentTravel(20);
        bouncer.setMaxTravel(20);
        bouncer.timeStep();
        assertEquals(-1, bouncer.getMvtDirection(), "X should increase by 2 (TRAVEL_SPEED)");
    }

    
    @Test
    @DisplayName("Bouncer should shrink maxTravel after 11 direction changes")
    public void testMaxTravelDecreasesAfterDirectionChanges() {
    bouncer.setMaxTravel(10);
    int originalMaxTravel = bouncer.getMaxTravel();

    for (int i = 0; i < 200; i++) {
        bouncer.timeStep(); // Enough to cause >11 direction changes
    }

    assertTrue(bouncer.getMaxTravel() < originalMaxTravel, "maxTravel should decrease after too many direction changes");
    }
    
    @Test
    @DisplayName("Bouncer's current travel should increase by 50.")
    public void testMultipleDirectionMovement() {
        for (int i = 0; i < 25; i++) {
            bouncer.timeStep();
        }
        assertEquals(150, bouncer.getX(), "X should increase by 50.");
    }
    

    
}
    
    
    
    
    
    
    
    

//    @BeforeAll
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterAll
//    public static void tearDownClass() throws Exception {
//    }
//
//    @BeforeEach
//    public void setUp() throws Exception {
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//    }

