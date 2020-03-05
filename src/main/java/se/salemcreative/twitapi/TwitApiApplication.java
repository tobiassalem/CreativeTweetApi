package se.salemcreative.twitapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.salemcreative.twitapi.jpa.TweetRepository;
import se.salemcreative.twitapi.jpa.UserRepository;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.User;

import javax.transaction.Transactional;

@SpringBootApplication
public class TwitApiApplication {

    private final Logger log = LoggerFactory.getLogger(TwitApiApplication.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    public static void main(String[] args) {
        SpringApplication.run(TwitApiApplication.class, args);
    }

    @Bean
    @Transactional
    public void initData() {
        initUsers();
        initTweets();
    }

    private void initUsers() {
        final User hobbit1 = new User("frodo");
        final User hobbit2 = new User("bilbo");
        final User hobbit3 = new User("sam");
        final User wizard1 = new User("gandalf");
        final User wizard2 = new User("saruman");
        final User orc1 = new User("grishnack");
        final User orc2 = new User("drumph");
        final User orc3 = new User("puthin");

        orc1.followUser(wizard2);
        orc2.followUser(wizard2);
        orc3.followUser(wizard2);

        userRepository.save(hobbit1);
        userRepository.save(hobbit2);
        userRepository.save(hobbit3);
        userRepository.save(orc1);
        userRepository.save(orc2);
        userRepository.save(orc3);
        userRepository.save(wizard1);
        userRepository.save(wizard2);
        log.info("Persisted {} nr of users.", userRepository.count());
    }

    private void initTweets() {
        final User hobbit = userRepository.findByUserName("frodo");
        final User wizard = userRepository.findByUserName("gandalf");
        final Tweet t1 = new Tweet(hobbit, "There and back again, a hobbit's tale.");
        final Tweet t2 = new Tweet(wizard, "A wizard arrives exactly when he means to");

        tweetRepository.save(t1);
        tweetRepository.save(t2);
        log.info("Persisted {} nr of tweets.", tweetRepository.count());
    }
}
