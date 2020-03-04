package se.salemcreative.twitapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salemcreative.twitapi.jpa.TweetRepository;
import se.salemcreative.twitapi.model.Tweet;

import java.util.List;

@Service
public class TweetServiceJpaImpl implements TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public List<Tweet> findByUserName(String userName) {
        return tweetRepository.findByAuthorUserName(userName);
    }
}
