/*
 * Design and implement a data structure for Least Frequently Used (LFU) cache.
 *
 * TC: O(1) for get and put operations
 * SC: O(n)
 */
import java.util.*;

public class LFUCache {

    // stores key-value pairs
    Map<Integer, Integer> valueStore;

    // stores key-frequency pairs
    Map<Integer, Integer> frequencyMap;

    // stores frequency-keys
    Map<Integer, LinkedHashSet<Integer>> frequencyKeyMap;

    // size of the cache
    int size;

    // capacity of the cache
    int capacity;

    // least frequency
    int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        minFrequency = 0;
        valueStore = new HashMap<>();
        frequencyMap = new HashMap<>();
        frequencyKeyMap = new TreeMap<>();
    }

    public int get(int key) {
        // if key is not present in the cache, return -1,
        if(!valueStore.containsKey(key)) return -1;
        // get the frequency of the key
        int frequency = frequencyMap.get(key);
        // get the keys at this frequency
        Set<Integer> keys = frequencyKeyMap.get(frequency);
        // remove the key from the current frequency
        if(keys.contains(key)) {
            keys.remove(key);
            // if the current frequency is the least frequency and no keys are present at that frequency, update the least frequency
            if (keys.isEmpty() && frequency == minFrequency) {
                minFrequency = frequency + 1;
            }
        }
        // update the frequency of the key
        frequency = frequency + 1;
        // add the key to the new frequency
        frequencyKeyMap.putIfAbsent(frequency, new LinkedHashSet<>());
        // add the key to the front of the set, as it is the most recently used key
        frequencyKeyMap.get(frequency).addFirst(key);
        // update the frequency of the key
        frequencyMap.put(key, frequency);
        // return the value of the key
        return valueStore.get(key);
    }

    public void put(int key, int value) {
        if(valueStore.containsKey(key)) {
            // if the key is already present in the cache,
            // get the frequency of the key
            int frequency = frequencyMap.get(key);
            // get the keys at this frequency
            Set<Integer> keys = frequencyKeyMap.get(frequency);
            // remove the key from the current frequency
            if(keys.contains(key)) {
                keys.remove(key);
                // if the current frequency is the least frequency and no keys are present at that frequency, update the least frequency
                if (keys.isEmpty() && frequency == minFrequency) {
                    minFrequency = frequency + 1;
                }
            }
            // increase the frequency of the key
            frequency = frequency + 1;
            // add the key to the new frequency
            frequencyKeyMap.putIfAbsent(frequency, new LinkedHashSet<>());
            frequencyKeyMap.get(frequency).addFirst(key);
            // update the frequency of the key
            frequencyMap.put(key, frequency);
            // update the value of the key
            valueStore.put(key, value);
            return;
        }
        if(size == capacity) {
            // if the cache is full, remove the least frequently used key
            LinkedHashSet<Integer> lfuKeys = frequencyKeyMap.get(minFrequency);
            // the least frequently used key is the last key in the set
            int removedKey = lfuKeys.removeLast();
            // remove the key from the value store and frequency map
            frequencyMap.remove(removedKey);
            valueStore.remove(removedKey);
            // update the size
            size--;
        }
        // add the key to the cache
        valueStore.put(key, value);
        // update the frequency of the key to 1
        frequencyMap.put(key, 1);
        // update the least frequency to 1
        minFrequency = 1;
        // update the size
        size++;
        // add the key to the frequency-key map
        frequencyKeyMap.putIfAbsent(1, new LinkedHashSet<>());
        frequencyKeyMap.get(1).addFirst(key);
    }
}

