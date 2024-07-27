# Time Complexity : O(1)
# Space Complexity : O(n), where n is the capacity of the cache

class Node:
    def __init__(self, key, value, freq):
        self.key = key
        self.value = value
        self.freq = freq
        self.prev = None
        self.next = None

class DLList:
    def __init__(self):
        self.head = Node(0, 0, 0)
        self.tail = Node(0, 0, 0)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def add(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next.prev = node
        self.head.next = node
        self.size += 1

    def remove(self, node):
        prev = node.prev
        next = node.next
        prev.next = next
        next.prev = prev
        self.size -= 1

    def remove_last(self):
        if self.size > 0:
            node = self.tail.prev
            self.remove(node)
            return node
        return None

class LFUCache:
    def __init__(self, capacity):
        self.cache = {}
        self.freqMap = {}
        self.size = 0
        self.capacity = capacity
        self.minFreq = 0

    def get(self, key):
        if key not in self.cache:
            return -1
        node = self.cache[key]
        self.update(node)
        return node.value

    def put(self, key, value):
        if self.capacity == 0:
            return
        if key in self.cache:
            node = self.cache[key]
            node.value = value
            self.update(node)
        else:
            if self.size == self.capacity:
                oldList = self.freqMap[self.minFreq]
                node_to_remove = oldList.remove_last()
                if node_to_remove:
                    del self.cache[node_to_remove.key]
                    self.size -= 1
            newNode = Node(key, value, 1)
            self.cache[key] = newNode
            if 1 not in self.freqMap:
                self.freqMap[1] = DLList()
            self.freqMap[1].add(newNode)
            self.size += 1
            self.minFreq = 1

    def update(self, node):
        oldList = self.freqMap[node.freq]
        oldList.remove(node)
        if node.freq == self.minFreq and oldList.size == 0:
            self.minFreq += 1
        node.freq += 1
        if node.freq not in self.freqMap:
            self.freqMap[node.freq] = DLList()
        self.freqMap[node.freq].add(node)

# Examples

# Example 1
cache = LFUCache(2)
cache.put(1, 1)
cache.put(2, 2)
print(cache.get(1))  # returns 1
cache.put(3, 3)      # removes key 2
print(cache.get(2))  # returns -1 (not found)
print(cache.get(3))  # returns 3
cache.put(4, 4)      # removes key 1
print(cache.get(1))  # returns -1 (not found)
print(cache.get(3))  # returns 3
print(cache.get(4))  # returns 4

# Example 2
cache = LFUCache(0)
cache.put(0, 0)
print(cache.get(0))  # returns -1 (capacity is 0)

# Example 3
cache = LFUCache(3)
cache.put(1, 1)
cache.put(2, 2)
cache.put(3, 3)
print(cache.get(1))  # returns 1
cache.put(4, 4)      # removes key 2
print(cache.get(2))  # returns -1 (not found)
print(cache.get(3))  # returns 3
print(cache.get(4))  # returns 4