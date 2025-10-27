# Time Complexity : O(1) as all operations are done in constant time
# Space Complexity : O(C) where C is the capacity of the cache
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach:
# I am using 2 hashmaps and 2 doubly linked lists to implement the LFU Cache
# The first hashmap (map) stores the key and its corresponding node
# The second hashmap (freqMap) stores the frequency and its corresponding doubly linked list of nodes
# Each node contains the key, value, frequency, and pointers to the previous and next nodes
# The doubly linked list contains a head and tail node to facilitate easy addition and removal of nodes
# The DDL class has methods to add a node to the head and remove a node from the list
# The LFUCache class has methods to get and put nodes in the cache. 
# The get method retrieves the value of the key if it exists and updates its frequency and removes it from the old frequency list and adds it to the new frequency list
# The put method adds a new key-value pair to the cache or updates an existing key's value and frequency. If the cache is at capacity, it removes the least frequently used node before adding the new node.


class Node:
    def __init__(self,key,val):
        self.val = val
        self.key = key
        self.freq = 1
        self.prev = None
        self.next = None
class DDL:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
    def remove(self,node):
        node.next.prev = node.prev
        node.prev.next = node.next
        node.prev = None
        node.next = None
        self.size -= 1
    def addToHead(self,node):
        node.prev = self.head
        node.next = self.head.next
        node.next.prev = node
        self.head.next = node
        self.size += 1

class LFUCache:

    def __init__(self, capacity: int):
        self.size = capacity
        self.map = {}
        self.freqMap = {}
        self.minFreq = 0

    
    def update(self,node):
        oldFreq = node.freq
        oldFreqList = self.freqMap[oldFreq]
        oldFreqList.remove(node)
        if oldFreq == self.minFreq and oldFreqList.size == 0:
            self.minFreq += 1
        newFreq = oldFreq + 1
        node.freq = newFreq
        newFreqList = self.freqMap.get(newFreq,DDL())
        newFreqList.addToHead(node)
        self.freqMap[newFreq] = newFreqList

    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            self.update(node)
            return node.val
        return -1
    
    def put(self, key: int, value: int) -> None:
        if key in self.map:
            node = self.map[key]
            node.val = value
            self.update(node)
        else:
            if len(self.map) == self.size:
                minFreqList = self.freqMap[self.minFreq]
                last = minFreqList.tail.prev
                minFreqList.remove(last)
                del self.map[last.key]
            newNode = Node(key,value)
            # self.addToHead(newNode)
            self.map[key] = newNode
            self.minFreq = 1
            freqList = self.freqMap.get(self.minFreq,DDL())
            freqList.addToHead(newNode)
            self.freqMap[self.minFreq] = freqList 
 
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)