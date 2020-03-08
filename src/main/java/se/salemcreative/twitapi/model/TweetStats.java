package se.salemcreative.twitapi.model;

import java.util.HashMap;
import java.util.Map;

public class TweetStats {

    private Map<String, Long> keywordStats = new HashMap<>();
    private Map<String, Long> userStats = new HashMap<>();

    public TweetStats() {
    }

    public Map<String, Long> getKeywordStats() {
        return keywordStats;
    }

    public Map<String, Long> getUserStats() {
        return userStats;
    }

    public void addKeywordOccurance(String keyword) {
        if (!keywordStats.containsKey(keyword)) {
            keywordStats.put(keyword, 1L);
            return;
        }
        Long currentCount = keywordStats.get(keyword);
        keywordStats.put(keyword, ++currentCount);
    }

    public void addUserOccurence(String userName) {
        if (!userStats.containsKey(userName)) {
            userStats.put(userName, 1L);
            return;
        }
        Long currentCount = userStats.get(userName);
        userStats.put(userName, ++currentCount);
    }
}
