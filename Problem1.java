// Time Complexity : O(1) for put and get operations
// Space Complexity : O(2n) where n is the capacity of the cache
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Using a hashmap to maintain the key and Node pair. Node has frequency associated to it.
 * Using a map of LinkedHashSet to maintain the frequency of each node so that we can do a O(1) lookup for minimum value. Also maintaining the currMin frequency count.
 * LinkedHashSet is used for putting the most recently used entry for that frequency count in last. So when a node needs to be removed for LFU, we can just take the first entry out off of the LinkedHashSet
 */
class LFUCache {
    class Node {
        int value;
        int freq = 1;
    }

    Map<Integer, Node> map;
    Map<Integer, LinkedHashSet<Integer>> freqMap;
    int capacity;
    int currMin = 0;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        Node node = map.get(key);
        // remove from old frequency entry since get function call should increase the count by one
        LinkedHashSet<Integer> freqKeys = freqMap.get(node.freq);
        freqKeys.remove(key);
        if (freqKeys.isEmpty()) {
            if (currMin == node.freq) currMin++;
        }
        // increase the frequency count and add it to new frequency
        node.freq++;
        if (!freqMap.containsKey(node.freq)) {
            freqMap.put(node.freq, new LinkedHashSet<>());
        }
        freqMap.get(node.freq).add(key);

        return node.value;
    }

    public void put(int key, int value) {
        // existing entry
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            map.put(key, node);
            freqMap.get(node.freq).add(key);
            // to increase the freq count
            get(key);
        } // new entry
        else {
            Node node = new Node();
            node.value = value;
            // capacity not reached
            if (map.size() < capacity) {
                if (!freqMap.containsKey(node.freq)) {
                    freqMap.put(node.freq, new LinkedHashSet<>());
                }
            }
            // capacity reached
            else {
                LinkedHashSet<Integer> minKeys = freqMap.get(currMin);
                int minKey = minKeys.iterator().next();
                map.remove(minKey);
                minKeys.remove(minKey);
            }
            // new entry will have the frequency set to 1
            map.put(key, node);
            freqMap.get(node.freq).add(key);
            currMin = 1;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */