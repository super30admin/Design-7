//Tc: O(1)
//Sc: O(n
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUCache {

    private int capacity;
    private int minFreq = 0;
    private Map<Integer, Integer> keyToVal = new HashMap<>();
    private Map<Integer, Integer> keyToFreq = new HashMap<>();
    private Map<Integer, LinkedHashSet<Integer>> freqToLRUKeys = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!keyToVal.containsKey(key))
            return -1;

        final int freq = keyToFreq.get(key);
        freqToLRUKeys.get(freq).remove(key);
        if (freq == minFreq && freqToLRUKeys.get(freq).isEmpty()) {
            freqToLRUKeys.remove(freq);
            ++minFreq;
        }

        putFreq(key, freq + 1);
        return keyToVal.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0)
            return;
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, value);
            get(key); 
        }

        if (keyToVal.size() == capacity) {
            final int keyToEvict = freqToLRUKeys.get(minFreq).iterator().next();
            freqToLRUKeys.get(minFreq).remove(keyToEvict);
            keyToVal.remove(keyToEvict);
        }

        minFreq = 1;
        putFreq(key, minFreq);
        keyToVal.put(key, value);
    }

    private void putFreq(int key, int freq) {
        keyToFreq.put(key, freq);
        freqToLRUKeys.putIfAbsent(freq, new LinkedHashSet<>());
        freqToLRUKeys.get(freq).add(key);
    }
}