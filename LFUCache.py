# Time Complexity : O(1)
# Space Complexity : O(C) c=capacity
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach

class Node:
        def __init__(self, key, val):
            self.key = key
            self.val = val
            self.count = 1
            self.prev = None
            self.next = None
class DLL:
        def __init__(self):
            self.head = Node(-1,-1)
            self.tail = Node(-1,-1)
            self.head.next = self.tail 
            self.tail.prev = self.head
            self.size = 0
            
        def addToHead(self, node):
            node.next = self.head.next 
            node.prev = self.head 
            self.head.next = node
            node.next.prev = node
            self.size += 1
            return
        def removeNode(self, node):
            node.next.prev = node.prev 
            node.prev.next = node.next 
            self.size -= 1
            return
        def removeLast(self):
            tailPrev = self.tail.prev 
            self.removeNode(tailPrev)
            return tailPrev    
class LFUCache:
    
        
    def __init__(self, capacity: int):
        #freqMap of Integer:DLL
        self.freqMap = {}
        #map of Integer:Node
        self.map = {}
        self.capacity = capacity
        self.mn = 0

    def get(self, key: int) -> int:
        if key not in self.map:
            return -1 
        node = self.map[key]
        self.update(node)
        return node.val 

    def put(self, key: int, value: int) -> None:
        if key in self.map:
            #update val and update freqMap
            node = self.map[key]
            node.val = value
            self.update(node) #update freqMap
        else:
            if self.capacity == 0:
                return
            node = Node(key,value)
            if len(self.map) == self.capacity:
                minList = self.freqMap[self.mn]
                toRemove = minList.removeLast()
                del self.map[toRemove.key]
            
            newNode = Node(key,value)
            self.mn = 1
            if self.mn not in self.freqMap:
                newList = DLL()
            else:
                newList = self.freqMap[self.mn]
            newList.addToHead(newNode)
            self.freqMap[self.mn] = newList
            
            self.map[key] = newNode
        
    def update(self, node):
        count = node.count
        oldList = self.freqMap[count]
        oldList.removeNode(node)
        if self.mn == count and oldList.size == 0:
            self.mn += 1
        #transfer to new List with +1 freq
        node.count += 1
        if node.count not in self.freqMap:
            newList = DLL()
            self.freqMap[node.count] = newList
            
        newList = self.freqMap[node.count]
        newList.addToHead(node)
        self.freqMap[node.count] = newList
        
        
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)