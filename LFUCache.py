# Time Complexity : O(1) for both get() and put()
# Space Complexity : O(capacity) for storing nodes and frequency lists.
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Approach : Whenever we access or update a key, we increase its frequency count and move it to the updated bucket.
# If the cache is full and a new key needs to be inserted, we evict the least frequently used one from the lowest frequency list.
# This way, both frequency and recency are tracked, and we always get or put in O(1) time.

class LFUCache:

    class Node:
        def __init__(self, key, value):
            self.key = key
            self.value = value
            self.frq = 1
            self.prev = None
            self.next = None

    class DLList:
        def __init__(self):
            self.head = LFUCache.Node(-1, -1)
            self.tail = LFUCache.Node(-1, -1)
            self.head.next = self.tail
            self.tail.prev = self.head
            self.size = 0

        def insertNodeToHead(self, node):
            node.prev = self.head
            node.next = self.head.next
            self.head.next.prev = node
            self.head.next = node
            self.size += 1

        def removeNode(self, node):
            node.prev.next = node.next
            node.next.prev = node.prev
            node.prev = None
            node.next = None
            self.size -= 1

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.map = {}
        self.freqMap = {}
        self.minFrq = 0

    def update(self, node):
        oldFrq = node.frq
        oldFrqList = self.freqMap[oldFrq]
        oldFrqList.removeNode(node)

        if oldFrq == self.minFrq and oldFrqList.size == 0:
            self.minFrq += 1

        newFrq = oldFrq + 1
        node.frq = newFrq
        newFrqList = self.freqMap.get(newFrq, LFUCache.DLList())
        newFrqList.insertNodeToHead(node)
        self.freqMap[newFrq] = newFrqList

    def get(self, key: int) -> int:
        if key not in self.map:
            return -1
        node = self.map[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.map:
            node = self.map[key]
            node.value = value
            self.update(node)
        else:
            if len(self.map) == self.capacity:
                minFrqList = self.freqMap[self.minFrq]
                lastNode = minFrqList.tail.prev
                minFrqList.removeNode(lastNode)
                del self.map[lastNode.key]

            self.minFrq = 1
            newNode = LFUCache.Node(key, value)
            self.map[key] = newNode
            minFrqList = self.freqMap.get(self.minFrq, LFUCache.DLList())
            minFrqList.insertNodeToHead(newNode)
            self.freqMap[self.minFrq] = minFrqList
