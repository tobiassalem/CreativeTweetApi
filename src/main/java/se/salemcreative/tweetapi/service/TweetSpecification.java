package se.salemcreative.tweetapi.service;

import org.springframework.data.jpa.domain.Specification;
import se.salemcreative.tweetapi.model.Tweet;

public class TweetSpecification {

    public static Specification<Tweet> hasMessageLikeIgnoringCase(final String message) {

        return (Specification<Tweet>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("message")), "%"+message+"%");
    }
}
