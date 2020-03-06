package se.salemcreative.twitapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salemcreative.twitapi.exception.TweetApiSystemException;
import se.salemcreative.twitapi.jpa.UserRepository;
import se.salemcreative.twitapi.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceJpaImpl implements UserService {

    final Logger log = LoggerFactory.getLogger(UserServiceJpaImpl.class.getSimpleName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionService sessionService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new TweetApiSystemException("User with id " + id + " does not exist.");
        }
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Set<User> findFollowers(String userName) {
        User byUserName = userRepository.findByUserName(userName);
        return byUserName.getFollowers();
    }

    @Override
    public void followUser(String userName) {
        User currentUser = sessionService.getActiveUser();
        User userToFollow = findByUserName(userName);

        log.info("currentUser: {}", currentUser);
        log.info("userToFollow: {}", userToFollow);
        log.info("currentUser is following {} others: ", currentUser.getFollowing().size());
        for (User u : currentUser.getFollowing()) {
            log.info("user: {}", u);
            log.info("user.equals(userToFollow)? {}", u.equals(userToFollow));
        }

        currentUser.followUser(userToFollow);
        userRepository.save(currentUser);
    }

    @Override
    public void unFollowUser(String userName) {
        User activeUser = sessionService.getActiveUser();
        User userToUnFollow = findByUserName(userName);
        activeUser.unFollowUser(userToUnFollow);
        userRepository.save(activeUser);
    }

}
