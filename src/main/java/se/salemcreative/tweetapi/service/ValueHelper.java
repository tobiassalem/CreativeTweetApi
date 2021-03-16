package se.salemcreative.tweetapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueHelper {

    /**
     *      * Splits the given input string using Google style used for search.
     *      * That is, split string on spaces, EXCEPT if within quotes.
     *      * Example: "hello world" once upon "a time" there was "a hobbit"
     *      * returns
     *      * ["hello world", "once", "upon", "a time", "there", "was", "a hobbit"]
     * @param input
     * @return
     */
    public static List<String> splitStringGoogleStyle(String input) {
        final List<String> result = new ArrayList<>();
        final Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find()) {
            result.add(m.group(1).replace("\"", ""));
        }
        return result;
    }
}
