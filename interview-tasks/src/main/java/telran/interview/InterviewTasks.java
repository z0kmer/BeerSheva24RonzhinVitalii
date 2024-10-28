package telran.interview;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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
        TreeMap<LocalDate, String> roleMap = new TreeMap<>();
        for (DateRole role : rolesHistory) {
            roleMap.put(role.date(), role.role());
        }

        List<DateRole> result = new ArrayList<>();
        String currentRole = null;

        for (LocalDate date : dates) {
            currentRole = roleMap.floorEntry(date) != null ? roleMap.floorEntry(date).getValue() : currentRole;
            result.add(new DateRole(date, currentRole));
        }

        return result;
    }

    public static boolean isAnagram(String word, String anagram) {
        if (word.equals(anagram)) {
            return false;
        }
        if (word.length() != anagram.length()) {
            return false;
        }
        int[] counts = new int[256];
        for (char c : word.toCharArray()) {
            counts[c]++;
        }
        for (char c : anagram.toCharArray()) {
            if (--counts[c] < 0) {
                return false;
            }
        }
        return true;
    }
}
