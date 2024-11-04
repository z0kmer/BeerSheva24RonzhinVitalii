package telran.interview;

import java.util.SortedSet;
import java.util.TreeSet;

public class AutoCompletion {
    private final TreeSet<String> words = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    public boolean addWord(String word) {
        return words.add(word);
    }

    public String[] getVariants(String prefix) {
        // Create an upper limit for a subset
        String upperBound = prefix + Character.MAX_VALUE;
        SortedSet<String> subSet = words.subSet(prefix, upperBound);
        return subSet.toArray(new String[0]);
    }
}
