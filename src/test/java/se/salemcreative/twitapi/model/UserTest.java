package se.salemcreative.twitapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUserName() {
        final User user = new User("Frodo");
        assertEquals("Frodo", user.getUserName());
    }

    @Test
    void setUserName() {
        final User user = new User("Frodo");
        user.setUserName("Gandalf");
        assertEquals("Gandalf", user.getUserName());
    }

    @Test
    void followUser() {
        final User u1 = new User("Frodo");
        final User u2 = new User("Gandalf");
        u1.followUser(u2);
        assertTrue(u1.getFollowing().contains(u2));
        assertTrue(u2.getFollowers().contains(u1));
    }

    @Test
    void unFollowUser() {
        final User u1 = new User("Frodo");
        final User u2 = new User("Gandalf");
        u1.followUser(u2);
        u1.unFollowUser(u2);

        assertFalse(u1.getFollowing().contains(u2));
        assertFalse(u2.getFollowers().contains(u1));
    }
}