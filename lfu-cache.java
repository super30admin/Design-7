// Time Complexity : O(1) for both get and put operations
// Space Complexity : O(c), where c is the capacity
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : -


// Your code here along with comments explaining your approach

class LFUCache {

    HashMap<Integer, Integer> keyToCount;
    HashMap<Integer, LinkedHashMap<Integer,Integer>> countToLRU;
    int min;
    int capacity;

    public LFUCache(int capacity) {
        keyToCount = new HashMap<>();
        countToLRU = new HashMap<>();
        this.capacity = capacity;
        this.min = 0;
    }

    private LinkedHashMap<Integer, Integer> getLHM() {
        return new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            };
        };
    }
    
    public int get(int key) {
        if (!keyToCount.containsKey(key)) {
            return -1;
        }
        int count = keyToCount.get(key);
        int val = countToLRU.get(count).remove(key);
        if (count == min && countToLRU.get(count).size() == 0) {
            min++;
        }
        keyToCount.put(key, count + 1);
        countToLRU.putIfAbsent(count + 1, getLHM());
        countToLRU.get(count+1).put(key, val);
        return val;
    }
    
    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        if (keyToCount.containsKey(key)) {
            get(key);
            int freq = keyToCount.get(key);
            countToLRU.get(freq).put(key, value);
            return;
        }

        if (keyToCount.size() >= capacity) {
            int k = countToLRU.get(min).entrySet().iterator().next().getKey();
            countToLRU.get(min).remove(k);
            keyToCount.remove(k);
        }

        min = 1;
        keyToCount.put(key, 1);
        countToLRU.putIfAbsent(1, getLHM());
        countToLRU.get(1).put(key, value);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */