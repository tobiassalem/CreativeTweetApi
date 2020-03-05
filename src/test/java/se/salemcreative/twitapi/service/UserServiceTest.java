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
import se.salemcreative.twitapi.jpa.UserRepository;
import se.salemcreative.twitapi.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    private final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceJpaImpl();
        }

        @Bean
        public SessionService sessionService() {
            return new SessionServiceMockImpl();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @MockBean
    private UserRepository userRepository;

    final User hobbit = new User("Frodo");
    final User wizard = new User("Gandalf");
    final Long id = 1L;

    @Before
    public void setUp() {
        Mockito.when(userRepository.findByUserName(hobbit.getUserName()))
                .thenReturn(hobbit);
        Mockito.when(userRepository.findByUserName(wizard.getUserName()))
                .thenReturn(wizard);
        Mockito.when(userRepository.findAll())
                .thenReturn(Arrays.asList(hobbit, wizard));
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(hobbit));
    }

    @Test
    public void findAll() {
        List<User> all = userService.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void findById() {
        userService.findById(id);
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.eq(id));
    }

    @Test
    public void findByUserName() {
        String userName = "Frodo";
        User found = userService.findByUserName(userName);

        assertEquals(userName, found.getUserName());
    }

    @Test
    public void followUser() {
        String userToFollow = "Gandalf";
        userService.followUser(userToFollow);

        Set<User> followers = userService.findFollowers(userToFollow);
        User currentUser = sessionService.getCurrentUser();

        log.info("followers.size " + followers.size());
        log.info("currentUser: " + currentUser);
        log.info("hobbit: " + hobbit);
        log.info("hobbit.equals(currentUser) " + hobbit.equals(currentUser));
        assertTrue(followers.contains(currentUser));
    }

    @Test
    public void unFollowUser() {
        String userToUnFollow = "Gandalf";
        userService.unFollowUser(userToUnFollow);

        Set<User> followers = userService.findFollowers(userToUnFollow);
        assertFalse(followers.contains(sessionService.getCurrentUser()));
    }
}
