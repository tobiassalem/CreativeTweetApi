package se.salemcreative.twitapi.model;

import java.io.Serializable;

public class KeywordCount implements Serializable {

    private static final long serialVersionUID = 2420976298190109611L;
    private String keyword;
    private Long count;

    public KeywordCount(String year, Long count) {
        this.keyword = year;
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
