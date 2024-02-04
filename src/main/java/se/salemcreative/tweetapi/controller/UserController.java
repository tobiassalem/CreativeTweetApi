package se.salemcreative.tweetapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.salemcreative.tweetapi.model.User;
import se.salemcreative.tweetapi.service.SessionService;
import se.salemcreative.tweetapi.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService service;

    @Autowired()
    SessionService sessionService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        log.info("Returning all users");
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("id") Long id) {
        log.info("Returning user by id {}", id);
        return service.findById(id);
    }

    @GetMapping("/followers/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Set<User> getFollowers(@PathVariable("username") String userName) {
        log.info("Returning followers for user {}", userName);
        return service.findFollowers(userName);
    }

    @PostMapping("/follow/{username}")
    @ResponseStatus(HttpStatus.OK)
    public String followUser(@PathVariable("username") String userName) {
        String feedback = "Following user " + userName;
        log.info(feedback);
        service.followUser(userName);
        return feedback;
    }

    @PostMapping("/un-follow/{username}")
    @ResponseStatus(HttpStatus.OK)
    public String unFollowUser(@PathVariable("username") String userName) {
        String feedback = "Un-following user " + userName;
        log.info(feedback);
        service.unFollowUser(userName);
        return feedback;
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public User getActiveUser() {
        User activeUser = sessionService.getActiveUser();
        log.info("Returning active user {}", activeUser);
        return activeUser;
    }


}
