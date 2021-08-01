# Time Complexity : O(n)
# Space Complexity : O(n)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


#saving the frequency map and key map to maintain both LFU and LRU

class Node:
    def __init__(self,key,value):
        self.key = key
        self.value = value
        self.count = 1
        self.next = None
        self.prev = None
    
class DLL:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
        
    def addToHead(self,node):
        node.next = self.head.next
        node.next.prev = node
        self.head.next = node
        self.head.next.prev = self.head
        self.size += 1
    
    def remove(self,node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
        
    
        

class LFUCache:

    def __init__(self, capacity: int):
        self.keyMap = {}
        self.freqMap = {}
        self.capacity = capacity
        self.minFreq = 0
        

    def get(self, key: int) -> int:
        if self.capacity == 0:
            return -1
        if self.minFreq == 0:
            return -1
        
        if key not in self.keyMap:
            return -1
        node = self.keyMap[key]
        count = node.count
        dll = self.freqMap[count]
        dll.remove(node)
        if self.minFreq == count and dll.size == 0:
            del self.freqMap[count]
            self.minFreq += 1
        
        node.count += 1
        count = node.count
        if count not in self.freqMap:
            self.freqMap[count] = DLL()
        dll = self.freqMap[count]
        
        dll.addToHead(node)
        
        return node.value
        
        
        

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return;
        if key in self.keyMap:
            node = self.keyMap[key]
            self.get(key)
            node.value = value
            return
        if self.capacity == len(self.keyMap):
            dll = self.freqMap[self.minFreq]
            
            node = dll.tail.prev
            dll.remove(node)
            del self.keyMap[node.key]
        
        node = Node(key,value)
        
        if 1 not in self.freqMap:
            self.freqMap[1] = DLL()
        dll = self.freqMap[1]
        
        dll.addToHead(node)
        self.minFreq = 1
        self.keyMap[key] = node
        
        
            
            
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)