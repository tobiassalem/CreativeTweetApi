package se.salemcreative.twitapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TweetTest {

    final User u1 = new User("Frodo");
    final User u2 = new User("Gandalf");

    @Test
    void getAuthor() {
        Tweet t = new Tweet(u1, "Test");
        assertEquals(u1, t.getAuthor());
    }

    @Test
    void setAuthor() {
        Tweet t = new Tweet(u1, "Test");
        t.setAuthor(u2);
        assertEquals(u2, t.getAuthor());
    }

    @Test
    void getMessage() {
        Tweet t = new Tweet(u1, "Test");
        assertEquals("Test", t.getMessage());
    }

    @Test
    void setMessage() {
        Tweet t = new Tweet(u1, "Test");
        t.setMessage("Test updated");
        assertEquals("Test updated", t.getMessage());
    }

    @Test
    void getTimestamp() {
        Tweet t = new Tweet(u1, "Test");
    }
}