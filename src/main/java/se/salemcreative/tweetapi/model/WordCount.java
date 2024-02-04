package se.salemcreative.tweetapi.model;

import java.io.Serializable;

public class WordCount implements Serializable {

    private static final long serialVersionUID = 2420976298190109611L;
    private String word;
    private Long count;

    public WordCount(String word, Long count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
