package se.salemcreative.twitapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.service.TweetService;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final Logger log = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    TweetService service;

    @GetMapping("/")
    public List<Tweet> getAllTweets() {
        log.info("Returning all tweets ");
        return service.findAll();
    }

    @GetMapping("/{userName}")
    public List<Tweet> getTweetsByUser(@PathVariable("username") String userName) {
        log.info("Returning tweets for user {}", userName);
        return service.findByUserName(userName);
    }

}
