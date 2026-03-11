# Time Complexity : O(1) for get and put (amortized) using dictionary + frequency map
# Space Complexity : O(capacity), to store keys and their frequency info
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Explanation:
# 1. Use a dictionary to store key -> (value, frequency).
# 2. Use another dictionary to map frequency -> keys in order of usage (OrderedDict).
# 3. On get(), update frequency and move key to next frequency list.
# 4. On put(), if at capacity, remove least frequently used key (and oldest among ties).
# 5. Insert new key with frequency 1.

from collections import defaultdict, OrderedDict

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.min_freq = 0
        self.key_to_val_freq = {}  # key -> (value, freq)
        self.freq_to_keys = defaultdict(OrderedDict)  # freq -> keys in order

    def get(self, key: int) -> int:
        if key not in self.key_to_val_freq:
            return -1
        value, freq = self.key_to_val_freq[key]
        # remove from current freq
        del self.freq_to_keys[freq][key]
        if not self.freq_to_keys[freq]:
            del self.freq_to_keys[freq]
            if self.min_freq == freq:
                self.min_freq += 1
        # add to next freq
        self.freq_to_keys[freq + 1][key] = None
        self.key_to_val_freq[key] = (value, freq + 1)
        return value

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        if key in self.key_to_val_freq:
            # update value and frequency
            self.key_to_val_freq[key] = (value, self.key_to_val_freq[key][1])
            self.get(key)  # update frequency
            return
        if len(self.key_to_val_freq) >= self.capacity:
            # evict least freq and oldest
            evict_key, _ = self.freq_to_keys[self.min_freq].popitem(last=False)
            del self.key_to_val_freq[evict_key]
        # insert new key
        self.key_to_val_freq[key] = (value, 1)
        self.freq_to_keys[1][key] = None
        self.min_freq = 1