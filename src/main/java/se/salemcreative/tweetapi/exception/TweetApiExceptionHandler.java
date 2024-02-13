package se.salemcreative.tweetapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.salemcreative.tweetapi.service.SessionService;

/**
 * Class handling our exceptions.
 */
@ControllerAdvice
@Slf4j
public class TweetApiExceptionHandler {

    @Autowired
    SessionService sessionService;

    @ExceptionHandler(TweetApiUserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    TweetApiResult handleException(final TweetApiUserException e) {
        log.error("---> TweetApiUserException: {}", e.getMessage());
        return new TweetApiResult(getActiveUserName(), e.getMessage(), e.getCause());
    }

    @ExceptionHandler(TweetApiSystemException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    TweetApiResult handleException(final TweetApiSystemException e) {
        log.error("---> TweetApiSystemException: {}", e.getMessage());
        return new TweetApiResult(getActiveUserName(), e.getMessage(), e.getCause());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    TweetApiResult handleException(final UsernameNotFoundException e) {
        log.error("---> UsernameNotFoundException: {}", e.getMessage());
        return new TweetApiResult(getActiveUserName(), e.getMessage(), e.getCause());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    TweetApiResult handleException(final BadCredentialsException e) {
        log.error("---> BadCredentialsException: {}", e.getMessage());
        return new TweetApiResult(getActiveUserName(), e.getMessage(), e.getCause());
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    TweetApiResult handleException(final DisabledException e) {
        log.error("---> DisabledException: {}", e.getMessage());
        return new TweetApiResult(getActiveUserName(), e.getMessage(), e.getCause());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    TweetApiResult handleException(final Exception e) {
        log.error("---> Unknown exception caught: {}", e);
        return new TweetApiResult(getActiveUserName(), e.getMessage(), e.getCause());
    }

    /* =========================== [Helpers] ======================================================================= */

    private String getActiveUserName() {
        if (sessionService.getActiveUser() != null) {
            return sessionService.getActiveUser().getUserName();
        } else {
            return "No user authorized";
        }
    }
}
