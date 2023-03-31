import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
//Time Complexity : O(1)
//Space Complexity : O(capacity)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

/**
 * Maintain two maps. One for the key to (freq, value) and one for frequencies
 * (freq -> set of keys). So when get is called check if there is a key in the
 * cache. If so, get its frequency and fetch the set of keys having that
 * frequency. Remove this key from that list and if that list becomes empty
 * remove that frequency from the frequencies map. Then update the key in the
 * cache with frequency+1 and value and also put the frequency with the key in
 * its set.
 * 
 * When put is called, check if there exists a key already in cache. If so,
 * update the key with the new value and old frequency and then call get of this
 * key. It will update the frequency accordingly. If the capacity is already
 * full, then get the min frequency from the frequencies map and remove it from
 * cache as well. Then insert the new key with frequency as 1. Update min to 1.
 *
 */
class LFUCache {
	Map<Integer, Pair<Integer, Integer>> cache;
	Map<Integer, LinkedHashSet<Integer>> frequencies;
	int min;
	int capacity;

	public LFUCache(int capacity) {
		this.capacity = capacity;
		this.cache = new HashMap<>();
		frequencies = new HashMap<>();
		min = 0;
	}

	public int get(int key) {
		Pair<Integer, Integer> freq = cache.get(key);
		if (freq == null) {
			return -1;
		}
		int frequency = freq.getKey();
		Set<Integer> keys = frequencies.get(frequency);
		keys.remove(key);
		if (keys.isEmpty()) {
			frequencies.remove(frequency);
			if (min == frequency)
				min++;
		}
		int value = freq.getValue();
		insert(key, frequency + 1, value);
		return value;
	}

	public void insert(int key, int frequency, int value) {
		cache.put(key, new Pair<>(frequency, value));
		frequencies.putIfAbsent(frequency, new LinkedHashSet<>());
		frequencies.get(frequency).add(key);
	}

	public void put(int key, int value) {
		if (capacity <= 0) {
			return;
		}
		Pair<Integer, Integer> freq = cache.get(key);
		if (freq != null) {
			cache.put(key, new Pair<>(freq.getKey(), value));
			get(key);
			return;
		}
		if (capacity == cache.size()) {
			Set<Integer> keys = frequencies.get(min);
			int keyToDel = keys.iterator().next();
			cache.remove(keyToDel);
			keys.remove(keyToDel);
			if (keys.isEmpty()) {
				frequencies.remove(min);
			}
		}
		min = 1;
		insert(key, 1, value);
	}
}

/**
 * Your LFUCache object will be instantiated and called as such: LFUCache obj =
 * new LFUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */
