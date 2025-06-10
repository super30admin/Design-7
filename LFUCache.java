// Time complexity: O(1)
// Space Complexity: O(capacity)
import java.util.*;

class LFUCache {
    private final int capacity;
    private int minFreq;
    private final Map<Integer, Integer> keyToVal;
    private final Map<Integer, Integer> keyToFreq;
    private final Map<Integer, LinkedHashSet<Integer>> freqToKeys;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToVal = new HashMap<>();
        this.keyToFreq = new HashMap<>();
        this.freqToKeys = new HashMap<>();
    }

    public int get(int key) {
        if (!keyToVal.containsKey(key)) return -1;

        // Increase frequency
        int freq = keyToFreq.get(key);
        keyToFreq.put(key, freq + 1);

        // Remove key from old freq list
        freqToKeys.get(freq).remove(key);
        // If this freq list is empty and freq == minFreq, increment minFreq
        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            if (minFreq == freq) minFreq++;
        }

        // Add key to new freq list
        freqToKeys.computeIfAbsent(freq + 1, ignore -> new LinkedHashSet<>()).add(key);

        return keyToVal.get(key);
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;

        if (keyToVal.containsKey(key)) {
            // Update value and frequency
            keyToVal.put(key, value);
            get(key); // reuse get to update frequency
            return;
        }

        if (keyToVal.size() >= capacity) {
            // Evict least frequently used key
            LinkedHashSet<Integer> keys = freqToKeys.get(minFreq);
            int evictKey = keys.iterator().next();
            keys.remove(evictKey);
            if (keys.isEmpty()) freqToKeys.remove(minFreq);

            keyToVal.remove(evictKey);
            keyToFreq.remove(evictKey);
        }

        // Insert new key
        keyToVal.put(key, value);
        keyToFreq.put(key, 1);
        freqToKeys.computeIfAbsent(1, ignore -> new LinkedHashSet<>()).add(key);
        minFreq = 1;  // reset minFreq to 1 for new key
    }
}
