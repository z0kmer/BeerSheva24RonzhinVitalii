package telran.stream;

import java.util.Random;
import java.util.stream.IntStream;

public class StreamTasks {
    public static int[] shuffle(int [] arr){
        //how to int to stream in java =)) use this metod
        //only one pipeline for getting new array of int whith all numbers from a given array but with different order each method call returns new array in some random order
        
        Random random = new Random();
        return IntStream.range(0, arr.length)
                .map(i -> {
                    int randomIndex = random.nextInt(arr.length);
                    int temp = arr[i];
                    arr[i] = arr[randomIndex];
                    arr[randomIndex] = temp;
                    return i;
                })
                .mapToObj(i -> arr)
                .findFirst()
                .orElse(arr);
    }
}
