//TC: O(1)
//SC: O(capacity)
class LFUCache {
    class Node {
        int key, value, freq;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;  // Initial frequency is 1 when first added
        }
    }

    private final int capacity;
    private int minFreq;
    private Map<Integer, Node> keyToNode;  // key -> Node
    private Map<Integer, LinkedHashSet<Integer>> freqToKeys;  // freq -> keys with that freq
    private Map<Integer, Integer> keyToFreq;  // key -> frequency of the key

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToNode = new HashMap<>();
        this.freqToKeys = new HashMap<>();
        this.keyToFreq = new HashMap<>();
    }

    public int get(int key) {
        if (!keyToNode.containsKey(key)) return -1;

        Node node = keyToNode.get(key);
        updateFrequency(key);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            node.value = value;  // Update the value
            updateFrequency(key);  // Update the frequency of the key
        } else {
            if (keyToNode.size() >= capacity) {
                evictLFUKey();  // Evict the least frequently used key
            }

            // Insert new key
            Node newNode = new Node(key, value);
            keyToNode.put(key, newNode);
            keyToFreq.put(key, 1);  // Frequency is 1 initially
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFreq = 1;  // Reset the minFreq to 1 when adding a new key
        }
    }

    // Helper function to update the frequency of a key
    private void updateFrequency(int key) {
        int freq = keyToFreq.get(key);
        freqToKeys.get(freq).remove(key);  // Remove key from the current frequency set

        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);  // Clean up if the set is empty
            if (minFreq == freq) {
                minFreq++;  // If this was the minFreq, increment it
            }
        }

        // Add the key to the next frequency set
        keyToFreq.put(key, freq + 1);
        freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
    }

    // Helper function to evict the least frequently used key
    private void evictLFUKey() {
        if (freqToKeys.isEmpty()) return;

        // Get the least frequently used key (minFreq) and the oldest key
        int keyToEvict = freqToKeys.get(minFreq).iterator().next();
        freqToKeys.get(minFreq).remove(keyToEvict);  // Remove the key

        if (freqToKeys.get(minFreq).isEmpty()) {
            freqToKeys.remove(minFreq);  // Clean up if the set is empty
        }

        keyToNode.remove(keyToEvict);  // Remove the key from the cache
        keyToFreq.remove(keyToEvict);  // Remove frequency tracking
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */