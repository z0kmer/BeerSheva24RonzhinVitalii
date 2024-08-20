package telran.stream;

import java.util.Random;
import java.util.Optional;

public class StreamTasks {
    public static int[] shuffle(int [] arr){
        //how to int to stream in java =)) use this metod
        //only one pipeline for getting new array of int whith all numbers from a given array but with different order each method call returns new array in some random order
        
        Random random = new Random();
        return Optional.of(arr)
                    .filter(a -> a.length > 0)
                    .map(a -> random.ints(0, a.length)
                                    .distinct()
                                    .limit(a.length)
                                    .map(i -> a[i])
                                    .toArray())
                    .orElse(new int[0]);
    }
}
