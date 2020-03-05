package se.salemcreative.twitapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salemcreative.twitapi.jpa.UserRepository;
import se.salemcreative.twitapi.model.User;

@Service
public class SessionServiceMockImpl implements SessionService {

    final Logger log = LoggerFactory.getLogger(SessionServiceMockImpl.class.getSimpleName());
    final String defaultUserName = "frodo";

    private User activeUser;

    @Autowired
    UserRepository userRepository;

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
