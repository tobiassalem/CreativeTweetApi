package se.salemcreative.twitapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import se.salemcreative.twitapi.model.User;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

    private final Logger log = LoggerFactory.getLogger(UserServiceIT.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Test
    public void followUser() {
        String userName = "Gandalf";
        userService.followUser(userName);

        Set<User> followers = userService.findFollowers(userName);
        User currentUser = sessionService.getCurrentUser();

        log.info("followers.size " + followers.size());
        log.info("currentUser: " + currentUser);
        assertTrue(followers.contains(currentUser));
    }

    @Test
    public void unFollowUser() {
        String userName = "Gandalf";
        userService.unFollowUser(userName);

        Set<User> followers = userService.findFollowers(userName);
        assertFalse(followers.contains(sessionService.getCurrentUser()));
    }
}
