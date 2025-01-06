# The LFUCache class implements a Least Frequently Used (LFU) cache.

# Initialization:
# - Use dictionaries to track key-value pairs, key frequencies, and keys grouped by frequencies.
# - Maintain the minimum frequency (`minf`) to track the least frequently used keys.

# get:
# - Retrieve a value if the key exists, update its frequency, and move it to the next frequency group.
# - Update the minimum frequency if the old group becomes empty.
# - Return -1 if the key doesn't exist.

# put:
# - Update the value and frequency if the key already exists.
# - If the cache is full, remove the least frequently used key before adding a new key.
# - Add the new key with frequency 1 and reset `minf` to 1.

# TC: O(1) - Efficient operations using hash maps and ordered dictionaries.
# SC: O(n) - Space for storing up to `capacity` keys and their metadata.


import collections


class LFUCache:

    def __init__(self, capacity: int):
        self.cap = capacity
        self.key2val = {}
        self.key2freq = {}
        self.freq2key = collections.defaultdict(collections.OrderedDict)
        self.minf = 0

    def get(self, key: int) -> int:
        if key not in self.key2val:
            return -1
        oldfreq = self.key2freq[key]
        self.key2freq[key] = oldfreq + 1
        self.freq2key[oldfreq].pop(key)
        if not self.freq2key[oldfreq]:
            del self.freq2key[oldfreq]
        self.freq2key[oldfreq + 1][key] = 1
        if self.minf not in self.freq2key:
            self.minf += 1
        return self.key2val[key]

    def put(self, key: int, value: int) -> None:
        if self.cap <= 0:
            return
        if key in self.key2val:
            self.get(key)
            self.key2val[key] = value
            return

        if len(self.key2val) == self.cap:
            delkey, _ = self.freq2key[self.minf].popitem(last=False)
            del self.key2val[delkey]
            del self.key2freq[delkey]
        self.key2val[key] = value
        self.key2freq[key] = 1
        self.freq2key[1][key] = 1
        self.minf = 1