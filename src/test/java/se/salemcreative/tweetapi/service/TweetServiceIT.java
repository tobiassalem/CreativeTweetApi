package se.salemcreative.tweetapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.TweetStats;
import se.salemcreative.tweetapi.model.User;
import se.salemcreative.tweetapi.model.WordStats;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TweetServiceIT extends AbstractServiceTest {

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
    public void getTweetStats() {
        // Setup
        final int expectedNrOfKeywords = 3;
        final Long expectedWisdomCount = 9L;
        final Long expectedStorytellingCount = 2L;
        final Long expectedCorruptionCount = 1L;

        // Call
        TweetStats tweetStats = tweetService.getTweetStats();

        // Assert
        assertEquals(expectedNrOfKeywords, tweetStats.getKeywordStats().size());
        assertEquals(expectedWisdomCount, tweetStats.getKeywordStats().get("#wisdom"));
        assertEquals(expectedStorytellingCount, tweetStats.getKeywordStats().get("#storytelling"));
        assertEquals(expectedCorruptionCount, tweetStats.getKeywordStats().get("#corruption"));
    }

    @Test
    public void getWordStats() {
        // Setup
        final int expectedWordCount = 100;
        final Long expectedHobbitCount = null;
        final Long expectedWizardCount = 1L;
        final Long expectedWisdomCount = 9L;
        final Long expectedStorytellingCount = 2L;
        final Long expectedCorruptionCount = 1L;

        // Call
        WordStats wordStats = tweetService.getWordStats();

        // Assert
        assertEquals(expectedWordCount, wordStats.getWordCount().size());
        assertEquals(expectedHobbitCount, wordStats.getWordCount().get("hobbit"));
        assertEquals(expectedWizardCount, wordStats.getWordCount().get("wizard"));
        assertEquals(expectedWisdomCount, wordStats.getWordCount().get("#wisdom"));
        assertEquals(expectedStorytellingCount, wordStats.getWordCount().get("#storytelling"));
        assertEquals(expectedCorruptionCount, wordStats.getWordCount().get("#corruption"));
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

    @Test
    public void findByContent_given_existing_content_ignoring_case_find_expected_matches() {
        final String content = "shire";
        List<Tweet> byContent = tweetService.findByContent(content);
        assertEquals(1, byContent.size());
        assertAllTweetsContain(byContent, content);
    }

    @Test
    public void findByContent_given_non_existing_content_find_no_matches() {
        final String content = "does_not_exist";
        List<Tweet> byContent = tweetService.findByContent(content);
        assertEquals(0, byContent.size());
        assertAllTweetsContain(byContent, content);
    }

    private void assertAllTweetsContain(List<Tweet> byContent, String content) {
        for (Tweet t : byContent) {
            t.getMessage().toLowerCase().contains(content.toLowerCase());
        }
    }

    private int getReplyCount(final Long id) {
        Tweet byId = tweetService.findById(id);
        return byId.getReplies().size();
    }
}

