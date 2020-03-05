package se.salemcreative.twitapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salemcreative.twitapi.jpa.TweetRepository;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.User;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Tweet findById(Long id) {
        Optional<Tweet> byId = tweetRepository.findById(id);
        if (byId.isPresent()) {
            return  byId.get();
        } else {
            throw new RuntimeException("No Tweet by id " +id+"  exists");
        }
    }

    @Override
    public void tweet(User user, String message) {
        Tweet tweet = new Tweet(user, message);
        tweetRepository.save(tweet);
    }

    @Override
    public void reply(User user, String message, Tweet inReplyTo) {
        Tweet tweet = new Tweet(user, message);
        tweet.setInReplyTo(inReplyTo);
        tweetRepository.save(tweet);
    }

}
