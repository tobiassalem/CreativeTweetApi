package se.salemcreative.twitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

    @Transient
    private final Logger log = LoggerFactory.getLogger(User.class);

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
            log.error("You can only follow other users! You are trying to follow {}", u);
            return;
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
            log.warn("No user to unfollow {}", u);
            return;
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

    @PostLoad
    public void initRelations() {
        this.getFollowers().size();
    }
}
