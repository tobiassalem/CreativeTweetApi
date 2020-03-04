package se.salemcreative.twitapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.salemcreative.twitapi.jpa.UserRepository;
import se.salemcreative.twitapi.model.User;

import javax.transaction.Transactional;

@SpringBootApplication
public class TwitApiApplication {

	private final Logger log = LoggerFactory.getLogger(TwitApiApplication.class);

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TwitApiApplication.class, args);
	}

	@Bean
	@Transactional
	public void initUsers() {
		final User hobbit1 = new User("Frodo");
		final User hobbit2 = new User("Bilbo");
		final User hobbit3 = new User("Sam");
		final User wizard1 = new User("Gandalf");
		final User wizard2 = new User("Saruman");
		final User orc1 = new User("Grishnack");
		final User orc2 = new User("Drumph");
		final User orc3 = new User("Puthin");

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
}
