/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Tests;

import cst8218.basi041092251.bouncer.entity.Bouncer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 */
public class BouncerTest {
	// Bouncer object.
	private Bouncer bouncer;
	
	// Runs before each test.
    @BeforeEach
	/**
	 * Defines and initializes objects and variables used for testing.
	 */
    public void setUp() {
        // Initialize Bouncer object.
        bouncer = new Bouncer();
        bouncer.setX(100);
        bouncer.setY(50);
        bouncer.setMaxTravel(90);
        bouncer.setMvtDirection(1);
        bouncer.setCurrentTravel(10);
    }
	
	// Defines a test method.
    @Test
	// Defines the name of the test displayed to the user.
    @DisplayName("Bouncer's X property should increase by 2 when timeStep() is called.")
	/**
	 * Tests the bouncer's X position when the timeStep() is called.
	 */
    public void testIncreaseXOnceWhenTimeStep() {
		int expectedX = 100 + (1 * 2);
        bouncer.timeStep();
		int actualX = bouncer.getX();
        assertEquals(expectedX, actualX, "X should increase by 2 (TRAVEL_SPEED).");
    }
	
	@Test
    @DisplayName("Bouncer's mvtDirection should be negative when currentTravel equals maxTravel, "
					+ "mvtDirection is positive, and timeStep() is called .")
	/**
	 * Tests the bouncer's movement direction when its current travel distance is equal to
	 * its maximum travel distance, its current movement direction is positive, and the 
	 * timeStep() method is called.
	 */
    public void testNegativeMvtDirectionWhenMaximumCurrentTravel() {
		int expectedMvtDirection = -1;
		bouncer.setCurrentTravel(19);
		bouncer.setMaxTravel(20);
        bouncer.timeStep();
		int actualMvtDirection = bouncer.getMvtDirection();
        assertEquals(expectedMvtDirection, actualMvtDirection, "MvtDirection should be negative.");
    }	
	
	@Test
    @DisplayName("Bouncer's maxTravel should shrink after 11 direction changes.")
    public void testMaxTravelDecreasesAfterDirectionChanges() {
		bouncer.setMaxTravel(10);
		int originalMaxTravel = bouncer.getMaxTravel();

		for (int i = 0; i < 200; i++) {
			bouncer.timeStep(); // Enough to cause >11 direction changes
		}

		assertTrue(bouncer.getMaxTravel() < originalMaxTravel, 
				"maxTravel should decrease after too many direction changes");
    }
	
	@Test
	@DisplayName("Bouncer's current travel should increase by 50 when timeStep() is called"
					+ "25 times.")
	public void testIncreaseXMultipleTimesWhenMultipleTimeStep() {
		int expectedX = 100 + (1 * 2 * 25);
		for (int i = 0; i < 25; i++) {
			bouncer.timeStep();
		}
		int actualX = bouncer.getX();
		assertEquals(expectedX, actualX, "X should increase by 50.");
	}
}
