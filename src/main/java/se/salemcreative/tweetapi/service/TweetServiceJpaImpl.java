package se.salemcreative.tweetapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import se.salemcreative.tweetapi.exception.TweetApiSystemException;
import se.salemcreative.tweetapi.jpa.TweetRepository;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.TweetStats;
import se.salemcreative.tweetapi.model.User;
import se.salemcreative.tweetapi.model.WordStats;

import java.util.*;

@Service
@Slf4j
public class TweetServiceJpaImpl implements TweetService {

    @Autowired
    TweetRepository repo;

    @Override
    public List<Tweet> findAll() {
        return repo.findAllByOrderByTimestampDesc();
    }

    @Override
    public List<Tweet> findByUserName(String userName) {
        return repo.findByAuthorUserNameOrderByTimestampDesc(userName);
    }

    @Override
    public Tweet findById(Long id) {
        Optional<Tweet> byId = repo.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new TweetApiSystemException("Tweet with id " + id + " does not exist.");
        }
    }

    @Override
    public void tweet(User user, String message) {
        Tweet tweet = new Tweet(user, message);
        repo.save(tweet);
    }

    @Override
    public void reply(User user, String message, Long inReplyToId) {
        Tweet tweet = new Tweet(user, message);
        Tweet inReplyTo = findById(inReplyToId);
        inReplyTo.addReply(tweet);
        repo.save(tweet);
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

    public WordStats getWordStats() {
        final String whitespaceMetaChar = "\\s+";
        WordStats stats = new WordStats();
        Set<String> words = new HashSet<>();

        List<Tweet> all = findAll();
        all.stream().forEach(t -> {
            words.addAll(Arrays.asList(t.getMessage().toLowerCase().split(whitespaceMetaChar)));
        });

        for (String word : words) {
            stats.addWordCount(word, repo.countByMessageContainingIgnoreCase(word));
        }
        return stats;
    }

    @Override
    public List<Tweet> findByContent(String text) {
        return repo.findByMessageContainingIgnoreCase(text);
    }

    /**
     * Given criteria, we perform Google Style search
     * Example: "once upon a time" there was a hobbit
     */
    @Override
    public List<Tweet> findByCriteria(String criteria) {
        List<String> tokens = ValueHelper.splitStringGoogleStyle(criteria);
        Specification<Tweet> specification = buildTweetSpecification(tokens);
        return repo.findAll(specification);
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
        log.trace("---> Found keyword: {}", keyword);
        return keyword;
    }
}
