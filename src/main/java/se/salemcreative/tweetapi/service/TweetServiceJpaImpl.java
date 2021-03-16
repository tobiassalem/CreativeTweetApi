package se.salemcreative.tweetapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import se.salemcreative.tweetapi.exception.TweetApiSystemException;
import se.salemcreative.tweetapi.jpa.TweetRepository;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.TweetStats;
import se.salemcreative.tweetapi.model.User;

import java.util.ArrayList;
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

    @Override
    public TweetStats getTweetStats() {
        TweetStats stats = new TweetStats();

        List<Tweet> all = findAll();
        for (Tweet t : all) {
            stats.addUserOccurence(t.getAuthor().getUserName());
            String message = t.getMessage();
            if (message.contains("#")) {
                String keyword = extractKeyword(message);
                stats.addKeywordOccurance(keyword);
            }
        }
        return stats;
    }

    @Override
    public List<Tweet> findByContent(String text) {
        return tweetRepository.findByMessageContainingIgnoreCase(text);
    }

    /**
     * Given criteria, we perform Google Style search
     * Example: "once upon a time" there was a hobbit
     */
    @Override
    public List<Tweet> findByCriteria(String criteria) {
        List<String> tokens = ValueHelper.splitStringGoogleStyle(criteria);
        Specification<Tweet> specification = buildTweetSpecification(tokens);
        return tweetRepository.findAll(specification);
    }

    private Specification<Tweet> buildTweetSpecification(List<String> tokens) {
        List<Specification<Tweet>> andSpecifications = new ArrayList<>();
        for (String t : tokens) {
            andSpecifications.add(TweetSpecification.hasMessageLikeIgnoringCase(t));
        }
        return buildCompoundAndSpecification(andSpecifications);
    }

    private Specification<Tweet> buildCompoundAndSpecification(List<Specification<Tweet>> andSpecifications) {
        Specification<Tweet> compoundAndSpec = null;
        boolean firstSpec = true;
        for (Specification<Tweet> spec : andSpecifications) {
            if (firstSpec) {
                compoundAndSpec = Specification.where(spec);
            } else {
                compoundAndSpec = compoundAndSpec.and(spec);
            }
        }
        return compoundAndSpec;
    }

    private String extractKeyword(String message) {
        int startIndex = message.indexOf("#");
        int endIndex = message.indexOf(" ", startIndex);
        String keyword;
        if (endIndex > 0) {
            keyword = message.substring(startIndex, endIndex);
        } else {
            keyword = message.substring(startIndex);
        }
        System.out.println("---> Found keyword: " + keyword);
        return keyword;
    }
}
