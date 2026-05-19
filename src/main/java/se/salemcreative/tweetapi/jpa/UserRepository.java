package se.salemcreative.tweetapi.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.salemcreative.tweetapi.model.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(@Param("userName") String userName);

    // Alt.1: JPA query with Spring Boot 3 and Hibernate 6
    @Query("SELECT u FROM User u JOIN u.following f WHERE f = :userBeingFollowed")
    Set<User> findByFollowing(@Param("userBeingFollowed") User user);


    // Alt.2
    @Query(nativeQuery = true,
            value = "SELECT * from User u " +
                    "WHERE u.id = following_relation.user_id " +
                    "AND following_relation.following_id = :id ")
    Set<User> findFollowersNative(@Param("id") Long id);
}
