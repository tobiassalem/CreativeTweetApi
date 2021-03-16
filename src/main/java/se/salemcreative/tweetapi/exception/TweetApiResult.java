package se.salemcreative.tweetapi.exception;

import java.io.Serializable;

public class TweetApiResult implements Serializable {

    private static final long serialVersionUID = -2848314553970994382L;

    private final String activeUser;
    private final String message;
    private final Throwable cause;

    public TweetApiResult(String activeUser, String message, Throwable cause) {
        this.activeUser = activeUser;
        this.message = message;
        this.cause = cause;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

}
