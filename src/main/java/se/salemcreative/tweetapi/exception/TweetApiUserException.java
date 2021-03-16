package se.salemcreative.tweetapi.exception;

/**
 * Exception class for user exceptions in the TweetApi.
 * That is, exceptions caused by users of the system. This can be any user: man or machine.
 */
public class TweetApiUserException extends TweetApiException {

    public TweetApiUserException(String s) {
        super(s);
    }

    public TweetApiUserException(String s, Throwable throwable) {
        super(s, throwable);
    }

}


