/**
 * This class is responsible for configuring Jakarta RESTful Web Services (JAX-RS)
 * for the application. It defines the base path for all REST API endpoints
 */
package cst8218.basi041092251.bouncer;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

// Initialize the RESTful API configuration
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
}
