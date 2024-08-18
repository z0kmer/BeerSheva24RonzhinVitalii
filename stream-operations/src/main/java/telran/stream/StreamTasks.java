package telran.stream;

import java.util.Random;
import java.util.stream.IntStream;

public class StreamTasks {
    public static int[] shuffle(int [] arr){
        //how to int to stream in java =)) use this metod
        //only one pipeline for getting new array of int whith all numbers from a given array but with different order each method call returns new array in some random order
        
        Random random = new Random();
        int[] shuffled = arr.clone();
        IntStream.range(0, shuffled.length).forEach(i -> {
            int j = random.nextInt(shuffled.length);
            int temp = shuffled[i];
            shuffled[i] = shuffled[j];
            shuffled[j] = temp;
        });
        return shuffled;
    }
}
