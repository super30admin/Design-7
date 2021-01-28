"""
Approach: Using two hashmaps and doubly linkedlist

Here we will maintain two data structures.

Hashmap 1: Two store <key, Node(key, value, frequency)>
Hashmap 2(frequecy map): Two store <Frequency f, DDList(Node(key k1, value v1, f) -> Node(key k1, value v1, f))>

Now the nature of doubly-linkedlist would be something like this. First node is recently used node and last node is the less recently used node for the given frequency f.

Lets take an example. Lets say we are going to put (1,1), (2,2), (3,3), (4,4), (5,5)

Now when we put (1,1) our DS will look like this

h1 = {1: Node(1,1,1) -> None}
h2 = {1: DDList(Node(1,1,1))}

Put (2,2), (3,3), (4,4), (5,5)

h1 = {1: Node(1,1,1)
      2: Node(2,2,1)
      3: Node(3,3,1)
      4: Node(4,4,1)
      5: Node(5,5,1)
      }
h2 = {1: DDList(Node(5,5,1) -> Node(4,4,1) -> Node(3,3,1) -> Node(2,2,1) -> Node(1,1,1) -> None)}

Here last added <k,v> pair was (5,5) so it will show first in h2 for frequency 1

Now when we do get(2) ds changes like this
h1 = {1: Node(1,1,1)
      2: Node(2,2,2) <- frequency change
      3: Node(3,3,1)
      4: Node(4,4,1)
      5: Node(5,5,1)
      }

h2 = {1: DDList(Node(5,5,1) -> Node(4,4,1) -> Node(3,3,1) -> Node(1,1,1) -> None)
      2: DDList(Node(2,2,2) -> None)}
      
Do get(4)      

h1 = {1: Node(1,1,1)
      2: Node(2,2,2) 
      3: Node(3,3,1)
      4: Node(4,4,2) <- frequency change
      5: Node(5,5,1)
      }

h2 = {1: DDList(Node(5,5,1) ->  Node(3,3,1) -> Node(1,1,1) -> None)
      2: DDList( Node(4,4,2) -> Node(2,2,2) -> None)}

Here Node(4,4,2) gets added infront of the DD List telling its the most recently acccessed

So whenever we do a get() or put() that nodes usage frequency changes. That means we need update value for it in h1 and h2.

TC: O(1)
SC: O(n)
"""

class Node:
    def __init__(self,  key, value):
        self.key = key
        self.value = value
        self.count = 1
        self.next = None
        self.prev = None
            
class DDLList:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def addToHead(self, node):
        node.next = self.head.next
        self.head.next.prev = node
        self.head.next = node
        node.prev = self.head
        self.size += 1 

    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1

    def removeLastNode(self):
        tailPrev = self.tail.prev
        self.removeNode(tailPrev)
        return tailPrev
        
class LFUCache:
    def __init__(self, capacity: int):
        self.hmap = {}
        self.freqMap = {}
        self.min = None
        self.capacity = capacity

    #  TC: O(1)
    def get(self, key: int) -> int:
        if key in self.hmap:
            node = self.hmap[key]
            self.update(node)
            return node.value
    
        return -1

    # TC: O(1)    
    def put(self, key: int, value: int) -> None:
        if key in self.hmap:
            node = self.hmap[key]
            node.value = value
            self.update(node)
            
        else:
            if self.capacity == 0:
                return
            
            if len(self.hmap) == self.capacity: 
                # remove LFU element
                removeList = self.freqMap[self.min]
                removedNode = removeList.removeLastNode()
                del self.hmap[removedNode.key]
            
            newNode = Node(key,value)
            self.min = 1
            self.hmap[key] = newNode
            
            if self.min not in self.freqMap:
                self.freqMap[self.min] = DDLList()
            
            newList = self.freqMap[self.min]
            newList.addToHead(newNode)
            self.freqMap[self.min] = newList
    
    # TC: O(1)
    def update(self, node):
        count = node.count
        node.count += 1
        
        # Now if the count is changed, we need tu updayte the freqMap. Query the freqMap and remove it from wherever it is present and put at its right place for the new count as the key
        
        oldList = self.freqMap[count]
        oldList.removeNode(node)
        
        if self.min == count and oldList.size == 0:
            self.min += 1
            
        if node.count not in self.freqMap:
            self.freqMap[node.count] = DDLList()
            
        newList = self.freqMap[node.count]
        newList.addToHead(node)
        self.freqMap[node.count] = newList
        
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)