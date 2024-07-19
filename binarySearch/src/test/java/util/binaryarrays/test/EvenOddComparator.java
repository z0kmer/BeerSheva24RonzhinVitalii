package util.binaryarrays.test;

import java.util.Comparator;

public class EvenOddComparator implements Comparator<Integer>{

    @Override
    public int compare(Integer arg0, Integer arg1) {
        return (arg0 % 2 == arg1 % 2) ? Integer.compare(arg0 % 2 == 0 ? arg0 : -arg0, arg1 % 2 == 0 ? arg1 : -arg1) : (arg0 % 2 == 0 ? -1 : 1);
    }

}