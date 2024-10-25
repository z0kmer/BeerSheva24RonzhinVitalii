package telran.interview;


//all methods must have complexity O[1]
public class MyArray<T> {
	private T[] array;
    private T defaultValue;
    private long defaultVersion;
    private long[] versions;
    private long currentVersion = 0;

	public MyArray(int size) {
		//creates the Array object for a given size
		//with setting null's at each element
        array = (T[]) new Object[size];
        versions = new long[size];
    }

	public void setAll(T value) {
		//all array's elements should be set with a given value
		defaultValue = value;
        defaultVersion = ++currentVersion;
	}
	public void set(int index, T value) {
		//set new value at a given index
		//throws ArrayIndexOutOfBoundsException for incorrect index
		if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[index] = value;
        versions[index] = ++currentVersion;
	}
	
	public T get(int index) {
		//returns a value at a given index
		//throws ArrayIndexOutOfBoundsException for incorrect index
		if (index < 0 || index >= array.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return versions[index] > defaultVersion ? array[index] : defaultValue;
    }
}