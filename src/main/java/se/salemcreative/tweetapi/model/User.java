package se.salemcreative.tweetapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import se.salemcreative.tweetapi.exception.TweetApiUserException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Backtick names that are reserved words
 * Ref. https://www.h2database.com/html/advanced.html#keywords
 */
@Entity
@Table(name = "`User`")
@Slf4j
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true, nullable = false, length = 200)
    private String userName;

    @OneToMany(mappedBy = "author")
    private Set<Tweet> tweets = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "following_relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> following = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "following")
    private Set<User> followers = new HashSet<>();

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    /* ================================== [Accessors] ============================================================== */

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(Set<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    /* ================================== [Logic] ================================================================= */

    public void followUser(User u) {
        if (u == null || u.equals(this)) {
            log.error("User {} tried to follow an invalid user. Non-existent or himself/herself {}", userName, u);
            throw new TweetApiUserException("You can only follow other users! You are trying to follow " + u);
        }
        if (following.contains(u)) {
            log.warn("You are already following {}", u);
            return;
        }

        log.info("Ok, user {} is now following {}", this.userName, u);
        following.add(u);
        u.followers.add(this);
    }

    public void unFollowUser(User u) {
        if (u == null) {
            log.warn("User {} tried to un-follow a non-existent user", userName, u);
            throw new TweetApiUserException("User you tried to un-follow does not exist " + u);
        }
        if (!following.contains(u)) {
            log.warn("You are not following {}", u);
            return;
        }

        following.remove(u);
        u.followers.remove(this);
    }

    /* ================================== [Helpers] =============================================================== */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", following= " + following.size() +
                ", followers= " + followers.size() +
                '}';
    }

    /**
     * This callback hook method is called right after the entity is loaded.
     * We use it to load any JPA relations that are not fetched eagerly.
     * Concretely this solves the Hibernate limitation that it is not possible to eagerly load multiple collections.
     */
    @PostLoad
    public void initRelations() {
        this.tweets.size();
        this.followers.size();
    }
}
