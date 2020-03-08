package se.salemcreative.twitapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.TweetStats;
import se.salemcreative.twitapi.model.User;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetServiceIT extends AbstractServiceTest {

    private final Logger log = LoggerFactory.getLogger(TweetServiceIT.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private SessionService sessionService;

    @Test
    public void findAll() {
        List<Tweet> all = tweetService.findAll();
        assertEquals(12, all.size());
    }

    @Test
    public void findById() {
        final Long id = 1L;
        Tweet tweet = tweetService.findById(id);
        assertEquals(id, tweet.getId());
    }

    @Test
    public void findByUserName() {
        List<Tweet> byUserName = tweetService.findByUserName("frodo");
        assertEquals(2, byUserName.size());
    }

    @Test
    @Transactional
    public void tweet() {
        final String userName = wizard.getUserName();
        final User user = userService.findByUserName(userName);
        int tweetCountBefore = tweetService.findByUserName(userName).size();

        tweetService.tweet(user, "A wizard arrives exactly when he means to");

        int tweetCountAfter = tweetService.findByUserName(userName).size();
        assertEquals(tweetCountBefore + 1, tweetCountAfter);
    }

    @Test
    @Transactional
    public void reply() {
        final Long inReplyToId = 1L;
        final String userName = wizard.getUserName();
        final User user = userService.findByUserName(userName);
        int replyCountBefore = getReplyCount(inReplyToId);

        tweetService.reply(user, "Yes I agree", inReplyToId);

        int replyCountAfter = getReplyCount(inReplyToId);
        assertEquals(replyCountBefore + 1, replyCountAfter);
    }

    @Test
    public void getKeywordStats() {
        final int expectedNrOfKeywords = 3;
        final Long expectedWisdomCount = 9L;
        final Long expectedStorytellingCount = 2L;
        final Long expectedCorruptionCount = 1L;
        TweetStats tweetStats = tweetService.getTweetStats();

        assertEquals(expectedNrOfKeywords, tweetStats.getKeywordStats().size());
        assertEquals(expectedWisdomCount, tweetStats.getKeywordStats().get("#wisdom"));
        assertEquals(expectedStorytellingCount, tweetStats.getKeywordStats().get("#storytelling"));
        assertEquals(expectedCorruptionCount, tweetStats.getKeywordStats().get("#corruption"));
    }

    @Test
    public void getUserStats() {
        final Long expectedHobbitCount = 2L;
        final Long expectedWizardCount = 8L;
        final Long expectedOrcCount = 1L;
        TweetStats tweetStats = tweetService.getTweetStats();

        assertEquals(expectedHobbitCount, tweetStats.getUserStats().get(hobbit.getUserName()));
        assertEquals(expectedWizardCount, tweetStats.getUserStats().get(wizard.getUserName()));
        assertEquals(expectedOrcCount, tweetStats.getUserStats().get(orc.getUserName()));
    }

    private int getReplyCount(final Long id) {
        Tweet byId = tweetService.findById(id);
        return byId.getReplies().size();
    }
}

