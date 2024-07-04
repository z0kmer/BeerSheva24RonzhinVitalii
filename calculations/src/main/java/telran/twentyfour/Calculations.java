package telran.twentyfour;

public class Calculations {
    
    public static int sum(int op1, int op2) {
      
    return op1 + op2;
    }

    public static int multiply (int op1, int op2){
        return op1 * op2;
    }

    public static int divde (int op1, int op2){
        if (op2 != 0) {
            return op1 / op2;
        } else {
            return 0;
        }
    }

    public static int subtract (int op1, int op2){
        return op1 - op2;
    }

    public static int sumOfDigits (int number){
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static int maxDigits (int number){
        int maxDigit = 0;

        while (number > 0) {
            int digit = number % 10;
            maxDigit = Math.max(maxDigit, digit);
            number /= 10;
        }
        return maxDigit;
    }

    public static boolean isDividedOn (int number, int dividor){
        return number % dividor == 0;
    }
}