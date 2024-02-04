package se.salemcreative.tweetapi.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.WordCount;

import java.util.List;

/**
 * Repo for our Tweets
 * <p>
 * Ref. https://www.objectdb.com/java/jpa/query/jpql/string#like_-_string_pattern_matching_with_wildcards
 * On counting entities & data.
 * Ref. https://stackoverflow.com/questions/10696490/does-spring-data-jpa-have-any-way-to-count-entites-using-method-name-resolving
 */
@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>, JpaSpecificationExecutor<Tweet> {

    List<Tweet> findAllByOrderByTimestampDesc();

    List<Tweet> findByAuthorUserNameOrderByTimestampDesc(String userName);

    List<Tweet> findByMessageContainingIgnoreCase(String message);

    Long countByMessageContainingIgnoreCase(String word);

    /**
     * Calculate word count of tweets
     * <p>
     * TODO: convert the PostgreSQL query to JPQL (alt. fallback to native query).
     * Functions string_to_array and unnest is not supported by JPQL.
     * Ref. https://github.com/h2database/h2database/issues/3799
     * Ref. https://thoughts-on-java.org/hibernate-tips-call-standard-function-jpql-query/
     *
     * @return
     */
    /*
    select word, count(*) occurrence
    from
        (select
          unnest(string_to_array(lower(message),' ')) word
         from message) words
    group by words.word
    order by count(*) desc
    */
//    @Query("SELECT new se.salemcreative.tweetapi.model.KeywordCount(keyword, COUNT(*)) FROM " +
//            "(SELECT substring(message, locate('#', message)) AS keyword FROM Tweet) words " +
//            "GROUP BY keyword " +
//            "ORDER BY count(*) DESC")
    @Query(value = "SELECT word, count(*) occurrence FROM " +
            "(SELECT UNNEST(string_to_array(lower(message),' ')) word FROM tweet) words " +
            "GROUP BY word " +
            "ORDER BY count(*) DESC",
            nativeQuery = true)
    List<WordCount> wordCount();


}
