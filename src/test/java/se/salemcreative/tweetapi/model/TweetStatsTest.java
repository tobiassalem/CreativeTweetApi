package se.salemcreative.tweetapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TweetStatsTest {

    @Test
    public void getKeywordStats() {
        final String keyword = "#wisdom";
        final Long expectedOccurence = 1L;
        TweetStats stats = new TweetStats();
        stats.addKeywordOccurance(keyword);
        stats.getKeywordStats().containsKey(keyword);

        assertEquals(expectedOccurence, stats.getKeywordStats().get(keyword));
    }
}