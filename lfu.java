// Time Complexity :  O(n) 
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : Yes Its working
// Any problem you faced while coding this : No
class LFUCache {
    HashMap<Integer,Integer> keyToCount;
    int capacity;
    HashMap<Integer,LinkedHashMap<Integer,Integer>> countToLru;
    int min_count;
    public LFUCache(int capacity) {
        keyToCount = new HashMap<>();
        this.capacity = capacity;
        countToLru = new HashMap<>();
        min_count = 0;
    }
    private LinkedHashMap<Integer,Integer> lhm()
    {
        return new LinkedHashMap<Integer,Integer>(capacity,0.75f,true);
    }
    public int get(int key) {
        if(keyToCount.containsKey(key))
        {
            int count = keyToCount.get(key);
            keyToCount.put(key,count+1);
            int value = countToLru.get(count).remove(key);
        
            if(count == min_count && countToLru.get(count).size()==0)
            {
                min_count +=1;
            }
            countToLru.putIfAbsent(count+1,lhm());
            countToLru.get(count+1).put(key,value);
            return value;
        }
        else
        {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        if(capacity<=0) return;
        if(!keyToCount.containsKey(key))
        {
          
            if(capacity==keyToCount.size())
            {
                int temp_key = countToLru.get(min_count).entrySet().iterator().next().getKey();
                countToLru.get(min_count).remove(temp_key);
                keyToCount.remove(temp_key);
            }
           min_count=1;
           keyToCount.put(key,1);
           countToLru.putIfAbsent(1,lhm());
           countToLru.get(1).put(key,value); 
        }
        else
        {
            get(key);
            int count = keyToCount.get(key);
            countToLru.get(count).put(key,value);
            return;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */