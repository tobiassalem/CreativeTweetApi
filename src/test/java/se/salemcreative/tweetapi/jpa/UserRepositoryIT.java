package se.salemcreative.tweetapi.jpa;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.salemcreative.tweetapi.model.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
public class UserRepositoryIT {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUserName_thenReturnUser() {
        // given
        User human = new User("Aragorn");
        entityManager.persist(human);
        entityManager.flush();

        // when
        User found = userRepository.findByUserName(human.getUserName());

        // then
        assertEquals(human.getUserName(), found.getUserName());
    }

    @Test
    public void whenFollowerUser_thenExistInFollowing() {
        // given
        User human = new User("Boromir");
        User wizard = new User("Ozzie");
        human.followUser(wizard);
        entityManager.persist(human);
        entityManager.persist(wizard);
        entityManager.flush();

        // when
        User found = userRepository.findByUserName(human.getUserName());
        log.info("found.getFollowing(): {}", found.getFollowing());

        // then
        assertEquals(found.getUserName(), human.getUserName());
        assertEquals(1, found.getFollowing().size());
        log.info("found user: {} who is following: ", found);
        for (User u : found.getFollowing()) {
            log.info("{}", u);
        }
        log.info("We now compare with {} contains? {}", wizard, found.getFollowing().contains(wizard));
        assertTrue(human.getFollowing().contains(wizard));
    }

    @Test
    public void whenFollowerUser_thenExistAsFollower() {
        // given
        User elf = new User("Legolas");
        User wizard = new User("Ozzie");
        elf.followUser(wizard);
        entityManager.persist(elf);
        entityManager.persist(wizard);
        entityManager.flush();

        // when
        User foundElf = userRepository.findByUserName(elf.getUserName());
        User foundWizard = userRepository.findByUserName(wizard.getUserName());

        // Alt.1 - read directly from entity with @ManyToMany relation. Most elegant solution.
        Set<User> foundFollowers = foundWizard.getFollowers();

        // Alt.2 - find by JPA query
        //Set<User> foundFollowers = userRepository.findFollowers(foundWizard);

        // Alt.3 - find by native query
        //Set<User> foundFollowers = userRepository.findFollowersNative(foundWizard.getId());

        // then
        log.debug("Nr of followers of Gandalf: {}", foundFollowers.size());
        log.debug("foundFollowers.contains(foundElf): {}", foundFollowers.contains(foundElf));
        assertEquals(foundElf.getUserName(), elf.getUserName());
        assertEquals(foundWizard.getUserName(), wizard.getUserName());
        assertEquals(1, foundElf.getFollowing().size());
        assertEquals(0, foundElf.getFollowers().size());
        assertEquals(0, foundWizard.getFollowing().size());
        assertEquals(1, foundWizard.getFollowers().size());
        assertTrue(foundFollowers.contains(foundElf));
    }
}
