package se.salemcreative.tweetapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import se.salemcreative.tweetapi.model.User;
import se.salemcreative.tweetapi.security.JwtTokenUtil;
import se.salemcreative.tweetapi.security.JwtUserDetailsService;

public abstract class AbstractControllerTest {

    protected static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION; // "Authorization ";
    protected static final String JWT_PREFIX = "Bearer ";

    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    protected final User hobbit = new User("frodo");
    protected final User wizard = new User("gandalf");

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    protected String generateToken(String userName) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        final String token = jwtTokenUtil.generateToken(userDetails);
        log.info("---> Generated token: " + token);
        return token;
    }

}
