/**
 * Time Complexity - O(1)
 * Space Complexity - O(n)
 */

class LFUCache {

    Map<Integer, Integer> counts;
    Map<Integer, Integer> values;
    Map<Integer, LinkedHashSet<Integer>> lists;
    int min;
    int capacity;
    public LFUCache(int capacity) {
        min = -1;
        counts = new HashMap<>();
        values = new HashMap<>();
        lists = new HashMap<>();
        lists.put(1,new LinkedHashSet<>());
        this.capacity = capacity;
    }


    public int get(int key) {

        // If key is absent, then return -1
        if(!values.containsKey(key))
            return -1;

        // Get Count of Key
        int count = counts.get(key);
        counts.put(key,count+1);

        //remove key from count level
        lists.get(count).remove(key);

        // check aftering removing key, is list empty ? if yes, min++;
        if(count == min && lists.get(min).size() == 0){
            min++;
        }

        if(!lists.containsKey(count+1)){
            lists.put(count+1, new LinkedHashSet<>());
        }

        // put key in count+1 level
        lists.get(count+1).add(key);
        return values.get(key);
    }

    public void put(int key, int value) {


        if(capacity <=0){
            return;
        }

        // If key exists, change value and call get()
        if(values.containsKey(key)){
            values.put(key,value);
            get(key);
            return;
        }

        // If LFU is over capacitu, remove least count key
        if(values.size() >= capacity){
            int evict = lists.get(min).iterator().next();
            lists.get(min).remove(evict);
            counts.remove(evict);
            values.remove(evict);
        }

        // Add new Key, value
        counts.put(key,1);
        values.put(key,value);
        min = 1;
        lists.get(1).add(key);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */