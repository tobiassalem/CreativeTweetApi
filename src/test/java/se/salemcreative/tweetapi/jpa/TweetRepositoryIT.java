package se.salemcreative.tweetapi.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.User;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TweetRepositoryIT {

    private final Logger log = LoggerFactory.getLogger(TweetRepositoryIT.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TweetRepository repo;

    @Test
    public void findByAuthorUserName() {
        // Setup
        User author = new User("Aragorn");
        entityManager.persist(author);
        entityManager.flush();

        Tweet tweet = new Tweet(author, "Never give up!");
        repo.save(tweet);

        // Call
        List<Tweet> byUserUserName = repo.findByAuthorUserNameOrderByTimestampDesc(author.getUserName());

        // Assert
        assertEquals(1, byUserUserName.size());
        for (Tweet t : byUserUserName) {
            assertEquals(author.getUserName(), t.getAuthor().getUserName());
        }
    }

    @Test
    public void countByMessageContainingIgnoreCase() {
        // Setup
        final Long expectedHobbitCount = 0L;
        final Long expectedWizardCount = 1L;

        // Call
        Long hobbitCount = repo.countByMessageContainingIgnoreCase("hobbit");
        Long wizardCount = repo.countByMessageContainingIgnoreCase("wizard");

        // Assert
        assertEquals(expectedHobbitCount, hobbitCount);
        assertEquals(expectedWizardCount, wizardCount);
    }

}
