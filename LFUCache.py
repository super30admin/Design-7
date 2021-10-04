"""
Time Complexity : O(1) for both get() and put()
Space Complexity : O(n) where n is the capacity
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No
"""
class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.count = 1
        self.prev = None
        self.next = None
# Doubly Linked List class
class DLL:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
        
    # Add a new node to the head of DLL
    def addToHead(self, node):
        node.next = self.head.next
        node.next.prev = node
        node.prev = self.head 
        self.head.next = node
        self.size += 1
        
    # Remove a node from the DLL
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1
    
    # Remove a node from the end of DLL
    def removeFromEnd(self):
        lastNode = self.tail.prev
        self.removeNode(self.tail.prev)
        return lastNode
    
class LFUCache:

    def __init__(self, capacity: int):
        self.hashMap = {}
        self.freqMap = collections.defaultdict(DLL)
        self.capacity = capacity
        self.mini = 0
    def get(self, key: int) -> int:
        # To get a key we check whether the key is in the hashMap or not if it isn't 
        # we simply return -1 else we enter it inside the map and update the node by
        # updating the frequincy and connecting it to a diff freq value DLL
        if key not in self.hashMap: return -1
        node = self.hashMap[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        # Case: 1 Node exists
        if key in self.hashMap:
            node = self.hashMap[key]
            node.value = value
            self.update(node)
            return
        if self.capacity == 0: return
        # When we reach the capacity we simply get the DLL from the minimum freq and
        # remove a node from the end of DLL and remove it in the hashmap as well
        if self.capacity == len(self.hashMap):
            oldList = self.freqMap[self.mini]
            lastNode = oldList.removeFromEnd()
            del self.hashMap[lastNode.key]
        # We also need to add it to the minimum frequency map value a new node as it 
        # it does not exist in the map. W do that by adding it to the head node and
        # thus calling addToHead()
        newNode = Node(key,value)
        self.hashMap[key] = newNode
        self.mini = 1
        oldList = self.freqMap.get(self.mini, DLL())
        oldList.addToHead(newNode)
        self.freqMap[self.mini] = oldList
        
    def update(self, node):
        frequency = node.count
        node.count += 1
        oldList = self.freqMap[frequency]
        oldList.removeNode(node)
        # If we find that the DLL size 0 at minimum frequency in Frequency map we
        # increase the minimum frequency size.
        if self.mini == frequency and oldList.size == 0:
            self.mini += 1
        newList = self.freqMap.get(node.count, DLL())
        newList.addToHead(node)
        self.freqMap[node.count] = newList
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)