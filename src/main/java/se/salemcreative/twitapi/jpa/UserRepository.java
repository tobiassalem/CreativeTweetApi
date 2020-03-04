package se.salemcreative.twitapi.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.salemcreative.twitapi.model.User;

import javax.persistence.NamedNativeQuery;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(@Param("userName") String userName);

    // Alt.1 - sadly this does not work
    @Query("SELECT u from User u " +
            "WHERE :userBeingFollowed IN (u.following) ")
    Set<User> findFollowers(@Param("userBeingFollowed") User user);

    // Alt.2
    @Query(nativeQuery = true,
            value = "SELECT * from User u " +
                    "WHERE u.id = following_relation.user_id " +
                    "AND following_relation.following_id = :id ")
    Set<User> findFollowersNative(@Param("id") Long id);
}
