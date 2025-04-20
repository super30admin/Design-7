class LFUCache {

        HashMap<Integer,Pair<Integer, Integer>> valueMap;
        int minf;
       HashMap<Integer,LinkedHashSet<Integer>> frequencies; 
      int capacity; 
    public LFUCache(int capacity) {
       this.capacity = capacity;
       valueMap = new HashMap<>() ;
       minf = 1;
       frequencies = new HashMap<>();

    }
    
    public int get(int key) {
         if (!valueMap.containsKey(key)) {
            return -1;
        }
        Pair<Integer,Integer> p = valueMap.get(key);
        int value = p.getValue();
        int freq = p.getKey();
        //update the frequency - REMOVE OLD FREQ
        frequencies.get(freq).remove (key);
         if (frequencies.get(freq).isEmpty() && freq == minf) {
            minf++;
        }
        //update the frequency - ADD NEW FREQ
        frequencies.putIfAbsent(freq + 1, new LinkedHashSet<>());
        frequencies.get(freq+1).add (key);
         valueMap.put(key, new Pair<>(freq + 1, value));
        return value;
    }
    
    public void put(int key, int value) {
        if (capacity <= 0){
            return;
        }
        if (valueMap.get(key) == null){ 
            //key absent
            //add to value map
            if (capacity == valueMap.size ()){
                final Set<Integer> keys = frequencies.get(minf);
                 final int keyToDelete = keys.iterator().next();
            valueMap.remove(keyToDelete);
            keys.remove(keyToDelete);
            if (keys.isEmpty()) {
                frequencies.remove(minf);
            }
            }
            Pair<Integer, Integer> p = new Pair (1, value);
            valueMap.put(key,p);
            //maintain it's frequency
            frequencies.putIfAbsent(1, new LinkedHashSet<>());
            frequencies.get (1).add (key);
            minf = 1;
        } else {
            //key present
            int freq = valueMap.get(key).getKey();
            // create a new pair with updated freq
            Pair<Integer, Integer> p = new Pair (freq +1, value);
            valueMap.put (key,p);
            // now update the key in the frequencies map
            Set<Integer> keys = frequencies.get (freq);
            keys.remove (key);
            if (keys.isEmpty()){
                frequencies.remove (freq);
                if (minf == freq)
                ++minf;
            }
        frequencies.putIfAbsent(freq+1, new LinkedHashSet<>());
        frequencies.get(freq + 1).add(key);

        }        
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
