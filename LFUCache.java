class LFUCache {

    private HashMap<Integer, Integer> freqMap;
    private HashMap<Integer, LinkedHashMap<Integer, Integer>> freqToLRU;
    private int minFreq;
    private int capacity;

    public LFUCache(int capacity) {
        freqMap = new HashMap<>();
        freqToLRU = new HashMap<>() {
            {
                put(1, new LinkedHashMap<Integer, Integer>());
            }
        };
        minFreq = 1;
        this.capacity = capacity;
    }

    public int get(int key) {
        // System.out.println("get: " + key);
        // System.out.println("freqMap:" + freqMap.toString());
        // System.out.println("freqToLRU:" + freqToLRU.toString());
        Integer freq = freqMap.get(key);
        if (freq == null) {
            return -1;
        }

        LinkedHashMap<Integer, Integer> lru = freqToLRU.get(freq);
        int value = lru.get(key);
        lru.remove(key);
        if (freq == minFreq && lru.size() == 0) {
            minFreq++;
        }

        LinkedHashMap<Integer, Integer> newlru = freqToLRU.get(freq + 1);
        if (newlru == null) {
            newlru = new LinkedHashMap<>();
            freqToLRU.put(freq + 1, newlru);
        }
        newlru.put(key, value);
        freqMap.put(key, freq + 1);

        return value;
    }

    public void put(int key, int value) {
        // System.out.println("put: " + key + ":" + value);
        // System.out.println("freqMap:" + freqMap.toString());
        // System.out.println("freqToLRU:" + freqToLRU.toString());
        // System.out.println(freqMap.toString());

        if (capacity == 0)
            return;

        Integer freq = freqMap.get(key);
        if (freq == null) {
            if (freqMap.size() == capacity) {
                LinkedHashMap<Integer, Integer> lru = freqToLRU.get(minFreq);
                Map.Entry<Integer, Integer> entry = lru.entrySet().iterator().next();
                lru.remove(entry.getKey());
                freqMap.remove(entry.getKey());
            }

            minFreq = 1;
            LinkedHashMap<Integer, Integer> lru = freqToLRU.get(minFreq);
            lru.put(key, value);
            freqMap.put(key, 1);
        } else {
            LinkedHashMap<Integer, Integer> lru = freqToLRU.get(freq);
            lru.remove(key);
            if (freq == minFreq && lru.size() == 0) {
                minFreq++;
            }

            LinkedHashMap<Integer, Integer> newlru = freqToLRU.get(freq + 1);
            if (newlru == null) {
                newlru = new LinkedHashMap<>();
                freqToLRU.put(freq + 1, newlru);
            }

            newlru.put(key, value);
            freqMap.put(key, freq + 1);
        }
    }
}