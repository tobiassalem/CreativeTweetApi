package se.salemcreative.twitapi.exception;

/**
 * Exception class for system exceptions in the TweetApi.
 * That is, runtime exceptions that normally should not occur.
 */
public class TweetApiSystemException extends TweetApiException {

    protected TweetApiSystemException() {
    }

    public TweetApiSystemException(String s) {
        super(s);
    }

    public TweetApiSystemException(Throwable throwable) {
        super(throwable);
    }

    public TweetApiSystemException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
