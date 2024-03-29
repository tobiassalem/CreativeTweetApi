package se.salemcreative.tweetapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.salemcreative.tweetapi.jpa.TweetRepository;
import se.salemcreative.tweetapi.jpa.UserRepository;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.TweetStats;
import se.salemcreative.tweetapi.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class TweetServiceTest {

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

    @BeforeEach
    public void setUp() {
        Tweet t1 = new Tweet(hobbit, "There and back again, a hobbit's tale.");
        Tweet t2 = new Tweet(wizard, "A wizard arrives exactly when he means to");
        List<Tweet> all = new ArrayList<>();
        all.add(t1);
        all.add(t2);

        Mockito.when(tweetRepository.findAllByOrderByTimestampDesc())
                .thenReturn(all);
        Mockito.when(tweetRepository.findByAuthorUserNameOrderByTimestampDesc("Frodo"))
                .thenReturn(Arrays.asList(t1));
        Mockito.when(tweetRepository.findByAuthorUserNameOrderByTimestampDesc("Gandalf"))
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

    @Test
    public void reply() {
        tweetService.reply(hobbit, "Yes I agree...", 1L);
        Mockito.verify(tweetRepository, Mockito.times(1)).save(Mockito.any(Tweet.class));
    }

    public void getTweetStats() {
        TweetStats tweetStats = tweetService.getTweetStats();
        Mockito.verify(tweetRepository, Mockito.times(1)).findAll();
    }

    private void assertAllByAuthor(List<Tweet> tweets, String author) {
        for (Tweet t : tweets) {
            assertEquals(author, t.getAuthor().getUserName());
        }
    }
}
