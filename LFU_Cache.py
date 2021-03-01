"""
Time Complexity:    O(1)
Space Complexity:   O(c)- for hashmaps
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No
Here, we are maintaining 2 hashmaps, one will store the keys with value as node reference(like in LRU)
and another will store frequency as key and doubly linked list of nodes with that frequency as a value.
We are maintaining DLL here for all the frequencies as we need the LRU in case of tie. 
"""


class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.freq = 1
        self.prev = None
        self.next = None


class DLL:

    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def insertToHead(self, node):
        node.prev = self.head
        node.next = self.head.next
        self.head.next = node
        node.next.prev = node
        self.size += 1
        return

    def remove(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
        return

    def removeLast(self):
        tempNode = self.tail.prev
        self.remove(tempNode)
        return tempNode


class LFUCache:

    def __init__(self, capacity: int):

        self.nodes = {}
        self.frequency = {}
        self.min = float('inf')
        self.capacity = capacity

    def update(self, node) -> None:
        oldList = self.frequency[node.freq]
        oldList.remove(node)
        if (oldList.size == 0):
            if (node.freq == self.min):
                self.min += 1
        node.freq += 1
        if (node.freq not in self.frequency):
            newList = DLL()
            self.frequency[node.freq] = newList
        newList = self.frequency[node.freq]
        newList.insertToHead(node)

    def get(self, key: int) -> int:
        if (key not in self.nodes):
            return -1
        node = self.nodes[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if (self.capacity == 0):
            return
        if (key in self.nodes):
            node = self.nodes[key]
            node.value = value
            self.update(node)
        else:
            node = Node(key, value)
            if (len(self.nodes) == self.capacity):
                newlist = self.frequency[self.min]
                removed = newlist.removeLast()
                del self.nodes[removed.key]
            if (1 not in self.frequency):
                newList = DLL()
                self.frequency[1] = newList
            newList = self.frequency[1]
            newList.insertToHead(node)
            self.nodes[key] = node
            self.min = 1


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
