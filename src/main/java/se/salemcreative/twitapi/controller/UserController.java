package se.salemcreative.twitapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.salemcreative.twitapi.model.User;
import se.salemcreative.twitapi.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService service;

    @GetMapping("/")
    public List<User> getAllUsers() {
        log.info("Returning all user ");
        return service.findAll();
    }

    @GetMapping("/followers/{userName}")
    public Set<User> getFollowers(@PathVariable("userName") String userName) {
        log.info("Returning followers for user {}", userName);
        return service.findFollowers(userName);
    }

    @PostMapping("/follow/{userName}")
    public void followUser(@PathVariable("userName") String userName) {
        log.info("Following user " + userName);
        service.followUser(userName);
    }

    @PostMapping("/unFollow/{userName}")
    public void unFollowUser(@PathVariable("userName") String userName) {
        log.info("Un-following user " + userName);
        service.unFollowUser(userName);
    }


}
