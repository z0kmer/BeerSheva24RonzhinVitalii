package util.binaryarrays;

import java.util.Arrays;

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

    while (leftSide <= rightSide) {
        int middle = leftSide + (rightSide - leftSide) / 2;
        if (ar[middle] == key)
            return middle;
        if (ar[middle] < key)
            leftSide = middle + 1;
        else
            rightSide = middle - 1;
    }

    return -1;
}

public static int[] insertSorted(int[] arSorted, int number) {

    // arSorted is sorted array
    // to insert number at index to keep the array sorted
    // additional sorting is disallowed

    int[] newArSorted = Arrays.copyOf(arSorted, arSorted.length + 1);
    int i;
    for (i = 0; i < arSorted.length; i++) {
        if (arSorted[i] > number) {
            break;
        }
    }
    System.arraycopy(arSorted, i, newArSorted, i + 1, arSorted.length - i);
    newArSorted[i] = number;
    
    return newArSorted;
}

/*
 * @param array
 */
public static boolean isOneSwap(int [] array) {

    // return true if a given array has exactly one swap to get sorted array
    // the swaped array's elements may or may not be neighbors 

    int firstPosition = 0;
    int secondPosition = 0;
    int countForSwap = 0;

    for (int i = 1; i < array.length; i++) {
        if (array[i] < array[i - 1]){
            countForSwap++;
            if (firstPosition == 0){
                firstPosition = i;
            }else{
                secondPosition = i;
            }
        }
    }
    if (countForSwap > 2){
        return false;
    }
    if (countForSwap == 0){
        return false;
    }
    if (countForSwap == 2){
        swap(array, firstPosition - 1, secondPosition);
    }
    else if (countForSwap == 1){
        swap(array, firstPosition - 1, firstPosition);
    }
    for (int i = 1; i < array.length; i++){
        if (array[i] < array[i - 1]){
            return false;
        }
    }
    return true;
}
static int[] swap(int []ar, int i, int j){
    int temp = ar[i];
    ar[i] = ar[j];
    ar[j] = temp;
    return ar;
}
}