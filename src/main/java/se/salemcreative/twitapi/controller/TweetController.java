package se.salemcreative.twitapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.service.SessionService;
import se.salemcreative.twitapi.service.TweetService;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final Logger log = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    TweetService service;

    @Autowired
    SessionService sessionService;

    @GetMapping("/")
    public List<Tweet> getAllTweets() {
        log.info("Returning all tweets ");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Tweet getTweetById(@PathVariable("id") Long id) {
        log.info("Returning tweet by id {}", id);
        return service.findById(id);
    }

    @GetMapping("/users/{username}")
    public List<Tweet> getTweetsByUser(@PathVariable("username") String userName) {
        log.info("Returning tweets for user {}", userName);
        return service.findByUserName(userName);
    }

    @PostMapping("/tweet")
    public void tweet(@RequestBody String message) {
        log.info("Current user is tweeting {}", message);
        service.tweet(sessionService.getCurrentUser(), message);
    }

}
