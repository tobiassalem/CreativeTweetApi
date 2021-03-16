package se.salemcreative.tweetapi.model;

import lombok.Value;
import org.springframework.core.env.Environment;

@Value
public class ProfileData {
  String defaultProfile;
  String activeProfile;
  String applicationName;
  String applicationVersion;
  String applicationAuthor;
  String dbHost;
  String dbUserName;
  String dbPassword;
  String serviceAlphaUrl;
  String serviceAlphaUserName;

  public ProfileData(Environment env) {
    this.defaultProfile = env.getProperty("spring.profiles.default");
    this.activeProfile = env.getProperty("spring.profiles.active");
    this.applicationName = env.getProperty("application.name");
    this.applicationVersion = env.getProperty("application.version");
    this.applicationAuthor = env.getProperty("application.author");
    this.dbHost = env.getProperty("db.host");
    this.dbUserName = env.getProperty("db.username");
    this.dbPassword = env.getProperty("db.password");
    this.serviceAlphaUrl = env.getProperty("service.alpha.url");
    this.serviceAlphaUserName = env.getProperty("service.alpha.username");
  }
}
