// TC = O(n)
// SC = O(1)
class LFUCache {
    HashMap<Integer, Integer> frequency;  
    HashMap<Integer, Integer> cache; 
    HashMap<Integer, LinkedHashSet<Integer>> freqMap; 
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.frequency = new HashMap<>();
        this.cache = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.freqMap.put(1, new LinkedHashSet<Integer>());
        this.min = 0;
    }
    
    public int get(int key) {
        if (capacity <= 0 || cache.get(key) == null) {
            return -1;
        }
        int oldFreq = frequency.getOrDefault(key, 0);
        frequency.put(key, oldFreq + 1);
        freqMap.get(oldFreq).remove(key);
        if (oldFreq == min && freqMap.get(oldFreq).size() == 0) {
            min++;
        }
        if (!freqMap.containsKey(oldFreq + 1)) {
            freqMap.put(oldFreq + 1, new LinkedHashSet<Integer>());
        }
        freqMap.get(oldFreq + 1).add(key);
        
        return cache.get(key);
    }
    
    public void put(int key, int value) {
        if (capacity <= 0) return;
        if (cache.containsKey(key)) {
            cache.put(key, value);
            get(key);
            return;
        }
        
        if (cache.size() >= capacity) {
            int oldKey = freqMap.get(min).iterator().next();
            freqMap.get(min).remove(oldKey);
            frequency.remove(oldKey);
            cache.remove(oldKey);
        }
        cache.put(key, value);
        frequency.put(key, 1);
        min = 1;
        freqMap.get(min).add(key);
    }
}
