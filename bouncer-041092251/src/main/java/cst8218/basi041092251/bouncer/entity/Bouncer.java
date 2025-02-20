/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.basi041092251.bouncer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author basil
 */
@Entity
public class Bouncer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Size of the Bouncer (in pixels)
    private int size; 
    
    // Position on the grid
    @NotNull
    @Min(0) @Max(800) // X_LIMIT
    private Integer x;

    @NotNull
    @Min(0) @Max(600) // Y_LIMIT
    private Integer y;
    
    // Maximum travel distance
    private int maxTravel;
    
    // Current movement offset from its original position
    private int currentTravel = 0;
    
    // Direction of movement (1 = right, -1 = left)
    private int mvtDirection = 1;

    // Tracks how many times direction has changed
    private int dirChangeCount = 0;
    
    // Constants
    public static final int INITIAL_SIZE = 30;  // Default size
    public static final int TRAVEL_SPEED = 2;   // Speed of movement
    public static final int MAX_DIR_CHANGES = 10; // Max times direction can change before shrinking
    public static final int DECREASE_RATE = 1;  // How much maxTravel decreases after MAX_DIR_CHANGES
    
     /**
     * Default constructor initializing Bouncer with default values.
     */
    public Bouncer() {
        this.size = INITIAL_SIZE;
        this.maxTravel = INITIAL_SIZE;
    }
    
    public Bouncer(Integer x, Integer y, int size, int maxTravel) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.maxTravel = maxTravel;
    }
    
    /**
    * Updates the properties to simulate the passing of one unit of time.
    */
    public void timeStep() {
        if (maxTravel > 0){ // If Bouncer can still move.
            currentTravel += mvtDirection * TRAVEL_SPEED; // Move in the current direction.
            if (Math.abs(currentTravel) >= maxTravel){ // If the Bouncer reaches the max distance, reverse direction.
                mvtDirection = -mvtDirection; // Reverse direction.
                dirChangeCount++; // Increase direction change counter.
                if (dirChangeCount > MAX_DIR_CHANGES){  // If it changes direction too many times, shrink the maxTravel distance.
                    maxTravel -= DECREASE_RATE; // Reduce maxTravel.
                    dirChangeCount = 0;         // Reset direction change count.
                }
            }
        }
    }
    
    public void updateFrom(Bouncer newBouncer) {
        if (newBouncer.getX() != null) {
            this.x = newBouncer.getX();
        }
        if (newBouncer.getY() != null) {
            this.y = newBouncer.getY();
        }
        if (newBouncer.getSize() > 0) { 
            this.size = newBouncer.getSize();
        }
        if (newBouncer.getMaxTravel() > 0) { 
            this.maxTravel = newBouncer.getMaxTravel();
        }
        if (newBouncer.getCurrentTravel() != 0) { 
            this.currentTravel = newBouncer.getCurrentTravel();
        }
        if (newBouncer.getMvtDirection() != 0) { 
            this.mvtDirection = newBouncer.getMvtDirection();
        }
        if (newBouncer.getDirChangeCount() >= 0) { 
            this.dirChangeCount = newBouncer.getDirChangeCount();
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getX() { 
        return x; 
    }
    
    public void setX(Integer x) {
        this.x = x; 
    }

    public Integer getY() { 
        return y; 
    }
    
    public void setY(Integer y) { 
        this.y = y; 
    }

    public int getSize() { 
        return size; 
    }
    
    public void setSize(int size) { 
        this.size = size; 
    }

    public int getMaxTravel() { 
        return maxTravel; 
    }
    
    public void setMaxTravel(int maxTravel) { 
        this.maxTravel = maxTravel; 
    }

    public int getCurrentTravel() { 
        return currentTravel; 
    }
    
    public void setCurrentTravel(int currentTravel) {
        this.currentTravel = currentTravel;
    }
    
    public int getMvtDirection() { 
    return mvtDirection; 
    }
    public void setMvtDirection(int mvtDirection) { 
        this.mvtDirection = mvtDirection; 
    }

    public int getDirChangeCount() { 
        return dirChangeCount; 
    }
    public void setDirChangeCount(int dirChangeCount) { 
        this.dirChangeCount = dirChangeCount; 
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.basi041092251.bouncer.entity.Bouncer[ id=" + id + " ]";
    }
    
}
