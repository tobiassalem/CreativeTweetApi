package se.salemcreative.twitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User author;

    private String message;

    private LocalDateTime timestamp;

    @JsonIgnore
    @ManyToOne
    private Tweet inReplyTo;

    @OneToMany(
            mappedBy = "inReplyTo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Tweet> replies = new ArrayList<>();

    public Tweet() {}

    public Tweet(User author, String message) {
        this.author = author;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /* ================================== [Accessors] ============================================================== */

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Tweet getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(Tweet inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public List<Tweet> getReplies() {
        return replies;
    }

    public void setReplies(List<Tweet> replies) {
        this.replies = replies;
    }

    /* ================================== [Logic] ================================================================= */

    public void addReply(Tweet comment) {
        replies.add(comment);
        comment.setInReplyTo(this);
    }

    public void removeReply(Tweet comment) {
        replies.remove(comment);
        comment.setInReplyTo(null);
    }

    /* ================================== [Helpers] ================================================================ */

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", author=" + author +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", inReplyTo=" + inReplyTo +
                ", replies=" + replies +
                '}';
    }
}
