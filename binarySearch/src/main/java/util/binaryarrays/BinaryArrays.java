package util.binaryarrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class BinaryArrays {

    /*
    * @param ar - sorted array
    * @param key - being searched number 
    * @return see comments definition
    */

    public static int binarySearch(int [] ar, int key) {

        // index of the search key, if it is contained in the array;
        // otherwise, (-(insertion point) - 1).
        // The insertion point is defined as the point at which the key would be inserted into 
        // the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
        
        int leftSide = 0;
        int rightSide = ar.length;
        int middle = 1;
        int res = -1;

        while (leftSide <= rightSide) {
            middle = leftSide + (rightSide - leftSide) / 2;
            if (ar[middle] == key){
                res = middle;
            }
            if (ar[middle] < key && middle + 1 < ar.length){
                leftSide = middle + 1;
            }
            else{
                rightSide = middle - 1;
            }
        }
        if(middle + 1 >= ar.length){
            res = ar.length - 1;
        }
        if(res < 0){
            res = 0;
        }

        return res;
    }

    public static int[] insertSorted(int[] arSorted, int number) {

        // arSorted is sorted array
        // to insert number at index to keep the array sorted
        // additional sorting is disallowed

        int key = arSorted.length;
        
        int[] newArSorted = Arrays.copyOf(arSorted, arSorted.length + 1);
        
        if(arSorted[arSorted.length - 1] > number){
            key = binarySearch(arSorted, number);
        }

        System.arraycopy(arSorted, key, newArSorted, key + 1, arSorted.length - key);
        newArSorted[key] = number;
        
        return newArSorted;
    }

    /*
    * @param array
    */
    public static boolean isOneSwap(int [] array) {

        // return true if a given array has exactly one swap to get sorted array
        // the swaped array's elements may or may not be neighbors

        int firstPosition = -1;
        int secondPosition = -1;
        int countForSwap = 0;
        boolean res = true;

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]){
                countForSwap++;
                if (countForSwap == 1){
                    firstPosition = i;
                }else{
                    secondPosition = i;
                }
            }
        }
        if (countForSwap > 2){
            res = false;
        }
        if (countForSwap == 0){
            res = false;
        }
        if (countForSwap == 2){
            swap(array, firstPosition - 1, secondPosition);
        }
        else if (countForSwap == 1){
            swap(array, firstPosition - 1, firstPosition);
        }
        for (int i = 1; i < array.length; i++){
            if (array[i] < array[i - 1]){
                res = false;
            }
        }
        return res;
    }
    static int[] swap(int []ar, int i, int j){
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
        return ar;
    }


    /*
    *  An alternative solution to the problem
    *  (I tried to think about what you wrote about a more compact solution.
    *  And the specified array was not changed at the program)
    */

    public static boolean isOneSwapVTwo(int [] array) {
        int firstPosition = -1;
        int secondPosition = -1;
        int countForSwap = 0;
        boolean res = true;

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]){
                countForSwap++;
                if (countForSwap == 1){
                    firstPosition = i;
                }
                if (countForSwap == 2) {
                    secondPosition = i;
                }
            }
        }
        if (countForSwap == 2 && (array[firstPosition - 1] < array[secondPosition - 1] || array[secondPosition] > array[firstPosition])) {
            res = false;
        }
        if (countForSwap == 0 || countForSwap > 2) {
            res = false;
        }
        if((countForSwap == 1 && firstPosition < array.length - 1 && array[firstPosition - 1] > array[firstPosition + 1])){
            res = false;
        }

        return res;
    }

    /*
     * Teachers version
     */

    public static boolean isOneSwapVTeacher(int[] array) {
        // return true if a given array has exactly one swap to get sorted array
        // the swaped array's elements may or may not be neighbors
        boolean res = false;
        int index1 = -1;
        int index2 = 0;
        index1 = getFirstIndex(array);
        if (index1 > -1) {
            index2 = getSecondIndex(array, index1);
            res = isOneSwapCheck(array, index1, index2);
        }
        return res;

    }
    private static boolean isOneSwapCheck(int[] array, int index1, int index2) {
        swap(array, index1, index2);
        boolean res = isArraySorted(array);
        swap(array, index2, index1); //array restored after swap
        return res;
    }
    private static boolean isArraySorted(int[] array) {
        int index = 1;
        while (index < array.length && array[index] >= array[index - 1]) {
            index++;
        }
        return index == array.length;
    }
    private static int getSecondIndex(int[] array, int index1) {
        int index = array.length - 1;
        int lowBorder = index1 + 1;
        while (index > lowBorder && array[index] >= array[index1]) {
            index--;
        }
        
        return index;
        
    }
    private static int getFirstIndex(int[] array) {
        int index = 0;
        int limit = array.length - 1;
        while(index < limit && array[index] <= array[index + 1]) {
            index++;
        }
        return index == limit ? -1 : index;
    }
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        int length = array.length;
        boolean flSort = false;
        do {
            length--;
            flSort = true;
            for(int i = 0; i < length; i++) {
                if(comparator.compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                    flSort = false;
                }
            }
        }while(!flSort);
    }
    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;

    }

    /*
     * BinarySearch
     */

    public static <T> int binarySearch(T[] array, T key, Comparator<T> comp) {
        int left = 0;
        int right = array.length - 1;
        int middle = (left + right) / 2;
        int compRes = 0;
        while (left <= right && (compRes = comp.compare(key, array[middle])) != 0) {
            if (compRes < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
            middle = (left + right) / 2;
        }
        return left > right ? -(left + 1) : middle;
    }

    public static <T> int binarySearch(T[] array, T key) {
        //The code should be base on binarySearch
        return binarySearch(array, key, (Comparator<T>) Comparator.naturalOrder());
    }

    /*
     * Insert, Find, removeIf
     */

    public static <T> T[] insert(T [] array, int index, T item) {
        T[] res = java.util.Arrays.copyOf(array, array.length + 1);
        System.arraycopy(array, index, res, index + 1, array.length - index);
        res[index] = item;
        return res;
    }

    public static <T> T[] find(T[]array, Predicate<T> predicate) {
        T[] result = java.util.Arrays.copyOf(array, 0);
        for(int i = 0; i < array.length; i++) {
            if(predicate.test(array[i])) {
                result = insert(result, result.length, array[i]);
            }
        }
        return result;
    }
    
    public static <T> T[] removeIf(T[] array, Predicate<T> predicate) {
        //using oneline
        return find(array, predicate.negate());
    }
    
}
