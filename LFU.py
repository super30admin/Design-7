# Time Complexity: O(1)
# Space Complexity: O(1)
from collections import defaultdict


class LFUCache:

    def __init__(self, capacity):

        self.kvmap = defaultdict()  # Will contain key to value and frequency
        self.vfmap = defaultdict(list)  # Will maintain frequency vs keys
        self.capacity = capacity

    def get(self, key: int) -> int:

        value = self.kvmap.get(key, -1)

        if value == -1:
            return value

        val, freq = value

        self.vfmap[freq].remove(key)

        if not self.vfmap[freq]:
            del self.vfmap[freq]

        self.vfmap[freq + 1].append(key)

        self.kvmap[key] = [val, freq + 1]

        return value[0]

    def put(self, key: int, value: int) -> None:

        if self.capacity <= 0:
            return

        val = self.kvmap.get(key, -1)

        freq = 1

        if val == -1:

            if self.capacity == len(self.kvmap):
                mfreq, klist = min(self.vfmap.items(), key=lambda x: x[0])

                curr_key = klist[0]
                del self.kvmap[curr_key]
                self.vfmap[mfreq].remove(curr_key)

                self.kvmap.update({key: [value, freq]})
                self.vfmap[freq].append(key)
            else:
                self.kvmap.update({key: [value, freq]})
                self.vfmap[freq].append(key)
        else:

            oval, ifreq = val

            self.vfmap[ifreq].remove(key)
            if not self.vfmap[ifreq]:
                del self.vfmap[ifreq]

            self.vfmap[ifreq + 1].append(key)

            self.kvmap[key] = [value, ifreq + 1]

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)