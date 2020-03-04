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
    final String userName = "Frodo";

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        User currentUser = userRepository.findByUserName(userName);
        log.info("---> Returning user: {}", currentUser);
        return currentUser;
    }
}
