package telran.stream;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.err.println();
        new Random().ints(1, 50) //из промижутка от 1 до 49 (включительно границы) сгенерить случайные числа
        .distinct().limit(6).forEach(n ->System.err.print(n + " ")); //получить только 6 таких чисел
    }
}