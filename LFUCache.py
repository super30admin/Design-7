"""
Use key - (value, freq) map to store the value and its usage frequency
Use a freq - OrderedDict map to track keys by usage frequency in LRU order
Track the minFreq to remove the least frequently used and least recently used key when needed
"""
"""
Time Complexity: get() - O(1); put() - O(1)
Space Complexity: O(capacity)
"""

from collections import defaultdict, OrderedDict

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.minFreq = 0
        self.keyToValFreq = dict()  
        self.freqToKeys = defaultdict(OrderedDict)  

    def get(self, key: int) -> int:
        if key not in self.keyToValFreq:
            return -1

        val, freq = self.keyToValFreq[key]
      
        del self.freqToKeys[freq][key]
        if not self.freqToKeys[freq]:
            del self.freqToKeys[freq]
            if freq == self.minFreq:
                self.minFreq += 1

  
        self.freqToKeys[freq + 1][key] = None
        self.keyToValFreq[key] = (val, freq + 1)
        return val

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.keyToValFreq:
            self.keyToValFreq[key] = (value, self.keyToValFreq[key][1])
            self.get(key) 
            return

        if len(self.keyToValFreq) >= self.capacity:
            evictKey, _ = self.freqToKeys[self.minFreq].popitem(last=False)
            del self.keyToValFreq[evictKey]

 
        self.keyToValFreq[key] = (value, 1)
        self.freqToKeys[1][key] = None
        self.minFreq = 1


if __name__ == "__main__":
    cache = LFUCache(2)
    cache.put(1, 1)
    cache.put(2, 2)
    print(cache.get(1))  
    cache.put(3, 3)     
    print(cache.get(2))  
    print(cache.get(3))  
    cache.put(4, 4)     
    print(cache.get(1)) 
    print(cache.get(3))
    print(cache.get(4)) 
