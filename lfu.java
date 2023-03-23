class LFUCache {
    int min;
    int capacity;
    HashMap<Integer, Integer> keyToCount;
    HashMap <Integer, LinkedHashMap<Integer, Integer>> countToLRU;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.min = 0;
        keyToCount = new HashMap<>();
        countToLRU = new HashMap<>();

    }
    
    public int get(int key) {
        if(!keyToCount.containsKey(key)) return -1;
        int count = keyToCount.get(key);
        int val = countToLRU.get(count).remove(key);
        if(count == min && countToLRU.get(count).size()==0) min++;
        keyToCount.put(key,count+1);
        countToLRU.putIfAbsent(count+1,getLHM());
        countToLRU.get(count+1).put(key,val);
        return val;
    }
    
    public void put(int key, int value) {
        if(capacity<=0) return;
        if(keyToCount.containsKey(key)){
            get(key);
            int freq = keyToCount.get(key);
            countToLRU.get(freq).put(key,value);
            return;
        }
        if(keyToCount.size()>= capacity){
            int k = countToLRU.get(min).entrySet().iterator().next().getKey();
            countToLRU.get(min).remove(k);
            keyToCount.remove(k);
        }
        min =1;
        keyToCount.put(key,1);
        countToLRU.putIfAbsent(1,getLHM());
        countToLRU.get(1).put(key,value);
    }

    public LinkedHashMap<Integer, Integer>getLHM(){
        return new LinkedHashMap<Integer,Integer>(capacity,0.75f,true){
                    protected boolean removeEldestEntry(Map.Entry eldest) {
                        return size()>capacity;
                    }
                
    };}
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */