package se.salemcreative.twitapi.exception;

import org.springframework.lang.Nullable;

/**
 * Exception class for any event that occurs in the TweetApi.
 */
public abstract class TweetApiException extends RuntimeException {

    private static final long serialVersionUID = 2367150756158513058L;

    TweetApiException() {
    }

    TweetApiException(String s) {
        super(s);
    }

    TweetApiException(Throwable throwable) {
        super(throwable);
    }

    TweetApiException(String s, Throwable throwable) {
        super(s, throwable);
    }

    @Nullable
    public <T extends Exception> T getCause(Class<T> clazz) {
        for (Throwable e = this; e != null; e = e.getCause()) {
            if (clazz.isAssignableFrom(e.getClass())) {
                return (T) e;
            }
        }
        return null;
    }
}