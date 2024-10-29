package telran.interview;

import java.util.HashMap;

public class MyArray<T> {
    private final int size;  // Variable to store the size of the array
    private HashMap<Integer, T> array;  // Use HashMap for values
    private HashMap<Integer, Long> versions;  // Use HashMap for versions
    private T defaultValue;
    private long defaultVersion;
    private long currentVersion = 0;

    public MyArray(int size) {
        this.size = size;  // Store the size
        array = new HashMap<>(size);  // Initialize HashMap for values
        versions = new HashMap<>(size);  // Initialize HashMap for versions
    }

    public void setAll(T value) {
        defaultValue = value;
        defaultVersion = ++currentVersion;
    }

    public void set(int index, T value) {
        checkIndex(index);  // Check for valid index
        array.put(index, value);  // Save value in HashMap
        versions.put(index, ++currentVersion);  // Update version in HashMap
    }

    public T get(int index) {
        checkIndex(index);  // Check for valid index
        Long version = versions.get(index);  // Get version from HashMap
        if (version == null || version <= defaultVersion) {
            return defaultValue;
        }
        return array.get(index);  // Return value from HashMap
    }

    // Method to check if the index is within bounds
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
