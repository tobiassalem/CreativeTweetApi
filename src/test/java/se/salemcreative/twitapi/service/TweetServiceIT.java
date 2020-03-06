package se.salemcreative.twitapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.salemcreative.twitapi.model.Tweet;
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
        assertEquals(2, all.size());
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
        assertEquals(1, byUserName.size());
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

    private int getReplyCount(final Long id) {
        Tweet byId = tweetService.findById(id);
        return byId.getReplies().size();
    }
}
