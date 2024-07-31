# I designed a cache system that efficiently handles frequent updates and queries while maintaining a fixed capacity. The LFUCache class utilizes two main data structures: cnt, a dictionary of lists that tracks keys by their frequency counts, and cache, a dictionary that maps keys to their corresponding values and frequency counts. The get method updates the frequency count of the accessed key, while the put method manages key-value pairs and handles eviction when the cache exceeds its capacity. If a new key is added and the capacity is exceeded, the least frequently used key (with the smallest frequency) is evicted. The time complexity for both get and put operations is O(1) on average, thanks to efficient dictionary operations and list management. The space complexity is O(N), where N is the number of keys in the cache, as it stores each key and its associated frequency count.

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity

        # self.cnt contains a list of the keys. the eailier ones in the front.
        self.cnt = collections.defaultdict(list)

        # self.cache contains the values and counts of the keys.
        self.cache = collections.defaultdict(tuple)

    def get(self, key: int) -> int:
        if key not in self.cache:
            return -1
        c = self.cache[key][1]
        self.cnt[c].remove(key)
        if len(self.cnt[c]) == 0:
            del self.cnt[c]
        self.cnt[c+1].append(key)
        self.cache[key] = (self.cache[key][0], c+1)

        # print(self.cnt, self.cache, c)
        return self.cache[key][0]

    def put(self, key: int, value: int) -> None:
        if key not in self.cache:
            if len(self.cache) >= self.capacity:
                mini = min(self.cnt)
                k = self.cnt[mini].pop(0)
                if len(self.cnt[mini]) == 0:
                    del self.cnt[mini]
                del self.cache[k]
            self.cnt[1].append(key)
            self.cache[key] = (value, 1)
        else:
            c = self.cache[key][1]
            self.cnt[c].remove(key)
            if len(self.cnt[c]) == 0:
                del self.cnt[c]
            self.cnt[c+1].append(key)
            self.cache[key] = (value, self.cache[key][1] + 1)
            

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)