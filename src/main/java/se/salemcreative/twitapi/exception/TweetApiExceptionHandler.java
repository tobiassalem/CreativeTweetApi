package se.salemcreative.twitapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.salemcreative.twitapi.service.SessionService;

/**
 * Class handling our exceptions.
 */
@ControllerAdvice
public class TweetApiExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(TweetApiExceptionHandler.class);

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
            return "Unknown";
        }
    }
}
