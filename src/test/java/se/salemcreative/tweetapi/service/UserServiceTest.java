package se.salemcreative.tweetapi.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.salemcreative.tweetapi.jpa.UserRepository;
import se.salemcreative.tweetapi.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Slf4j
public class UserServiceTest extends AbstractServiceTest {

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

    @BeforeEach
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
        String userName = hobbit.getUserName();
        User found = userService.findByUserName(userName);

        assertEquals(userName, found.getUserName());
    }

    @Test
    public void followUser() {
        String userToFollow = wizard.getUserName();
        userService.followUser(userToFollow);

        Set<User> followers = userService.findFollowers(userToFollow);
        User currentUser = sessionService.getActiveUser();

        log.info("followers.size " + followers.size());
        log.info("currentUser: " + currentUser);
        log.info("hobbit: " + hobbit);
        log.info("hobbit.equals(currentUser) " + hobbit.equals(currentUser));
        assertTrue(followers.contains(currentUser));
    }

    @Test
    public void unFollowUser() {
        String userToUnFollow = wizard.getUserName();
        userService.unFollowUser(userToUnFollow);

        Set<User> followers = userService.findFollowers(userToUnFollow);
        assertFalse(followers.contains(sessionService.getActiveUser()));
    }
}
