### Movie rating example ###

* This repository contains a little example of a minumum web application using a Java backend and a modern front end. The backend is built using Spring Boot and an in-memory db. The front end is built using Vue.js and Vuetify.

### How do I get set up? ###

Prerequisites:
 * Java 11 JDK. And the corresponding JAVA_HOME environment variable.
 * node/npm latest version. Use chocolatey for short.
 * A modern browser. Chrome for short.

#### To run the project: ####
 * Open a command line prompt with admin privileges.
 * Switch to the frontend directory and then install Vue and build:
 
	`movieratings\frontend>npm install -g @vue/cli`
	
	`movieratings\frontend>npm install`
	
	`movieratings\frontend>npm run build`
	
 * Switch to the parent directory and then run self-hosted in Tomcat:
 
	`movieratings>.\mvnw -projects backend spring-boot:run`
	
 * Open a browser and go to:
 
	`http://localhost:8088/`