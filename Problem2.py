# Time Complexity : O(1) 
# Space Complexity :O(n)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Your code here along with comments explaining your approach

class Node:
    def __init__(self, key, value):
        self.key = key 
        self.value = value 
        self.next = None 
        self.prev = None 
        self.count = 1  

class DLList:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail 
        self.tail.prev = self.head 
        self.size = 0
        
    def addToHead(self, node):
        node.next = self.head.next 
        node.prev = self.head 
        self.head.next = node 
        node.next.prev = node 
        self.size += 1 
    
    def removeNode(self, node):
        node.next.prev = node.prev 
        node.prev.next = node.next 
        self.size -= 1 
        
    def removeLast(self):
        node = self.tail.prev 
        self.removeNode(node)
        return node
    
class LRUCache:
    def __init__(self, capacity):
        self.freq = {}          # <Integer, DLList>
        self.map = {}           # <Integer, Node>
        self.capacity = capacity
        self.min = 0
        
    def get(self, key):
        if key in self.map:
            node = self.map[key]
            self.update(node)       # to update the frequency of the node as it was called
            return node.value
        return -1
    
    def put(self, key, value):
        if key in self.map:
            node = self.map[key]
            node.val = value 
            self.update(node)
        else:
            if self.capacity == 0:
                return 
            
            if len(self.map) == self.capacity:  # Capacity is full 
                minList = self.freq[self.min]
                nodeToBeRemoved = minList.removeLast()
                if nodeToBeRemoved.key != -1:
                    self.map.pop(nodeToBeRemoved.key)
            
            newNode = Node(key, value)
            self.min = 1 
            if self.min not in self.freq:
                self.freq[self.min] = DLList()
            
            minList = self.freq[self.min]
            minList.addToHead(newNode)
            self.freq[min] = minList
            self.map[key] = newNode
    
    def update(self, node):
        oldList = self.freq[node.count]
        oldList.removeNode(node)
        if node.count == self.min and oldList.size == 0:
            self.min += 1 
        node.count += 1 
        if node.count not in self.freq:
            self.freq[node.count] = DLList()
        
        newList = self.freq[node.count]
        newList.addToHead(node)
        self.freq[node.count] = newList 
        self.map[node.key] = node 

if __name__ == "__main__":
    s = LRUCache(2)
    output = [None]
    
    # Test case 1 
    output.append(s.put(1, 1))
    
    # Test case 2
    output.append(s.put(2, 2))
    
    # Test case 3
    output.append(s.get(1))
    
    # Test case 4
    output.append(s.put(3, 3))
    
    # Test case 5
    output.append(s.get(2))
    
    # Test case 6 
    output.append(s.get(3))
    
    # Test case 7 
    output.append(s.put(4, 4))
    
    # Test case 8
    output.append(s.get(1))
    
    # Test case 9
    output.append(s.get(3))
    
    # Test case 10
    output.append(s.get(4))

    print(output)                    
    
        
        
        
        
        
        
        