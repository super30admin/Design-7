class LFUCache {

    int min;
    int capacity;
    HashMap<Integer,Integer> keyToFreq;
    HashMap<Integer,LinkedHashMap<Integer,Integer>> freqToRece;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.min = 0;
        keyToFreq = new HashMap<>();
        freqToRece = new HashMap<>();
    }
    
    private LinkedHashMap<Integer,Integer> helper(){
        return new LinkedHashMap<Integer,Integer>(capacity,0.75f,true)
        {
            protected boolean removeEldestEntry(Map.Entry eldest){
                return size() > capacity ;
            }
        };
    }
    public int get(int key) {
        //base case 
        if(!keyToFreq.containsKey(key))
            return -1;
        
        int count = keyToFreq.get(key);
        int val = freqToRece.get(count).remove(key);
        
        if(count == min && freqToRece.get(count).size() == 0){
            min++;
        }
        keyToFreq.put(key,count+1);
        freqToRece.putIfAbsent(count+1,helper());
        freqToRece.get(count+1).put(key,val);
        return val;
    }
    
    
    public void put(int key, int value) {
        if(capacity <=0)
            return;
        if(keyToFreq.containsKey(key)){
            get(key);
            int freq  = keyToFreq.get(key);
            freqToRece.get(freq).put(key,value);
            return ;
        }
        
        //Evict
        if(keyToFreq.size() >= capacity){
            int k = freqToRece.get(min).entrySet().iterator().next().getKey();
            freqToRece.get(min).remove(k);
            keyToFreq.remove(k);
        }
        min = 1;
        keyToFreq.put(key,1);
        freqToRece.putIfAbsent(1,helper());
        freqToRece.get(1).put(key,value);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */