from collections import defaultdict
class Node:
    def __init__(self, key=-1, val=-1, freq=0, prev=None, nxt=None):
        self.key = key
        self.val = val
        self.freq = freq
        self.prev = prev
        self.nxt = nxt

class DLL:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.nxt = self.tail
        self.tail.prev = self.head
        self.len = 0
    
    def addToHead(self, node):
        node.nxt = self.head.nxt
        node.prev = self.head
        self.head.nxt = node
        node.nxt.prev = node
        self.len += 1
    
    def remove(self, node):
        node.prev.nxt = node.nxt
        node.nxt.prev = node.prev
        self.len -= 1

    def removeFromTail(self):
        node = self.tail.prev
        self.remove(node)
        return node

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.minFreq = 0

        self.keyMap = defaultdict(Node)
        self.freqMap = defaultdict()
    
    def updateNode(self, node):
        #remove from old freqList
        oldFreq = node.freq

        oldDll = self.freqMap[oldFreq]
        oldDll.remove(node)

        if self.minFreq == oldFreq and oldDll.len == 0:

            self.minFreq += 1

        #add to freq + 1 list
        newFreq = oldFreq + 1
        if newFreq not in self.freqMap:
            self.freqMap[newFreq] = DLL()
        self.freqMap[newFreq].addToHead(node)
        node.freq = newFreq
        
    def get(self, key: int) -> int:
        if key not in self.keyMap:
            return -1
        node = self.keyMap[key]
        self.updateNode(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if key in self.keyMap:
            node = self.keyMap[key]
            node.val = value
            self.updateNode(node)
        else:
            if self.capacity == 0:
                return
            
            if len(self.keyMap) == self.capacity:
                dll = self.freqMap[self.minFreq] #node to remove
                removedNode = dll.removeFromTail()
                del self.keyMap[removedNode.key]
            newNode = Node(key, value)
            self.minFreq = 1
            self.keyMap[key] = newNode
            newNode.freq = 1 
            if 1 not in self.freqMap:
                self.freqMap[1] = DLL()
            self.freqMap[1].addToHead(newNode) 


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)