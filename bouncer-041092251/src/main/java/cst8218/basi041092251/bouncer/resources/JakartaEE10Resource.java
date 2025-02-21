/*
 * RESTful endpoint to check if the Jakarta EE application is running.
*/
package cst8218.basi041092251.bouncer.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("jakartaee10")
public class JakartaEE10Resource {
    
    /**
     * Handles HTTP GET requests.
     * 
     * When accessed, it returns a simple text response indicating that the server is running.
     *
     * @return An HTTP response with the message "ping Jakarta EE".
     */
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
