# Time Complexity : O(1)
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach
# Using 2 HashMaps and Doubly Linked List.
# Using 1st HashMap to store key mapped to nodes along with frequencies
# Using 2nd HashMap to store frequency as key mapped to doubly linkedlist to add/remove nodes in O(1)
# Add to Head and remove from tail


class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.prev = None
        self.next = None
        self.freq = 1

class DLL:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.tail.next = None
        self.size = 0

    def addNode(self, node):
        node.next = self.head.next
        self.head.next = node
        node.prev = self.head
        node.next.prev = node
        self.size += 1
        
    def removeNode(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

class LFUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.dict = {}
        self.freqdict= {}
        self.minfreq = 0
    
    def get(self, key: int) -> int:
        if key in self.dict:
            node = self.dict[key]
            oldfreq = node.freq
            self.freqdict[node.freq].removeNode(node)
            node.freq += 1
            if oldfreq == self.minfreq and self.freqdict[oldfreq].size == 0:
                self.minfreq = node.freq
            if node.freq not in self.freqdict:
                dll = DLL()
                self.freqdict[node.freq] = dll
            self.freqdict[node.freq].addNode(node)
            return node.value
        return -1
        
    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        #Existing key
        if key in self.dict:
            self.dict[key].value = value
            self.get(key)
            return
        # New key: evict if full
        if len(self.dict) == self.capacity:
            dll = self.freqdict[self.minfreq]
            node = dll.tail.prev
            dll.removeNode(node)
            del self.dict[node.key]
        #Insert new node
        new_node = Node(key, value)
        freq = new_node.freq
        self.dict[key] = new_node
        if freq not in self.freqdict:
            self.freqdict[freq] = DLL()
        self.freqdict[freq].addNode(new_node)
        self.minfreq = freq
            
                

        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)