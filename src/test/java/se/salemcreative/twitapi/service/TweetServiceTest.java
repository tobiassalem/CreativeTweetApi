package se.salemcreative.twitapi.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import se.salemcreative.twitapi.jpa.TweetRepository;
import se.salemcreative.twitapi.jpa.UserRepository;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class TweetServiceTest {

    private final Logger log = LoggerFactory.getLogger(TweetServiceTest.class);

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceJpaImpl();
        }

        @Bean
        public TweetService tweetService() {
            return new TweetServiceJpaImpl();
        }

        @Bean
        public SessionService sessionService() {
            return new SessionServiceMockImpl();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private SessionService sessionService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TweetRepository tweetRepository;

    final User hobbit = new User("Frodo");
    final User wizard = new User("Gandalf");
    final Long id = 1L;

    @Before
    public void setUp() {
        Tweet t1 = new Tweet(hobbit, "There and back again, a hobbit's tale.");
        Tweet t2 = new Tweet(wizard, "A wizard arrives exactly when he means to");
        List<Tweet> all = new ArrayList<>();
        all.add(t1);
        all.add(t2);
        Mockito.when(tweetRepository.findAll())
                .thenReturn(all);
        Mockito.when(tweetRepository.findByAuthorUserName("Frodo"))
                .thenReturn(Arrays.asList(t1));
        Mockito.when(tweetRepository.findByAuthorUserName("Gandalf"))
                .thenReturn(Arrays.asList(t2));
        Mockito.when(tweetRepository.findById(id))
                .thenReturn(Optional.of(t1));
    }

    @Test
    public void findAll() {
        List<Tweet> all = tweetService.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void findById() {
        tweetService.findById(id);
        Mockito.verify(tweetRepository, Mockito.times(1)).findById(Mockito.eq(id));
    }

    @Test
    public void findByUserName() {
        List<Tweet> byHobbitUserName = tweetService.findByUserName("Frodo");
        assertEquals(1, byHobbitUserName.size());
        assertAllByAuthor(byHobbitUserName, "Frodo");

        List<Tweet> byWizardUserName = tweetService.findByUserName("Gandalf");
        assertEquals(1, byWizardUserName.size());
        assertAllByAuthor(byWizardUserName, "Gandalf");
    }

    @Test
    public void tweet() {
        tweetService.tweet(hobbit, "Once upon a time there was a hobbit");

        Mockito.verify(tweetRepository, Mockito.times(1)).save(Mockito.any(Tweet.class));
    }

    private void assertAllByAuthor(List<Tweet> tweets, String author) {
        for (Tweet t : tweets) {
            assertEquals(author, t.getAuthor().getUserName());
        }
    }
}
