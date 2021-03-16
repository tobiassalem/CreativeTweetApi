package se.salemcreative.tweetapi.service;

import se.salemcreative.tweetapi.model.User;

public interface SessionService {

    User getActiveUser();

    void setActiveUser(User user);
}
