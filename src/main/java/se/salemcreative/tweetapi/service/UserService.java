package se.salemcreative.tweetapi.service;


import se.salemcreative.tweetapi.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUserName(String userName);

    Set<User> findFollowers(String userName);

    void followUser(String userName);

    void unFollowUser(String userName);
}
