package telran.stream;

import java.util.Random;
import java.util.stream.IntStream;

public class StreamTasks {
    public static int[] shuffle(int [] arr){
        //how to int to stream in java =)) use this metod
        //only one pipeline for getting new array of int whith all numbers from a given array but with different order each method call returns new array in some random order

        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        Random random = new Random();
        return IntStream.of(arr)
                        .boxed()
                        .sorted((a, b) -> random.nextInt(3) - 1)
                        .mapToInt(Integer::intValue)
                        .toArray();
    }

}
