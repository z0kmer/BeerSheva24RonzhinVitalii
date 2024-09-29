package telran.interview;

import java.util.HashSet;
import java.util.Set;

public class InterviewTasks {
    /**
     * 
     * @param array
     * @param sum
     * @return true if a given array comprises of two number,
     *  summing of which gives the value equaled to a given "sum" value
     */
    static public boolean hasSumTwo(int [] array, int sum) {
        Set<Integer> seen = new HashSet<>();
        boolean result = false;
        for (int num : array) {
            if (seen.contains(sum - num)) {
                result = true;
            }
            seen.add(num);
        }
        return result;
    }
}