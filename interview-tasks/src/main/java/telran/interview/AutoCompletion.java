package telran.interview;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletion {
    private final List<String> words = new ArrayList<>();

    public boolean addWord(String word) {
		//adds new word into auto-completion variants
		//returns true if added, false otherwise (if a given word already exists)
		if (!words.contains(word)) {
            words.add(word);
            return true;
        }
        return false;
    }

    public String[] getVariants(String prefix) {
		//returns all words beginning with a given prefix
		//Complexity of finding the variants is O[logN]
		return words.stream()
                .filter(word -> word.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toArray(String[]::new);
    }
}