// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

import java.util.*;

public class LFU {

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

    /*LinkedHashMap(int capacity, float fillRatio, boolean Order): boolean         Order if to follow the insertion order or not.
      True is passed for last access order.
      False is passed for insertion order.*/

        private LinkedHashMap<Integer, Integer> getLinkedHashMap(){
            return new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true);
        }

        public int get(int key) {
            // 1.check if the key is present or not
            if(!keyToCount.containsKey(key)){
                return -1;
            }
            // 2. get the count
            int count = keyToCount.get(key);
            // 3. get value from LRU HashMap and remove it
            // why remove? as we access it, we need to change few things
            int value = countToLRU.get(count).remove(key); // returns the value removed

            // 4. check if count is empty, if so, update min_count
            if(count == minCount && countToLRU.get(count).size() == 0){
                minCount += 1;
            }
            // 5. insert everything accordingly to hashmap
            keyToCount.put(key, count+1);
            countToLRU.putIfAbsent(count+1, getLinkedHashMap());
            countToLRU.get(count+1).put(key, value);

            return value;
        }

        public void put(int key, int value) {
            // check if capacity is valid
            if (capacity <= 0) {
                return;
            }
            // check if hashmap contains key,
            if (keyToCount.containsKey(key)) {
                // process as you would do for get
                get(key);
                // update the value
                int count = keyToCount.get(key);
                countToLRU.get(count).put(key, value);

                return;
            }
            // check if capacity is filled
            if (keyToCount.size() >= capacity) {
                // get the key of Least recent, least freq
                int tempKey = countToLRU.get(minCount).entrySet().iterator().next().getKey();
                countToLRU.get(minCount).remove(tempKey);
                keyToCount.remove(tempKey);
            }
            // reset everything and insert accordingly
            // if key was present, we don't need to remove anything, hence, till here, would have been processed. Now we know that key is new and hence minCount is obvio 1

            minCount = 1;
            keyToCount.put(key, 1);
            countToLRU.putIfAbsent(minCount, getLinkedHashMap());
            countToLRU.get(minCount).put(key, value);
        }
}
