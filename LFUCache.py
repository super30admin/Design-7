class LFUCache:
     def __init__(self, capacity: int):
        self.capacity = capacity
        self.cache = {}
        self.freq_to_keys = {}
        self.min_freq = 0

def update_freq(self, key):
        freq = self.cache[key][1]
        self.freq_to_keys[freq].remove(key)
        if not self.freq_to_keys[freq]:
            del self.freq_to_keys[freq]
            if self.min_freq == freq:
                self.min_freq += 1
        freq += 1
        self.cache[key][1] = freq
        self.freq_to_keys.setdefault(freq, []).append(key)

def get(self, key: int) -> int:
        #Tc: O(1) Sc: O(1)
        if key not in self.cache:
            return -1
        self.update_freq(key)
        return self.cache[key][0]

def put(self, key: int, value: int) -> None:
        #Tc:O(1) Sc: O(1)
        if self.capacity == 0:
            return
        if key in self.cache:
            self.cache[key][0] = value
            self.update_freq(key)
        else:
            if len(self.cache) >= self.capacity:
                evict_key = self.freq_to_keys[self.min_freq].pop(0)
                if not self.freq_to_keys[self.min_freq]:
                    del self.freq_to_keys[self.min_freq]
                del self.cache[evict_key]
            self.cache[key] = [value, 1]
            self.freq_to_keys.setdefault(1, []).append(key)
            self.min_freq = 1
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)