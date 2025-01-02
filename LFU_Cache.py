# Explain your approach in briefly only at top of your code
# Approach:
# The LFUCache uses a combination of dictionaries and linked lists to achieve O(1) time complexity for both get and put operations.
# - A key-value store to maintain the cache.
# - A frequency map to track the frequency of each key.
# - A least-frequent map that groups keys by frequency, ensuring efficient retrieval of the least frequently used keys.
# - A min_frequency variable to track the current minimum frequency for efficient eviction.
# If the cache is full, the least frequently used key is removed. Ties are broken using the least recently used (LRU) approach.

# Time Complexity: O(1) for both get and put operations on average.
# Space Complexity: O(n), where n is the capacity of the cache.
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this: Managing the LRU aspect within the LFU implementation.


class LFUCache:

    def __init__(self, capacity: int):
        """
        Initialize the LFU Cache with the given capacity.
        """
        self.capacity = capacity  # Maximum capacity of the cache
        self.min_frequency = 0  # Track the minimum frequency in the cache
        self.cache = {}  # Key-value map to store (key, value)
        self.frequency = {}  # Key-frequency map to store (key, freq)
        self.freq_map = defaultdict(OrderedDict)  # Frequency map storing keys by their frequency

    def get(self, key: int) -> int:
        """
        Retrieve the value associated with the key. Increment the frequency of the key.
        """
        if key not in self.cache:
            return -1  # Key not found in cache
        
        # Increment frequency of the key
        freq = self.frequency[key]
        self.frequency[key] += 1
        
        # Move key to the next frequency list
        del self.freq_map[freq][key]
        if not self.freq_map[freq]:
            del self.freq_map[freq]
            if freq == self.min_frequency:
                self.min_frequency += 1

        self.freq_map[freq + 1][key] = None
        return self.cache[key]

    def put(self, key: int, value: int) -> None:
        """
        Add or update the value for the given key. If the cache is full, remove the least frequently used key.
        """
        if self.capacity == 0:
            return
        
        if key in self.cache:
            # Key already in cache, update the value and increment frequency
            self.cache[key] = value
            self.get(key)  # Increment frequency using get method
            return
        
        # If the cache is full, evict the least frequently used key
        if len(self.cache) >= self.capacity:
            # Find and remove the least frequently used key
            evict_key, _ = self.freq_map[self.min_frequency].popitem(last=False)
            if not self.freq_map[self.min_frequency]:
                del self.freq_map[self.min_frequency]
            del self.cache[evict_key]
            del self.frequency[evict_key]

        # Add the new key to the cache
        self.cache[key] = value
        self.frequency[key] = 1
        self.freq_map[1][key] = None
        self.min_frequency = 1

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key, value)
