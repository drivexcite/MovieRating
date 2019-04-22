Prerequisites:
Java 11 JDK. And the corresponding JAVA_HOME environment variable.
node/npm latest version. Use chocolatey for short.
A modern browser. Chrome for short.

To run the project:
Open a command line prompt with admin privileges.
Switch to the frontend directory and then install Vue and build:
	movieratings\frontend>npm install -g @vue/cli
	movieratings\frontend>npm install
	movieratings\frontend>npm run build
Switch to the parent directory and then run self-hosted in Tomcat:
	movieratings>.\mvnw -projects backend spring-boot:run
Open a browser and go to:
	http://localhost:8088/