package se.salemcreative.tweetapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.TweetStats;
import se.salemcreative.tweetapi.model.WordStats;
import se.salemcreative.tweetapi.service.SessionService;
import se.salemcreative.tweetapi.service.TweetService;

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
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> getAllTweets() {
        log.info("Returning all tweets ");
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet getTweetById(@PathVariable("id") Long id) {
        log.info("Returning tweet by id {}", id);
        return service.findById(id);
    }

    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> getTweetsByUser(@PathVariable("username") String userName) {
        log.info("Returning tweets for user {}", userName);
        return service.findByUserName(userName);
    }

    @PostMapping("/tweet")
    @ResponseStatus(HttpStatus.CREATED)
    public void tweet(@RequestBody String message) {
        log.info("Active user is tweeting {}", message);
        service.tweet(sessionService.getActiveUser(), message);
    }

    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public void reply(@PathVariable("id") Long id, @RequestBody String message) {
        log.info("Active user is replying to a tweet {}", message);
        service.reply(sessionService.getActiveUser(), message, id);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public TweetStats getTweetStats() {
        log.info("Returning tweet stats");
        return service.getTweetStats();
    }

    @GetMapping("/stats/words")
    @ResponseStatus(HttpStatus.OK)
    public WordStats getWordStats() {
        log.info("Returning word stats");
        return service.getWordStats();
    }

    @GetMapping("/search/{text}")
    public List<Tweet> searchTweets(@PathVariable("text") String text) {
        return service.findByContent(text);
    }

    @GetMapping("/search/v2/{criteria}")
    public List<Tweet> searchTweetsV2(@PathVariable("criteria") String criteria) {
        return service.findByCriteria(criteria);
    }

}
