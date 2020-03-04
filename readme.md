##CreativeTweetApi

A simple application to illustrate how to build a backend API. It is built using Spring Boot and the Maven build manager.
Feature wise this is a Twitter-like API/service, which supports the following 4 requests:
* Follow a user
* Unfollow a user
* Get a list of people following a user
* Get a list of tweets of a user (including self-tweets and replies by followers)

###Building
* Install Maven if not already present on your system (if you have an IDE like IntelliJ you're all set).
* Build with mvn install.

###Running
* Run the Spring Boot application file in your favorite IDE. You run it with java -jar target/tasklist-service-1.0-SNAPSHOT.jar server tasklist-service.yml.
* Access the REST services on http://localhost:8080/

###Possible improvements
The possible improvements to the application are of course many. The most relevant can easily be the following.

* Implement user and session management. [The active user is now always 'Frodo']
* Implement authentication with tokens.
* Implement more features, like creating users, tweeting, replying, finding the highest trending keyword.
* Implement an exception management with an elegant exception hierarchy.
        
###References
* Spring Boot - @See https://spring.io/projects/spring-boot
* Maven build manager - @See https://maven.apache.org/
* Clean Code by Robert C. Martin - @See https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882