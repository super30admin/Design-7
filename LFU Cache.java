// Time Complexity : O(n)
// Space Complexity : O(n)

class LFU {

        int minCount;
        int capacity;
        HashMap<Integer, Integer> keyToCount;
        HashMap<Integer, LinkedHashMap<Integer, Integer>> countToLRU;

        public LFU(int capacity) {
            this.capacity = capacity;
            this.minCount = 0;
            keyToCount = new HashMap<>();
            countToLRU = new HashMap<>();
        }

    /*LinkedHashMap(int capacity, float fillRatio, boolean Order): boolean   
      Order if to follow the insertion order or not.
      True is passed for last access order.
      False is passed for insertion order.*/

        private LinkedHashMap<Integer, Integer> getLinkedHashMap(){
            return new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true);
        }

        public int get(int key) {            
            if(!keyToCount.containsKey(key)){
                return -1;
            }            
            int count = keyToCount.get(key);           
            int value = countToLRU.get(count).remove(key);            
            if(count == minCount && countToLRU.get(count).size() == 0){
                minCount += 1;
            }           
            keyToCount.put(key, count+1);
            countToLRU.putIfAbsent(count+1, getLinkedHashMap());
            countToLRU.get(count+1).put(key, value);
            return value;
        }

        public void put(int key, int value) {            
            if (capacity <= 0) {
                return;
            }           
            if (keyToCount.containsKey(key)) {               
                get(key);               
                int count = keyToCount.get(key);
                countToLRU.get(count).put(key, value);
                return;
            }
            
            if (keyToCount.size() >= capacity) {                
                int tempKey = countToLRU.get(minCount).entrySet().iterator().next().getKey();
                countToLRU.get(minCount).remove(tempKey);
                keyToCount.remove(tempKey);
            }         

            minCount = 1;
            keyToCount.put(key, 1);
            countToLRU.putIfAbsent(minCount, getLinkedHashMap());
            countToLRU.get(minCount).put(key, value);
        }
}