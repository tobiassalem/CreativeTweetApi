package se.salemcreative.twitapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salemcreative.twitapi.exception.TweetApiSystemException;
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
            return byId.get();
        } else {
            throw new TweetApiSystemException("Tweet with id " + id + " does not exist.");
        }
    }

    @Override
    public void tweet(User user, String message) {
        Tweet tweet = new Tweet(user, message);
        tweetRepository.save(tweet);
    }

    @Override
    public void reply(User user, String message, Long inReplyToId) {
        Tweet tweet = new Tweet(user, message);
        Tweet inReplyTo = findById(inReplyToId);
        inReplyTo.addReply(tweet);
        tweetRepository.save(tweet);
    }

}
