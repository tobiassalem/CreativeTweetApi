package se.salemcreative.tweetapi.service;

import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.TweetStats;
import se.salemcreative.tweetapi.model.User;

import java.util.List;

public interface TweetService {

    List<Tweet> findAll();

    List<Tweet> findByUserName(String userName);

    Tweet findById(Long id);

    void tweet(User user, String message);

    void reply(User user, String message, Long inReplyToId);

    TweetStats getTweetStats();

    List<Tweet> findByContent(String text);

    List<Tweet> findByCriteria(String criteria);
}
