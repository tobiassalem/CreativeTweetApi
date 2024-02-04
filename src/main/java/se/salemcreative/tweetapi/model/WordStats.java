package se.salemcreative.tweetapi.model;

import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class WordStats {

    private Map<String, Long> wordCount = new HashMap<>();

    public void addWordCount(String word, Long count) {
        if (!wordCount.containsKey(word)) {
            wordCount.put(word, count);
        } else {
            Long currentCount = wordCount.get(word);
            Long newCount = currentCount + count;
            wordCount.put(word, newCount);
        }

        // Sort it by value
        this.wordCount = getWordCount();
    }

    public Map<String, Long> getWordCount() {
        Stream<Map.Entry<String, Long>> sorted =
                wordCount.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        return sorted.collect(Collectors.toMap(
                entry -> entry.getKey(), entry -> entry.getValue())
        );
    }
}
