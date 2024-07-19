package util.binaryarrays.test;

import java.util.Comparator;

public class ComparatorNumbers implements Comparator<Integer>{

    @Override
    public int compare(Integer arg0, Integer arg1) {
        return Integer.compare(Math.abs(arg0), Math.abs(arg1));
    }

}