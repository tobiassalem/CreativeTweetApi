package se.salemcreative.tweetapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salemcreative.tweetapi.jpa.UserRepository;
import se.salemcreative.tweetapi.model.User;

@Service
@Slf4j
public class SessionServiceMockImpl implements SessionService {

    final String defaultUserName = "frodo";

    private User activeUser;

    @Autowired
    UserRepository userRepository;

    @Override
    public User getActiveUser() {
        if (activeUser == null) {
            activeUser = userRepository.findByUserName(defaultUserName);
        }
        log.info("---> Returning activeUser: {}", activeUser);
        return activeUser;
    }

    @Override
    public void setActiveUser(User user) {
        activeUser = user;
    }

}
