package telran.util;

public class RecursionMethods {
    public static void f(int a) {
        if (a > 3) {
            f(a - 1);
        }
    }
    public static long factorial(int n) {
        //n! = 1 * 2 * 3 *.....*n
        if (x < 0) {
            x = -x;
        }
        return x == 0 ? 0 : x + x - 1 + square(x - 1);
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
        return pow(num, degree, 1, 0);
    }

    private static long pow(int num, int degree, long result, int sum) {
        long res = result;
        if (degree == 0) {
            res = result;
        } else if (sum == num) {
            res = pow(num, degree - 1, result + result, 0);
        } else {
            res = pow(num, degree, result, sum + 1);
        }
        return res;
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
        if(x < 0) {
            x = -x;
        }
        return x == 0 ? 1 : x + x - 1 + square (x - 1);
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
        } else if (matches(string, subString, index, 0)) {
            result = true;
        } else {
            result = isSubstringHelper(string, subString, index + 1);
        }
        return result;
    }
    private static boolean matches(String string, String subString, int index, int subIndex) {
        boolean result;
        if (subIndex == subString.length()) {
            result = true;
        } else if (string.charAt(index + subIndex) != subString.charAt(subIndex)) {
            result = false;
        } else {
            result = matches(string, subString, index, subIndex + 1);
        }
        return result;
    }
}
