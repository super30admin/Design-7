import java.util.*;

// TC: O(1)
// SC: O(min(n, capacity))
// where n is the number of keys in the cache and capacity is the maximum number of keys that can be stored in the cache

public class LFUCache {

    int min;
    int cap;
    HashMap<Integer, Integer[]> valuesAndCount;
    HashMap<Integer, LinkedHashSet<Integer>> sets;

    public LFUCache(int capacity) {
        valuesAndCount = new HashMap<>();
        sets = new HashMap<>();
        min = -1;
        cap = capacity;
    }
    
    public int get(int key) {
        if(!valuesAndCount.containsKey(key)){
            return -1;
        }
        int v = valuesAndCount.get(key)[0];
        int c = valuesAndCount.get(key)[1];
        valuesAndCount.put(key, new Integer[]{v, c+1});
        sets.get(c).remove(key);
        sets.remove(c, 0);
        if(min==c && sets.get(min).size()==0){
            min++;
        }
        if(!sets.containsKey(c+1)){
            sets.put(c+1, new LinkedHashSet<>());
        }
        sets.get(c+1).add(key);
        return valuesAndCount.get(key)[0];
    }
    
    public void put(int key, int value) {
        if(cap<=0) return;
        if(valuesAndCount.containsKey(key)){
            int c = valuesAndCount.get(key)[1];
            valuesAndCount.put(key, new Integer[]{value, c});
            get(key);
            return;
        }
        if(valuesAndCount.size()==cap){
            int el = sets.get(min).iterator().next();
            sets.get(min).remove(el);
            sets.remove(min, 0);
            valuesAndCount.remove(el);
        }
        valuesAndCount.put(key, new Integer[]{value, 1});
        min = 1;
        if(sets.get(1)==null) sets.put(1, new LinkedHashSet<>());
        sets.get(1).add(key);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
