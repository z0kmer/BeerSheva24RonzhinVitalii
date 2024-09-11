package telran.util;

public class RecursionMethods {
    public static void f(int a) {
        if (a > 3) {
            f(a - 1);
        }
    }
    public static long factorial(int n) {
        //n! = 1 * 2 * 3 *.....*n
        if(n < 0) {
            n = -n;
        }
        return n == 0 ? 1 : n * factorial(n - 1);
    }
    /**
     *
     * @param num - any integer number
     * @param degree - any positive number
     * @return num ^ degree
     * limitations:
     * 1. no cycles allowed
     * 2. arithmetic operators + ; - are allowed only
     * 3. bitwise operators like >>, <<, &&, etc disallowe
     */
    public static long pow(int num, int degree){
        return powHelper(num, degree, 1);
}
private static long powHelper(int num, int degree, long result) {
    if (degree == 0) {
        return result;
    }
    return powHelper(num, degree - 1, multiply(num, result));
}
private static long multiply(int a, long b) {
    if (b == 0) {
        return 0;
    }
    if (b > 0) {
        return a + multiply(a, b - 1);
    }
    return -multiply(a, -b);
}
    public static int sum(int [] array) {

        return sum(array, array.length);
    }
    private static int sum(int[] array, int length) {
        return length == 0 ? 0 : array[length - 1] + sum(array, length - 1);
    }
    /**
     *
     * @param x
     * @return x ^ 2
     * limitations:
     * 1. no cycles
     * 2. arithemetic operators only + ; -
     * 3. no bitwise operators
     * 4. no standard and additional methods are allowed
     * 5. no additional fields of the class RecursionMethods are allowed
     */
    public static int square(int x) {
        if (x < 0) {
            x = -x;
        }
        return x == 0 ? 0 : x + square(x - 1) + (x - 1);
    }
    /**
     *
     * @param string
     * @param subString
     * @return true if subString is actually substring of the given string
     * limitations:
     * 1. no cycles
     * 2. the allowed methods of class String are
     *     2.1 charAt(int index)
     *     2.2 length()
     *     2.3 substring(int beginIndex)
     */
    public static boolean isSubstring(String string, String subString) {
        return isSubstringHelper(string, subString, 0);
    }

    private static boolean isSubstringHelper(String string, String subString, int index) {
        boolean result;
        if (subString.length() == 0) {
            result = true;
        } else if (index + subString.length() > string.length()) {
            result = false;
        } else if (string.substring(index, index + subString.length()).equals(subString)) {
            result = true;
        } else {
            result = isSubstringHelper(string, subString, index + 1);
        }
        return result;
    }
}
