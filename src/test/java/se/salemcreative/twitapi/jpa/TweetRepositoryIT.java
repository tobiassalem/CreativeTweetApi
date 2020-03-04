package se.salemcreative.twitapi.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TweetRepositoryIT {

    private final Logger log = LoggerFactory.getLogger(TweetRepositoryIT.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    public void findByAuthorUserName() {
        // given
        User author = new User("Aragorn");
        entityManager.persist(author);
        entityManager.flush();

        Tweet tweet = new Tweet(author, "Never give up!");
        tweetRepository.save(tweet);

        // when
        List<Tweet> byUserUserName = tweetRepository.findByAuthorUserName(author.getUserName());
        assertEquals(1, byUserUserName.size());

        // then
        for (Tweet t : byUserUserName) {
            assertEquals(author.getUserName(), t.getAuthor().getUserName());
        }
    }

}
