package se.salemcreative.twitapi.service;


import se.salemcreative.twitapi.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    User findByUserName(String userName);

    Set<User> findFollowers(String userName);

    void followUser(String userName);

    void unFollowUser(String userName);
}
