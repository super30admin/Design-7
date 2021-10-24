//TC and SC is not needed for design problems
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

public class designLFUCache {
    class ValAndFreq{
        int val;
        int freq;
        public ValAndFreq(int val, int freq){
            this.val = val;
            this.freq = freq;
        }
    }
    
    private Map<Integer, LinkedHashSet<Integer>> freqMap;
    private Map<Integer, ValAndFreq> valMap; 
    int minFreq = 1;
    int capacity = 0;
    int size = 0;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        freqMap = new HashMap<>();
        this.valMap = new HashMap<>();
    }
    
    public int get(int key) {
       
        ValAndFreq valFreq = valMap.get(key);    
        if(valFreq==null) return -1;
        LinkedHashSet<Integer> set = freqMap.get(valFreq.freq);
     
        set.remove(key);
        LinkedHashSet<Integer> new_set = freqMap.getOrDefault(valFreq.freq+1, new LinkedHashSet<Integer>());
        new_set.add(key);
        freqMap.put(valFreq.freq+1, new_set);
        
        //update freq in valMap as well
        ValAndFreq newValFreq = new ValAndFreq(valFreq.val, valFreq.freq+1);
        valMap.put(key, newValFreq);
        
        //update min freq
        if(minFreq == valFreq.freq && set.size()==0)minFreq+=1;
        
        return valFreq.val;        
    }
    
    public void put(int key, int value) {        
        if(capacity == 0) return;

        if(valMap.containsKey(key)){
            ValAndFreq valFreq = valMap.get(key);
            
            LinkedHashSet<Integer> set = freqMap.get(valFreq.freq);
            set.remove(key);

            LinkedHashSet<Integer> new_set = freqMap.getOrDefault(valFreq.freq+1, new LinkedHashSet<Integer>());
            new_set.add(key);
            freqMap.put(valFreq.freq+1, new_set);
      
            valMap.put(key, new ValAndFreq(value, valFreq.freq+1));
            if(minFreq == valFreq.freq && set.size()==0)minFreq+=1;
            return;
        }
        
        if(capacity == size){
      
            LinkedHashSet<Integer> set = freqMap.get(minFreq);
            Iterator<Integer> iter = set.iterator();
            int element_rem = iter.next();
            valMap.remove(element_rem);
            set.remove(element_rem);
            size--;
        }

        ValAndFreq valFreq = new ValAndFreq(value, 1);
        valMap.put(key, valFreq);
        LinkedHashSet<Integer> set = freqMap.getOrDefault(1, new LinkedHashSet<Integer>());
        set.add(key);
        freqMap.put(1, set);
        minFreq=1;
        size++;
    }
}
