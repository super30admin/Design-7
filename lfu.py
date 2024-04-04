"""
Time Complexity: Average O(1) for all the methods
"""
from collections import defaultdict


class Node:
    def __init__(self, key, value, prev=None, next=None, frequency=1):
        self.key = key
        self.value = value
        self.frequency = frequency
        self.prev = None
        self.next = None


class DLL:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.size = 0
        self.head.next = self.tail
        self.tail.prev = self.head

    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size += 1

    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1

    def removeLastNode(self):
        prevNode = self.tail.prev
        self.removeNode(prevNode)
        return prevNode


class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.hashMap = defaultdict(Node)
        self.freqMap = defaultdict(DLL)
        self.minFreq = 0

    def get(self, key: int) -> int:
        if key in self.hashMap:
            node = self.hashMap[key]
            self.update(node)
            return node.value
        return -1

    def put(self, key: int, value: int) -> None:
        if key in self.hashMap:
            node = self.hashMap[key]
            node.value = value
            self.update(node)
        else:
            if len(self.hashMap) == self.capacity:
                ddl = self.freqMap[self.minFreq]
                node = ddl.removeLastNode()
                self.hashMap.pop(node.key)
            newNode = Node(key, value)
            self.minFreq = 1
            self.hashMap[key] = newNode
            if newNode.frequency not in self.freqMap:
                self.freqMap[newNode.frequency] = DLL()
            newDdl = self.freqMap[newNode.frequency]
            newDdl.addToHead(newNode)

    def update(self, node):
        oldDdl = self.freqMap[node.frequency]
        oldDdl.removeNode(node)
        if node.frequency == self.minFreq and oldDdl.size == 0:
            self.minFreq += 1
        node.frequency += 1
        if node.frequency not in self.freqMap:
            self.freqMap[node.frequency] = DLL()
        newDDL = self.freqMap[node.frequency]
        newDDL.addToHead(node)


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
