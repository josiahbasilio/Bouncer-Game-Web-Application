/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * This class provides a RESTful API for managing Bouncer entities.
 * It handles HTTP requests for creating, updating, deleting, and retrieving Bouncers.
 * 
 * It extends AbstractFacade<Bouncer>, which provides database operations,
 * and is annotated with @Stateless, meaning it does not maintain a session state.
 *
 * Each method is mapped to an HTTP request (POST, PUT, GET, DELETE), allowing
 * interactation with the Bouncer data through a web service.
 */
package cst8218.basi041092251.bouncer.entity.service;

import cst8218.basi041092251.bouncer.entity.Bouncer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author basil
 */
@Stateless
@Path("cst8218.basi041092251.bouncer.entity.bouncer")
public class BouncerFacadeREST extends AbstractFacade<Bouncer> {
    
    @PersistenceContext(unitName = "bouncer_persistence_unit")
    private EntityManager em;

    
    /**
     * Provides the EntityManager instance for database operations.
     * This method is required by AbstractFacade Bouncer.
     * 
     * @return The EntityManager that handles persistence.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    // Default constructor that passes the Bouncer entity type to the parent class 
    public BouncerFacadeREST() {
        super(Bouncer.class);
    }
    
    /**
     * Creates a new Bouncer or updates an existing one.
     * 
     * If the ID is null, a new Bouncer is created and returned with its generated ID.
     * If the ID is not null and exists in the database, updates the existing Bouncer with new values.
     * If the ID does not exist in the database, it returns a 400 Bad Request.
     * 
     * @param newBouncer The Bouncer object to be created or updated.
     * @return HTTP Response with the created or updated Bouncer.
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createOrUpdateBouncer(Bouncer newBouncer) {
        if (newBouncer.getId() == null) {
            // Creating a new Bouncer
            super.create(newBouncer);
            return Response.status(Response.Status.CREATED).entity(newBouncer).build();
        } else {
            // Updating an existing Bouncer
            Bouncer existingBouncer = super.find(newBouncer.getId());
            if (existingBouncer != null) {
                existingBouncer.updateFromNonNull(newBouncer); // Preserve old values
                super.edit(existingBouncer);
                return Response.ok(existingBouncer).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Bouncer with ID " + newBouncer.getId() + " does not exist.")
                        .build();
            }
        }
    }


//    @POST
//    @Override
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void create(Bouncer entity) {
//        super.create(entity);
//    }
    
     /**
     * Completely replaces a Bouncer at a specific ID.
     * 
     * This method ensures that the ID in the request URL matches the ID in the request body.
     * If the Bouncer does not exist, it returns a 400 Bad Request.
     *
     * @param id The ID of the Bouncer to replace.
     * @param newBouncer The new Bouncer data.
     * @return HTTP Response with the updated Bouncer.
     */
    @POST
@Path("{id}")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public Response replaceBouncer(@PathParam("id") Long id, Bouncer newBouncer) {
    Bouncer existingBouncer = super.find(id);
    if (existingBouncer == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Bouncer with ID " + id + " not found.")
                .build();
    }

    if (newBouncer.getId() != null && !newBouncer.getId().equals(id)) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Mismatched ID in body and URL.")
                .build();
    }

    if (newBouncer.getX() == null || newBouncer.getY() == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("X and Y positions cannot be null.")
                .build();
    }
    
    newBouncer.setId(id);
    super.edit(newBouncer);

    return Response.ok(newBouncer).build();
}
 

//    @PUT
//    @Path("{id}")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void edit(@PathParam("id") Long id, Bouncer entity) {
//        super.edit(entity);
//    }

    /**
     * Updates specific fields of a Bouncer while keeping old values.
     * 
     * Only the non-null fields from the request body overwrite the existing Bouncer's values.
     * 
     * @param id The ID of the Bouncer to update.
     * @param newBouncer The new Bouncer data.
     * @return HTTP Response with the updated Bouncer.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateBouncer(@PathParam("id") Long id, Bouncer newBouncer) {
        Bouncer existingBouncer = super.find(id);
        if (existingBouncer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bouncer with ID " + id + " not found.")
                    .build();
        }

        if (newBouncer.getId() != null && !newBouncer.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Mismatched ID in body and URL.")
                    .build();
        }

        // Update only non-null fields, preserving old values
        existingBouncer.updateFromNonNull(newBouncer);
        super.edit(existingBouncer);

        return Response.ok(existingBouncer).build();
    }

    
    /**
     * Blocks the use of PUT on the entire collection.
     * 
     * @return 405 Method Not Allowed
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putNotAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("PUT on the entire Bouncer table is not allowed.")
                .build();
    }
    
     /**
     * Deletes a specific Bouncer from the database.
     * If the Bouncer does not exist, it returns 404 Not Found.
     *
     * @param id The ID of the Bouncer to remove.
     * @return HTTP Response with no content if deletion is successful.
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Bouncer with ID " + id + " not found.")
                    .build();
        }
        super.remove(bouncer);
        return Response.noContent().build(); 
    }
    
     /**
     * Retrieves a specific Bouncer by ID.
     * 
     * @param id The ID of the Bouncer.
     * @return HTTP Response with the Bouncer if found, otherwise 404 Not Found.
     */
     @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Bouncer with ID " + id + " not found.")
                    .build();
        }
        return Response.ok(bouncer).build();
    }
    
    /**
     * Retrieves all Bouncers from the database.
     * 
     * @return A list of all Bouncers.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Bouncer> findAll() {
        return super.findAll();
    }
    
     /**
     * Returns the total number of Bouncers in the database.
     * 
     * @return The count as a plain text response.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countBouncers() {
        int count = super.count();
        return Response.ok(count).build();
    }
    
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        super.remove(super.find(id));
//    }

//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Bouncer find(@PathParam("id") Long id) {
//        return super.find(id);
//    }
//
//    @GET
//    @Override
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Bouncer> findAll() {
//        return super.findAll();
//    }
//
//    @GET
//    @Path("{from}/{to}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
    
}
