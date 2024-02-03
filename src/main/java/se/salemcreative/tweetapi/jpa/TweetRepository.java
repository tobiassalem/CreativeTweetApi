package se.salemcreative.tweetapi.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import se.salemcreative.tweetapi.model.Tweet;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>, JpaSpecificationExecutor<Tweet> {

    List<Tweet> findAllByOrderByTimestampDesc();

    List<Tweet> findByAuthorUserNameOrderByTimestampDesc(String userName);

    List<Tweet> findByMessageContainingIgnoreCase(String message);

    // Calculate word count of tweets
    // TODO: convert this PostgreSQL query to JPQL (alt. fallback to native query).
    // Functions string_to_array and unnest is not supported by JPQL.
    // See https://thoughts-on-java.org/hibernate-tips-call-standard-function-jpql-query/
    /*
    select word, count(*) occurrence
    from
        (select
          unnest(string_to_array(lower(message),' ')) word
         from message) words
    group by words.word
    order by count(*) desc
    */
/*    @Query("SELECT new se.salemcreative.tweetapi.model.KeywordCount(keyword, COUNT(*)) FROM \n" +
            "(SELECT substring(message, locate('#', message)) AS keyword FROM Tweet) words " +
            "GROUP BY keyword " +
            "ORDER BY count(*) DESC")
    List<KeywordCount> countKeywords();*/

}
