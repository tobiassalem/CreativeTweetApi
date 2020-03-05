package se.salemcreative.twitapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String index() {
        return "Greetings from the Creative Tweet API!";
    }

}
