package se.salemcreative.tweetapi.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.salemcreative.tweetapi.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class UserServiceIT extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Test
    public void findAll() {
        List<User> all = userService.findAll();
        assertEquals(8, all.size());
    }

    @Test
    public void findById() {
        final Long id = 1L;
        User byId = userService.findById(id);
        assertEquals(id, byId.getId());
    }

    /**
     * 1) follow user
     * 2) assert user is fallowed
     */
    @Test
    @Transactional
    public void followUser() {
        String userName = wizard.getUserName();
        userService.followUser(userName);

        User currentUser = sessionService.getActiveUser();
        Set<User> followers = userService.findFollowers(userName);

        log.info("activeUser: {}", sessionService.getActiveUser());
        log.info("followers of {} : {} ", userName, followers.size());
        assertTrue(followers.contains(currentUser));
    }

    @Test
    @Transactional
    public void unFollowUser() {
        String userName = wizard.getUserName();
        log.info("activeUser: {}", sessionService.getActiveUser());
        log.info("followers of {} : {} ", userName, userService.findFollowers(userName));
        userService.unFollowUser(userName);

        Set<User> followers = userService.findFollowers(userName);

        log.info("activeUser: {}", sessionService.getActiveUser());
        log.info("followers of {} : {} ", userName, followers);
        assertFalse(followers.contains(sessionService.getActiveUser()));
    }
}
