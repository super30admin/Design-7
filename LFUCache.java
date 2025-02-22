/*
Implement least frequently used cache 
 using data structure. it should support 
 get(key) and put(key,value) functions. 
 the get(key) function get the value(which will always be positive)
  of the key if that exists in the cache else returns -1. 
  similarly our put function set or insert the value if 
  it is not already present. and when the cache reaches its capacity, 
  it should invalidate the least frequently used item before inserting a new one. 
  for this problem if there is a tie the least recently used key would be evicted. 
  try to do both operations in O(1) time complexity.
*/

class LFUCache {

  int min;

  int capacity;

  HashMap<Integer, Integer> keyToCount;

  HashMap<Integer, LinkedHashMap<Integer, Integer>> countToLRU;

  public LFUCache(int capacity) {
    this.capacity = capacity;

    this.min = 0;

    keyToCount = new HashMap<>();

    countToLRU = new HashMap<>();
  }

  private LinkedHashMap<Integer, Integer> getLHM() {
    return new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
      protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
      }
    };
  }

  public int get(int key) {
    if (!keyToCount.containsKey(key)) {
      return -1;
    }

    int count = keyToCount.get(key);

    int val = countToLRU.get(count).remove(key);

    if (count == min && countToLRU.get(count).size() == 0) {
      min++;
    }

    keyToCount.put(key, count + 1);

    countToLRU.putIfAbsent(count + 1, getLHM());

    countToLRU.get(count + 1).put(key, val);

    return val;
  }

  public void put(int key, int value) {
    if (capacity <= 0) {
      return;
    }

    if (keyToCount.containsKey(key)) {
      get(key);

      int freq = keyToCount.get(key);

      countToLRU.get(freq).put(key, value);

      return;
    }

    if (keyToCount.size() >= capacity) {
      int k = countToLRU.get(min).entrySet().iterator().next().getKey();

      countToLRU.get(min).remove(k);

      keyToCount.remove(k);
    }

    min = 1;

    keyToCount.put(key, 1);

    countToLRU.putIfAbsent(1, getLHM());

    countToLRU.get(1).put(key, value);
  }
}
