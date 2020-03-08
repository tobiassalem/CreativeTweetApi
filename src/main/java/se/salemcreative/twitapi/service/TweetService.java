package se.salemcreative.twitapi.service;

import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.TweetStats;
import se.salemcreative.twitapi.model.User;

import java.util.List;

public interface TweetService {

    List<Tweet> findAll();

    List<Tweet> findByUserName(String userName);

    Tweet findById(Long id);

    void tweet(User user, String message);

    void reply(User user, String message, Long inReplyToId);

    TweetStats getTweetStats();

}
