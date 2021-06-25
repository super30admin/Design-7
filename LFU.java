
// Time Complexity : O(1) for get and put
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
// Using 3 HashMap 1 for key-value, another for key-Frequency and 
// another for Freq-ListofKey
class LFUCache {
    
    private Map<Integer, Integer> valuesMap;
    private Map<Integer, Integer> freqMap;
    private Map<Integer, LinkedHashSet<Integer>> listMap;
    private int min_freq;
    private int capacity;

    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        valuesMap = new HashMap<>();
        freqMap = new HashMap<>();
        listMap = new HashMap<>();
        min_freq = 0;
    }
    
    public int get(int key) {
        if(!valuesMap.containsKey(key)){
            return -1;
        }   
        int value = valuesMap.get(key);
        updateFrequency(key);
        return value;
    }
    
    public void put(int key, int value) { 
        if(capacity <=0)
            return;
        
        if(valuesMap.containsKey(key)){
            valuesMap.put(key, value);
            updateFrequency(key);
        }else{
            if(valuesMap.size() >= capacity){
                removeLessFrequency();
            }
            valuesMap.put(key, value);
            freqMap.put(key, 1);
            addToList(1, key);
            min_freq = 1;
        }
     
    }
    
    
    /* 
    * Get the frequency of the key and increment in 1
    * Remove the key from the list of previous frequency
    * increment min_frequency only if there is not other key in the list for that count
    * add the key to the list according the new fequency
    */
    private void updateFrequency (int key) {
        int freq = freqMap.get(key);
        freqMap.put(key, freq+1);
        listMap.get(freq).remove(key);
        
        if(min_freq == freq && listMap.get(freq).size()==0)
            min_freq++;
        
        addToList(freq+1, key);
    }
    
    /* 
    * Add the key to the list using a key the frequency
    */
    private void addToList(int frequency, int key) {
        if(!listMap.containsKey(frequency)){
            listMap.put(frequency, new LinkedHashSet());
        }
        listMap.get(frequency).add(key);
    }
    
    /*
    * Remove the elemenet with min frequency
    * if there are multiple with the same min frequency, remove the first element.
    * Remove the key from the frequency (count) and from the valsMap
    */
    private void removeLessFrequency() {
        int keyToRemove = listMap.get(min_freq).iterator().next(); 
        listMap.get(min_freq).remove(keyToRemove);
        valuesMap.remove(keyToRemove);
        freqMap.remove(keyToRemove);
    }  
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */