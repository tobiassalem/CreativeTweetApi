package se.salemcreative.tweetapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.salemcreative.tweetapi.model.ProfileData;

@RestController
public class ProfileController {

  @Autowired
  Environment env;

  @GetMapping("/profile")
  public ProfileData getProfileData() {
    return new ProfileData(env);
  }

}
