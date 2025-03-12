# T: get() -> O(1) (HashMap lookup + DLL update)
# put() -> O(1) (HashMap update + DLL insert/delete)
# S: O(C), where C is cache capacity.


class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.min_freq = 0
        self.key_map = {}  # { key: (value, freq) }
        self.freq_map = defaultdict(OrderedDict)  # { freq: { key: None } }

    def _update_freq(self, key):
        """Helper function to update key frequency"""
        value, freq = self.key_map[key]
        del self.freq_map[freq][key]  # Remove key from old frequency list
        if not self.freq_map[freq]:  # If empty, remove freq list
            del self.freq_map[freq]
            if self.min_freq == freq:
                self.min_freq += 1  # Increase min frequency
        # Move key to next frequency list
        new_freq = freq + 1
        self.freq_map[new_freq][key] = None
        self.key_map[key] = (value, new_freq)

    def get(self, key: int) -> int:
        if key not in self.key_map:
            return -1
        self._update_freq(key)  # Update key frequency
        return self.key_map[key][0]  # Return value

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.key_map:
            self.key_map[key] = (value, self.key_map[key][1])  # Update value
            self._update_freq(key)  # Update frequency
        else:
            if len(self.key_map) >= self.capacity:
                # Evict LFU key
                evict_key, _ = self.freq_map[self.min_freq].popitem(last=False)
                del self.key_map[evict_key]

            # Insert new key
            self.key_map[key] = (value, 1)
            self.freq_map[1][key] = None
            self.min_freq = 1  # Reset min_freq


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
