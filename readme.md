## CreativeTweetApi

A simple application to illustrate how to build a backend API. It is built using Spring Boot and the Maven build manager.
Feature wise this is a Twitter-like API/service, which supports the following requests:
* Authenticate user: POST /authenticate [Request body is user object with username and password]
* Get a list of users: GET /users/
* Get user by id: GET /users/{id}
* Get a list of people following a user: GET /users/followers/{username}
* Follow a user: POST /users/follow/{username}
* Un-follow a user: POST /users/un-follow/{username}
* Get a list of tweets: GET /tweets/
* Get tweet by id: GET /tweets/{id}
* Get a list of tweets by user: GET /tweets/{username}
* Post a tweet: POST /tweets/tweet [Request body is the message]
 
Note that all requests now are required to have the access token given by the /authenticate request.
This access token should be present as a http header with details as stated below. 
* key "Authorization" [http standard]
* value on the form "Bearer <token>" [JWT standard]

Concretely, see the Postman collection for details. We here naturally set up authorization on the collection level,
so we do not need to set it on each individual request (when using Postman).

The token lives for 2 hours. Remember that when the token times out, or you authenticate another user with /authenticate,
you need to update the access token (on the Postman collection, or on individual requests).
 
### Building
* Install Maven if not already present on your system (if you have an IDE like IntelliJ you're all set).
* Build with mvn install.

### Running
* Run the Spring Boot application file in your favorite IDE. You run it with java -jar target/tasklist-service-1.0-SNAPSHOT.jar server tasklist-service.yml.
* Access the REST services on http://localhost:8080/
* The application is hosted on Heroku as https://creative-tweet-api.herokuapp.com
* To perform the service calls a REST client like Postman is highly recommended. A Postman collection is included for your convenience.

### Possible improvements
The possible improvements to the application are naturally many. The most relevant can easily be the following.

* On getting tweets of a user, include both self-tweet and replies by followers.
* Implement more features, like creating users, replying, finding the highest trending keyword.
* Implement an exception management.
* Improve user and session management. Concretely a logout feature which invalidates the last access token.

### Implemented improvements
* Tweet feature.
* Authentication with access tokens [concretely JWT].
* User and session management.
        
### References
* Spring Boot - @See https://spring.io/projects/spring-boot
* Maven build manager - @See https://maven.apache.org/
* Heroku cloud platform - @See https://www.heroku.com/
* Postman REST client - @See https://www.postman.com/
* Clean Code by Robert C. Martin - @See https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882