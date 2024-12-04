package telran.numbers;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class OneGroupSum implements Callable<Long>{
    private int [] group;
    @Override
    public Long call() throws Exception {
        return Arrays.stream(group).asLongStream().sum();
    }
    public OneGroupSum(int [] group) {
        this.group = group;
    }

}