# Time Complexity = O(1) for both get and put
# Space Complexity = O(1) 
class LFUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.key_to_value = {}  # Stores key-value pairs
        self.key_to_freq = {}   # Stores frequency of each key
        self.freq_to_keys = defaultdict(OrderedDict)  # Groups keys by frequency
        self.min_freq = 1       # Tracks the minimum frequency

    def get(self, key: int) -> int:
        if key not in self.key_to_value:
            return -1

        # Increment the frequency of the key
        freq = self.key_to_freq[key]
        self.key_to_freq[key] += 1

        # Move the key to the new frequency group
        del self.freq_to_keys[freq][key]
        self.freq_to_keys[freq + 1][key] = None

        # Update min_freq if the old frequency group is now empty
        if not self.freq_to_keys[freq] and freq == self.min_freq:
            self.min_freq += 1

        return self.key_to_value[key]

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.key_to_value:
            # Update the value and increment the frequency
            self.key_to_value[key] = value
            self.get(key)  # Reuse get() to update frequency
            return

        if len(self.key_to_value) >= self.capacity:
            # Remove the least frequently used key
            lfu_key, _ = self.freq_to_keys[self.min_freq].popitem(last=False)
            del self.key_to_value[lfu_key]
            del self.key_to_freq[lfu_key]

        # Add new key
        self.key_to_value[key] = value
        self.key_to_freq[key] = 1
        self.freq_to_keys[1][key] = None
        self.min_freq = 1  # Reset min_freq since we added a new key
