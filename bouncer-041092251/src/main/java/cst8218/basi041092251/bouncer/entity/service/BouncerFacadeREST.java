/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public BouncerFacadeREST() {
        super(Bouncer.class);
    }
    
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
                existingBouncer.updateFrom(newBouncer);
                super.edit(existingBouncer);
                return Response.ok(existingBouncer).build(); // 200 OK
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

        existingBouncer.updateFrom(newBouncer);
        super.edit(existingBouncer); // Save to database

        return Response.ok(existingBouncer).build(); // 200 OK
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putNotAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("PUT on the entire Bouncer table is not allowed.")
                .build();
    }
    
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
        return Response.noContent().build(); // 204 No Content
    }
    
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
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Bouncer> findAll() {
        return super.findAll();
    }
    
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
