'''
Time: O(1) all methods
Space: O(n)
'''

class Node:
    def __init__(self, key=-1, val=-1):
        self.key = key
        self.val = val
        self.next = None
        self.prev = None
        self.freq = 1

class DLList:
    def __init__(self):
        self.head = Node()
        self.tail = Node()
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0 
    
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1
        return node
    
    def addtohead(self, node):
        node.next = self.head.next
        self.head.next.prev = node
        node.prev = self.head
        self.head.next = node
        self.size += 1
        
  
class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.min = 0
        self.hm = dict()
        self.freqmap = dict()
        

    def get(self, key: int) -> int:
        if key in self.hm:
            node = self.hm[key]
            self.update(node)
            return node.val
        return -1
            

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        if key in self.hm:
            node = self.hm[key]
            node.val = value
            self.update(node)
        else:
            if self.capacity == len(self.hm):
                dlist = self.freqmap[self.min]
                node = dlist.removeNode(dlist.tail.prev)
                self.hm.pop(node.key)
            node = Node(key, value)
            self.min = 1
            if self.min not in self.freqmap:
                self.freqmap[self.min] = DLList()
            dlist = self.freqmap[self.min]
            dlist.addtohead(node)
            self.hm[key] = node
    
    def update(self, node):
        oldc = node.freq
        newc = node.freq + 1
        dlist = self.freqmap[oldc]
        dlist.removeNode(node)
        if oldc == self.min and dlist.size == 0:
            self.min += 1
        if newc not in self.freqmap:
            self.freqmap[newc] = DLList()
        dlist = self.freqmap[newc]
        node.freq += 1
        dlist.addtohead(node)
        
        
                
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)







# Two more approaches pending - using trees and priority queues at each node
