package telran.interview;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class InterviewTasks {
    /**
     * 
     * @param array
     * @param sum
     * @return true if a given array comprises of two number,
     *  summing of which gives the value equaled to a given "sum" value
     */
    static public boolean hasSumTwo(int [] array, int sum) {
        HashSet<Integer> helpers = new HashSet<>();
        int index = 0;
        while(index < array.length && !helpers.contains(sum - array[index])) {
            helpers.add(array[index++]);
        }
        return index < array.length;
    }
    static public int getMaxWithNegativePresentation(int [] array) {
        //TODO
        //returns maximal positive value for which exists negative one with the same abs value
        //if no pair of positive and negative values with the same abs value the method returns -1
        //complexity O[N] only one pass over the elements
        throw new UnsupportedOperationException();
    }
    public static List<DateRole> assignRoleDates(List<DateRole> rolesHistory,
		List<LocalDate> dates) {
	//TODO
    //rolesHistory is the list containg dates and roles assigned to the employees at the appropriate dates
    //for example, date => 2019-01-01, role => Developer means that some employee (no matter who) was taken
    //role Developer at 2019-01-01
	//create List<DateRole> with roles matching with the given dates
	//most effective data structure
    throw new UnsupportedOperationException();
}
public static boolean isAnagram(String word, String anagram) {
	//TODO
	//returns true if "anagram" string contains all
	// letters from "word" in another order (case sensitive)
	//O[N] (sorting is disallowed)
    throw new UnsupportedOperationException();
}
    
}