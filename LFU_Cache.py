#  Time Complexity : O(1)
# Space Complexity : O(capacity)
from collections import defaultdict

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.key_to_value = {}
        self.key_to_frequency = {}
        self.frequency_to_keys = defaultdict(OrderedDict)
        self.min_frequency = 0

    def get(self, key: int) -> int:
        if key not in self.key_to_value:
            return -1

        self.update_frequency(key)
        return self.key_to_value[key]

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.key_to_value:
            self.key_to_value[key] = value
            self.update_frequency(key)
            return

        if len(self.key_to_value) >= self.capacity:
            self.evict()

        self.key_to_value[key] = value
        self.key_to_frequency[key] = 1
        self.frequency_to_keys[1][key] = None
        self.min_frequency = 1

    def update_frequency(self, key):
        frequency = self.key_to_frequency[key]
        del self.frequency_to_keys[frequency][key]

        if len(self.frequency_to_keys[frequency]) == 0 and frequency == self.min_frequency:
            self.min_frequency += 1

        frequency += 1
        self.key_to_frequency[key] = frequency
        self.frequency_to_keys[frequency][key] = None

    def evict(self):
        keys = self.frequency_to_keys[self.min_frequency]
        key_to_remove = next(iter(keys))
        del keys[key_to_remove]
        del self.key_to_value[key_to_remove]
        del self.key_to_frequency[key_to_remove]

        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)