package util.arrays;

public class Arrays {
    public static int search(int[] ar, int key){
        int index = 0;
        while(index < ar.length && key != ar[index]) {
            index++;
        }
        
        return index == ar.length ? -1 : index;
    }
    public static int[] add(int [] ar, int number) {
        int [] res = java.util.Arrays.copyOf(ar, ar.length + 1);
        res[ar.length] = number;
        
        return res;
    }

    public static int[] insert(int[] ar, int index, int number) {
        int[] newAr = new int[ar.length + 1];

            System.arraycopy(ar, 0, newAr, 0, index - 1);
            newAr[index] = number;
            System.arraycopy(ar, index, newAr, index+1, ar.length + 1);

        return newAr; 
    }
    public static int[] remove(int[] numbers, int index) {
        int[] newNum = new int[numbers.length - 1];

            System.arraycopy(numbers, 0, newNum, 0, index - 1);
            System.arraycopy(numbers, index+1, newNum, index, numbers.length - 1);

        return newNum;
    
    }
}
