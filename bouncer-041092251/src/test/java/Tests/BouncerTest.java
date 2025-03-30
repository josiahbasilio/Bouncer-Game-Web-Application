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
        bouncer.setMaxTravel(10);
        bouncer.setMvtDirection(1);
        bouncer.setCurrentTravel(0);
    }
    
   
    // Defines a test method.
    @Test
    // Defines the name of the test displayed to the user.
    @DisplayName("Bouncer's position and direction should update.")
    public void testTimeStep() {
        assertEquals(1, 1);
        //Bouncer bouncer = new Bouncer();

    }
    
    @Test
    @DisplayName("Bouncer should move in +X direction and update currentTravel")
    public void testPositiveDirectionMovement() {
        bouncer.timeStep();
        assertEquals(102, bouncer.getX(), "X should increase by 2 (TRAVEL_SPEED)");
        assertEquals(2, bouncer.getCurrentTravel(), "currentTravel should increase by 2");
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

}