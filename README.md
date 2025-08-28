# Bouncer Game Web Application (Jakarta EE / Java)

An interactive Jakarta EE web app to manage animated Bouncer entities. The system exposes JSF pages (UI) and a secured RESTful API, with an EJB business layer and JPA persistence. A React page visualizes/animates bouncers on a canvas and supports basic edits.

## Assignment Context

This project implements: secure JSF and REST, add an account admin area (AppUsers), internationalize a JSF form, add meaningful tests (JUnit & Selenium), and build a React page that displays/animates Bouncers and allows making changes. Group-size add-ons (Swing/Mobile) depend on team size.

## Features

- Security & Access Control
  - Basic Authentication protects both JSF pages and REST resources
  - Role-based access:
    - RestGroup (Admin): REST API 
    - JsfGroup (Admin): JSF Bouncer pages
    - Admin: both above and AppUser admin area
    - Others: only public index page
- Account Administration (JSF)
  - JSF admin pages to manage AppUser accounts & groups
- Internationalization
  - Language selector on index
- RESTful API (Secured)
  - CRUD for Bouncers (secured per groups above)
- React Page
  - Lists bouncers with position/size
  - Canvas animation of Bouncer movement
  - Make changes to bouncers from React
- Testing
  - JUnit unit tests (meaningful method tests)
  - Selenium tests for JSF pages
- Architecture
  - Tiered: JSF (presentation) -> EJB (business) -> JPA/SQL (data)

## Tech Stack

- Language: Java (Jakarta EE)
- Server: GlassFish
- APIs: JSF, EJB, JPA, JAX-RS
- Database: MariaDB (via JDBC DataSource in GlassFish)  
- Frontend: React, Canvas
- Testing: JUnit, Selenium
- Tools: Maven, Postman
