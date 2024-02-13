package se.salemcreative.tweetapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import se.salemcreative.tweetapi.model.User;
import se.salemcreative.tweetapi.security.JwtTokenUtil;
import se.salemcreative.tweetapi.security.JwtUserDetailsService;

@Slf4j
public abstract class AbstractControllerTest {

    protected static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    protected static final String JWT_PREFIX = "Bearer ";

    protected final User hobbit = new User("frodo");
    protected final User wizard = new User("gandalf");

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    protected String generateToken(String userName) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        final String token = jwtTokenUtil.generateToken(userDetails);
        log.info("---> Generated token for user {}: {} ", userName, token);
        return token;
    }

}
