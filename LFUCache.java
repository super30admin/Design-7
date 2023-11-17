import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.*;
import java.util.Set;

class LFUCache {

    Map<Integer, Pair> cache;
    Map<Integer, LinkedHashSet<Integer>> frequencies;
    int minF, capacity;

    public LFUCache(int capacity) {
        cache = new HashMap<>();
        frequencies = new HashMap<>();
        this.capacity = capacity;
    }

    public void insert(int frequency, int key, int value){
        cache.put(key, new Pair(frequency, value));
        if(!frequencies.containsKey(frequency)){
            frequencies.put(frequency, new LinkedHashSet<>());
        }
        frequencies.get(frequency).add(key);
    }

    public int get(int key) {
        if(!cache.containsKey(key)) return -1;

        Pair frequencyValue = cache.get(key);
        int frequency = frequencyValue.getKey();
        Set<Integer> set = frequencies.get(frequency);
        set.remove(key);
        if(set.size() == 0){
            frequencies.remove(frequency);
            if(minF == frequency) minF++;
        }
        int value = frequencyValue.getValue();
        insert(frequency+1, key, value);
        return value;


    }

    public void put(int key, int value) {
        if(cache.containsKey(key)){
            Pair frequencyValue = cache.get(key);
            cache.put(key, new Pair(frequencyValue.getKey(), value));
            get(key);
            return;
        }
        if(cache.size() == capacity){
            Set<Integer> toRemove = frequencies.get(minF);
            int keytoRemove = toRemove.iterator().next();
            toRemove.remove(keytoRemove);
            if(toRemove.size() == 0){
                frequencies.remove(minF);
            }
            cache.remove(keytoRemove);
        }
        insert(1, key, value);
        minF=1;

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */