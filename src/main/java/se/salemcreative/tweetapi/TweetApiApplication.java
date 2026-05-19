package se.salemcreative.tweetapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import se.salemcreative.tweetapi.jpa.TweetRepository;
import se.salemcreative.tweetapi.jpa.UserRepository;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.User;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class TweetApiApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    public static void main(String[] args) {
        SpringApplication.run(TweetApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        initUsers();
        initTweets();
    }

    /**
     * Init our Users of the application.
     * NB! With Spring Boot 3 with stricter Hibernate, we need to save them before
     * creating the relations.
     */
    @Transactional
    public void initUsers() {
        // 1. Create users
        User hobbit1 = new User("frodo");
        User hobbit2 = new User("bilbo");
        User hobbit3 = new User("sam");
        User wizard1 = new User("gandalf");
        User wizard2 = new User("saruman");
        User orc1 = new User("grishnack");
        User orc2 = new User("drumph");
        User orc3 = new User("puthin");

        // 2. Save them so they become MANAGED
        userRepository.saveAll(List.of(hobbit1, hobbit2, hobbit3, wizard1, wizard2, orc1, orc2, orc3));

        // 3. Now relationships are safe
        orc1.followUser(wizard2);
        orc2.followUser(wizard2);
        orc3.followUser(wizard2);

        // 4. Save again to persist relationships
        userRepository.saveAll(List.of(orc1, orc2, orc3));
        log.info("Persisted {} nr of users.", userRepository.count());
    }

    private void initUsersSpringBoot2WithLenientHibernate() {
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
        final User hobbitFriend = userRepository.findByUserName("sam");
        final User wizard = userRepository.findByUserName("gandalf");
        final User orc = userRepository.findByUserName("drumph");
        final List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet(hobbit,
                "How do you pick up the threads of an old life, when in your heart you begin to understand, there is no going back... #storytelling"));
        tweets.add(new Tweet(hobbit,
                "I feel that as long as the Shire lies behind, safe and comfortable, I shall wander more bearable... #storytelling"));
        tweets.add(new Tweet(hobbitFriend, "It is no small thing to appreciate the little things in life. #wisdom"));
        tweets.add(new Tweet(wizard, "A wizard arrives precisely when he means to. #wisdom"));
        tweets.add(new Tweet(wizard, "The difference between ignorance and skill is practice. " +
                "The difference between skill and mastery, is time. #wisdom"));
        tweets.add(new Tweet(wizard,
                "It is the small everyday deeds of ordinary folk that keep the darkness at bay. #wisdom"));
        tweets.add(new Tweet(wizard, "The biggest risk is not taking any risk. #wisdom"));
        tweets.add(new Tweet(wizard, "Everything is temporary. #wisdom"));
        tweets.add(new Tweet(wizard, "Beneath hate there is always fear. #wisdom"));
        tweets.add(new Tweet(wizard,
                "Hate is the most useless emotion, destructive to the mind and hurtful to the heart. #wisdom"));
        tweets.add(new Tweet(wizard, "A lifetime is not so long as you think. #wisdom"));
        tweets.add(new Tweet(orc,
                "In my corrupted world, the truth become lies, and twisted lies become truth. #corruption"));

        tweetRepository.saveAll(tweets);
        log.info("Persisted {} nr of tweets.", tweetRepository.count());
    }
}
