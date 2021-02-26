class LFUCache:
    #Approach: Two HashMaps and Doubly-Linked-Lists
    #Time Complexity: O(1) for everything
    #Space Complexity: O(capacity)

    def __init__(self, capacity: int):
        self.map, self.freqMap = {}, {}
        self.min = None
        self.capacity = capacity

    def get(self, key: int) -> int:
        if key not in self.map:
            return -1
        
        node = self.map[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        
        if key in self.map:
            node = self.map[key]
            node.value = value
            self.update(node)
            return
        
        if len(self.map) == self.capacity:
            removeFromList = self.freqMap[self.min]
            removedNode = removeFromList.removeFromTail()
            del self.map[removedNode.key]
            
        node = Node(key, value)
        self.map[node.key] = node
        addToList = self.freqMap.get(1, DLList())
        addToList.addToHead(node)
        self.freqMap[1] = addToList
        self.min = 1
        return
    
    #moves a node from one DLL to another (w/ freq = freq + 1)
    def update(self, node):
        freq = node.freq
        oldList = self.freqMap[freq]
        oldList.removeNode(node)
        if freq == self.min and oldList.head.next == oldList.tail:   #empty list after removal
            self.min += 1
            
        node.freq += 1
        newList = self.freqMap.get(node.freq, DLList())
        newList.addToHead(node)
        self.freqMap[node.freq] = newList
        return
    
class Node:
    def __init__(self, key, value):
        self.key, self.value = key, value
        self.freq = 1
        self.next, self.prev = None, None
        
class DLList:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        
    def addToHead(self, node):
        node.prev = self.head
        node.next = self.head.next
        node.next.prev = node
        self.head.next = node
        
    def removeNode(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        
    def removeFromTail(self):
        removedNode = self.tail.prev
        self.removeNode(removedNode)
        return removedNode

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)