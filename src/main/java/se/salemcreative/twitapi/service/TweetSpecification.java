package se.salemcreative.twitapi.service;

import org.springframework.data.jpa.domain.Specification;
import se.salemcreative.twitapi.model.Tweet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TweetSpecification {

    public static Specification<Tweet> hasMessageLikeIgnoringCase(final String message) {

        return (Specification<Tweet>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("message")), "%"+message+"%");
    }
}
