"""
Use freq map with doubly linked list to maintain access order and frequency and use dictionary to get node in O(1) time

TC: O(1)
SP:O(1) for user ops 
"""
class Node:
    def __init__(self, key, val):
        self.val = val
        self.key = key
        self.freq = 1
        self.prev = None
        self.next = None


class DLList:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def remove(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next.prev = node
        self.head.next = node
        self.size += 1


class LFUCache:
    def __init__(self, capacity: int):
        self.lfucache = {}
        self.frqMap = {}
        self.capacity = capacity
        self.count = 0
        self.minfreq = 0

    def update(self, node):
        oldFreq = node.freq
        oldFreqList = self.frqMap[oldFreq]
        oldFreqList.remove(node)
        if oldFreq == self.minfreq and oldFreqList.size == 0:
            self.minfreq += 1
        newFreq = oldFreq + 1
        node.freq = newFreq
        newFreqList = self.frqMap.get(newFreq, DLList())
        newFreqList.addToHead(node)
        self.frqMap[newFreq] = newFreqList

    def get(self, key: int) -> int:
        if key not in self.lfucache:
            return -1
        node = self.lfucache[key]
        self.update(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if key not in self.lfucache:
            if self.count == self.capacity:
                minFreqList = self.frqMap[self.minfreq]
                toRemove = minFreqList.tail.prev
                del self.lfucache[toRemove.key]
                minFreqList.remove(toRemove)
                self.count -= 1
            newNode = Node(key, value)
            self.lfucache[key] = newNode
            self.count += 1
            self.minfreq = 1
            minFreqList = self.frqMap.get(self.minfreq, DLList())
            minFreqList.addToHead(newNode)
            self.frqMap[self.minfreq] = minFreqList


        else:
            node = self.lfucache[key]
            node.val = value
            self.update(node)



# Your LRUCache object will be instantiated and called as such:
# obj = LRUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
