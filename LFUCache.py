"""
Design and implement a data structure for a Least Frequently Used (LFU) cache.

Implement the LFUCache class:

LFUCache(int capacity) Initializes the object with the capacity of the data structure.
int get(int key) Gets the value of the key if the key exists in the cache. 
Otherwise, returns -1.
void put(int key, int value) Update the value of the key if present, 
or inserts the key if not already present. When the cache reaches its capacity, 
it should invalidate and remove the least frequently used key before inserting 
a new item. For this problem, when there is a tie (i.e., two or more keys with the same 
frequency), the least recently used key would be invalidated.
To determine the least frequently used key, a use counter is maintained 
for each key in the cache. 
The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to 1 
(due to the put operation). The use counter for a key in the cache is 
incremented either a get or put operation is called on it.

The functions get and put must each run in O(1) average time complexity.
"""

# Time Complexity : O(1)
# Space Complexity : O(n) n = list size
# Did this code successfully run on VScode : Yes
# Any problem you faced while coding this : No

from typing import List

        
class Node:
    def __init__(self, key, val):
        self.prev = None
        self.next = None

        self.key = key
        self.val = val
        self.cnt = 1


class Dll:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.size = 0

        self.head.next = self.tail
        self.tail.prev = self.head

    def addTolist(self, node):
        node.next = self.head.next
        node.prev = self.head
        node.next.prev = node
        self.head.next = node

        self.size += 1

    def removeNode(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def removeLast(self):
        lastElem = self.tail.prev
        self.removeNode(lastElem)
        return lastElem


class LFUCache:

    def __init__(self, capacity: int):
        self.freqMap = {}
        self.hashMap = {}
        self._min = 1
        self.capacity = capacity

    def update(self, node):
        count = node.cnt
        dll = self.freqMap[count]
        if dll is not None:
            dll.removeNode(node)
        if dll.size == 0 and self._min == node.cnt:
            self._min += 1

        count += 1
        node.cnt = count

        newDll = self.freqMap.get(count, Dll())
        newDll.addTolist(node)
        self.freqMap[count] = newDll

    def get(self, key: int) -> int:
        if key not in self.hashMap:
            return -1

        node = self.hashMap[key]
        self.update(node)

        return node.val

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
            # if in the hashmap
        if key in self.hashMap:
            node = self.hashMap[key]
            node.val = value
            self.update(node)

        else:
            if len(self.hashMap) == self.capacity:
                dll = self.freqMap[self._min]
                node = dll.removeLast()
                del self.hashMap[node.key]

            newNode = Node(key, value)
            self._min = 1
            newDll = self.freqMap.get(self._min, Dll())
            newDll.addTolist(newNode)
            self.freqMap[self._min] = newDll
            self.hashMap[newNode.key] = newNode

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)