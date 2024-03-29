## CreativeTweetApi

A simple application to illustrate how to build a backend API. It is built using Spring Boot and the Maven build manager.
Feature wise this is a Twitter-like API/service, which supports the following requests:
* Authenticate user: POST /authenticate [Request body is JSON object with username and password]
* Health check: GET /
* Get profile info: GET /profile
* List all users: GET /users/
* List all tweets: GET /tweets/
* Get user by id: GET /users/{id}
* Get a list of people following a user: GET /users/followers/{username}
* Follow a user: POST /users/follow/{username}
* Un-follow a user: POST /users/un-follow/{username}
* Get a list of tweets: GET /tweets/
* Get tweet by id: GET /tweets/{id}
* Get a list of tweets by user: GET /tweets/{username}
* Post a tweet: POST /tweets/tweet [Request body is the message]
* Reply to a tweet: POST /tweets/{id}/reply [Request body is the message]
* Get tweet stats: POST /tweets/stats
* Get word stats: POST /tweets/stats/words
* Search tweets by content: GET /tweets/search/{content}

Note that all requests, naturally except the health check and auth request itself,
are required to have the JWT access token given by the /authenticate request.
This access token should be present as a http header with details as stated below.

* key "Authorization" [http standard]
* value on the form "Bearer <token>" [JWT standard]

Concretely, see the Postman collection for details. We here naturally set up authorization on the collection level,
so we do not need to set it on each individual request (when using Postman).
Existing users are: frodo, bilbo, sam, gandalf, saruman. They all have 'password' as password.

The access token lives for 30 minutes. Remember that when the token times out, or you authenticate another user with
/authenticate,
you need to update the access token (on the Postman collection, or on individual requests).
This can be automated by setting and using a Postman variable.
 
### Building
* Install Maven if not already present on your system (if you have an IDE like IntelliJ you're all set).
* Build with mvn install.

### Running

* Run the Spring Boot application file in your favorite IDE. The main class is TweetApiApplication.
* Access the REST services on http://localhost:8080/
* The application is also hosted on Heroku as https://creative-tweet-api.herokuapp.com
* To perform the service calls a REST client like Postman is highly recommended. A Postman collection is included for
  your convenience.

A typical use case is the following:

* POST {{url}}/authenticate to get an access token to add to all requests
* GET {{url}}/users/active - confirm that you are active user frodo
* GET {{url}}/users/followers/gandalf - confirm gandalf has no followers
* POST {{url}}/users/follow/gandalf - follow gandalf
* GET {{url}}/users/followers/gandalf - confirm gandalf now has one follower
* POST {{url}}/users/un-follow/gandalf - un-follow gandalf
* GET {{url}}/users/followers/gandalf - confirm gandalf again has no followers
* POST {{url}}/tweets/tweet - post a tweet
* GET {{url}}/tweets/users/frodo - read the tweets by frodo

### Possible improvements

The possible application improvements are naturally many. The most relevant can easily be the following.

* Create user feature.
* A more sophisticated search feature. Concretely "Google style" search.
* Improve user and session management. Concretely a logout feature which invalidates the last access token.
* Improve security. Concretely store hashed passwords in the database.
* Implement DTOs or alternatively customized JSON parsers to decide exactly which data to present to the frontend.
* Optimize data transfer given these DTOs. E.g. lightweight objects for listviews. Exclude any properties not relevant
  for the frontend.

### Version 1.1 features

* Follow and unfollow feature.
* When viewing a user, either individually or as a list, the user tweets are included.
* When viewing a tweet, either individually or as a list, the replies are included.
* Tweet stats feature, concretely #keyword stats and active user stats (nr of posts by respective user).
* Word stats feature, simply word count by usage.
* Simple search feature.

### Version 1.0 features

* List users and tweets.
* Tweet and reply feature.
* Authentication with access tokens [concretely JWT].
* User and session management.
* Exception management

### References

* Spring Boot - @See https://spring.io/projects/spring-boot
* Maven build manager - @See https://maven.apache.org/
* Heroku cloud platform - @See https://www.heroku.com/
* Postman REST client - @See https://www.postman.com/
* Clean Code by Robert C. Martin - @See https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882
* JWT: JSON Web Token - @See https://jwt.io/ and @See https://en.wikipedia.org/wiki/JSON_Web_Token