    /*  Explanation
    # Leetcode problem link : https://leetcode.com/problems/lfu-cache/
    Time Complexity for operators : o(1) 
    Extra Space Complexity for operators : o(1)
    Did this code successfully run on Leetcode : NA
    Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
        # Basic approach : 
        # Optimized approach: 
                              
            # 1. 
                    A) Initialize the variables keyToCount, countToLRU, capacity and min_count.
                    B) Create function getLHM(), it will create new LinkedHashMap.
                    C) get() - Check if key present in keyToCount if no then return -1.
                             - Then if present then get the count from keyToCount and then find that in countToLRU's Linkedhashmap
                               to get the value. Remove this from LinkedHashMap as counter is now changing for this.
                             - If this count == min_count and countToLRU is emoty then increse the min_count.
                             - Update the keyToCount with new counter value.
                             - Update countToLRU with new counter value.
                    D) put() - If capacity <= 0 then return.
                             - If element is present then do the get(), (C) step on it so that it will get incresed its coounts 
                               and then added into the respective haqshmap.
                             - If capacity is reached then, get the key of first ekement in the linkedhashmap of min_count.
                               remove it from LinkedHashmap and alsoo from KeyToCount.
                             - If it passes all these checks, then put the element regulary by updating both the hashmap with count.
    */

class LFUCache {

    HashMap<Integer, Integer> keyToCount = new HashMap<>(); // (key, count)
    HashMap<Integer, LinkedHashMap<Integer, Integer>> countToLRU = new HashMap<>();//(count, (key, value))
    
    int min_count = 0;
    int capacity = 0;
    
    private LinkedHashMap<Integer, Integer> getLHM(){
        return new LinkedHashMap<Integer, Integer>(capacity);
    }
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!keyToCount.containsKey(key))
            return -1;
        
        int count = keyToCount.get(key);
        int value = countToLRU.get(count).remove(key); // it returns the value
        
        if(count == min_count && countToLRU.get(count).isEmpty()){
            min_count += 1;
        }
        
        keyToCount.put(key, count+1);
        countToLRU.putIfAbsent(count + 1, getLHM());
        
        countToLRU.get(count+1).put(key, value);
        
        return value;
    }
    
    public void put(int key, int value) {
        if(this.capacity<=0)
            return;
        
        // put if element is present
        if(keyToCount.containsKey(key)){
            get(key);
            
            int count = keyToCount.get(key);
            countToLRU.get(count).put(key, value);
            
            return;
        }
        
        // capacity is reached 
        if(this.capacity <= keyToCount.size()){
            int temp_key = countToLRU.get(min_count).entrySet().iterator().next().getKey();
            countToLRU.get(min_count).remove(temp_key);
            keyToCount.remove(temp_key);
        }
        
        // put regulary if element is not present
        min_count = 1;
        keyToCount.put(key,1);
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