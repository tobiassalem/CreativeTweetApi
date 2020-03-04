package se.salemcreative.twitapi.service;

import se.salemcreative.twitapi.model.Tweet;

import java.util.List;

public interface TweetService {

    List<Tweet> findAll();

    List<Tweet> findByUserName(String userName);

}
