package se.salemcreative.tweetapi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.salemcreative.tweetapi.jpa.UserRepository;
import se.salemcreative.tweetapi.service.SessionService;

import java.util.ArrayList;

/**
 * JWTUserDetailsService implements the Spring Security UserDetailsService interface.
 * It overrides the loadUserByUsername for fetching user details from the database using the username.
 * <p>
 * The Spring Security Authentication Manager calls this method for getting the user details from the database when authenticating the user details provided by the user.
 * Here we read users from the database, and simply all give them password 'password' (bcrypted);
 * <p>
 * You can generate bcrypt hashes with https://www.devglan.com/online-tools/bcrypt-hash-generator
 */
@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private static final String BCRYPTED_PASSWORD = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionService sessionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        se.salemcreative.tweetapi.model.User byUserName = userRepository.findByUserName(username);
        log.info("byUserName {}: {} ", username, byUserName);
        
        if (byUserName != null) {
            sessionService.setActiveUser(byUserName);
            return new User(username, BCRYPTED_PASSWORD,
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User with given username not found: " + username);
        }
    }

}
