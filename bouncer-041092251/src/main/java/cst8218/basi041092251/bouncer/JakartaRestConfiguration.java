/**
 * This class is responsible for configuring Jakarta RESTful Web Services (JAX-RS)
 * for the application. It defines the base path for all REST API endpoints
 */
package cst8218.basi041092251.bouncer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

 // Initialize the RESTful API configuration.
 // Form Authentication annotaion. DO NOT REMOVE.
//@FormAuthenticationMechanismDefinition(
//	loginToContinue = @LoginToContinue(
//	loginPage = "/Login.xhtml",
//	errorPage = "/Login.xhtml")
//)
@BasicAuthenticationMechanismDefinition
@DatabaseIdentityStoreDefinition(
	dataSourceLookup = "${'java:app/MariaDB'}",
	callerQuery = "#{'select password from appuser where userid = ?'}",
	groupsQuery = "select groupname from appuser where userid = ?",
	hashAlgorithm =  Pbkdf2PasswordHash.class,
	priority = 10
)
@Named
@ApplicationScoped
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
}
