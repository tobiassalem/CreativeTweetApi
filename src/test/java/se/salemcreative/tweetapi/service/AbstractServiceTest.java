package se.salemcreative.tweetapi.service;

import se.salemcreative.tweetapi.model.User;

public abstract class AbstractServiceTest {

    protected final User hobbit = new User("frodo");
    protected final User wizard = new User("gandalf");
    protected final User orc = new User("drumph");
    protected final Long id = 1L;

}
