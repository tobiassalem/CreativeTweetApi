package se.salemcreative.tweetapi.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 8632856135825591194L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
