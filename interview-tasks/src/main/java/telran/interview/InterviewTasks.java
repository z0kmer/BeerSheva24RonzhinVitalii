package telran.interview;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InterviewTasks {

    /** 
     * @param array 
     * @param sum 
     * @return true if a given array comprises of two number, 
     * summing of which gives the value equaled to a given "sum" value 
     */
    public static boolean hasSumTwo(int[] array, int sum) {
        Set<Integer> complements = new HashSet<>();
        for (int num : array) {
            if (complements.contains(num)) {
                return true;
            }
            complements.add(sum - num);
        }
        return false;
    }

    public static int getMaxWithNegativePresentation(int[] array) {
        //returns maximal positive value for which exists negative one with the same abs value
        //if no pair of positive and negative values with the same abs value the method returns -1
        //complexity O[N] only one pass over the elements
        Set<Integer> set = new HashSet<>();
        int max = -1;
        for (int num : array) {
            set.add(num);
            if (set.contains(-num)) {
                max = Math.max(max, Math.abs(num));
            }
        }
        return max;
    }

    public static List<DateRole> assignRoleDates(List<DateRole> rolesHistory, List<LocalDate> dates) {
        TreeMap<LocalDate, String> roleMap = rolesHistory.stream() 
            .collect(Collectors.toMap(DateRole::date, DateRole::role, (r1, r2) -> r2, TreeMap::new));
            
        return dates.stream()
            .map(date -> new DateRole(date, roleMap.floorEntry(date) != null ? roleMap.floorEntry(date).getValue() : null))
            .collect(Collectors.toList());
    }

    public static boolean isAnagram(String word, String anagram) {
        if (word.equals(anagram) || word.length() != anagram.length()) {
            return false;
        }
        Map<Character, Integer> charCount = new HashMap<>();
        word.chars().forEach(c -> charCount.merge((char) c, 1, Integer::sum));
        anagram.chars().forEach(c -> charCount.merge((char) c, -1, Integer::sum));
        return charCount.values().stream().allMatch(count -> count == 0);
    }
}
