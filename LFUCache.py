"""
Description: LRUCache data structure for least frequently used cache

Time Complexity: O(1) 
Space Complexity: O(capacity)

Approach:
- Use two dictionaries, one with key with node containing key, value and frequency. Another one with key as frequency and doubly linked list for items with same frequency
- define adding node to head, remove a node, and remove last node methods for doubly linked list

get operation: check if the key exist in dictionary with keys and return, update the node to add 1 to it's frequency

put operation: 
- add node with key, update frequency and value if the key already exists, update both dictionaries
- add node with key, if key doesn't exist and the capacity is not fully utilized, update both dictionaries 
- if capacity is full, in update function remove the node either from the end (for same frequencies), or the last accessed node
"""

# node for global node map
class Node:
    
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.cnt = 1
        self.prev = None
        self.next = None

# doubly linked list class
class LinkedList:
    
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
        
    # add node to head of the doubly linked list
    def addtoHead(self, node):
        """
        node.next = self.head
        self.head.prev = node
        node.prev = None
        self.head = node
        self.size += 1
        """
        node.next = self.head.next 
        node.prev = self.head 
        self.head.next = node
        node.next.prev = node
        self.size += 1        
        
    # remove node in between
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1
        
    # remove the last node 
    def removeLast(self):
        tail_prev = self.tail.prev
        self.removeNode(tail_prev)
        return tail_prev
    
class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.node_map = {}
        self.freq_map = {}
        self.min_freq = 0
   
    def get(self, key: int) -> int:
        if key not in self.node_map: return -1
        node = self.node_map[key]
        self.update(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if key in self.node_map:
            node = self.node_map[key]
            node.val = value
            self.update(node)
        else:
            if self.capacity == 0:
                return
            node = Node(key,value)
            if len(self.node_map) == self.capacity:
                minList = self.freq_map[self.min_freq]
                toRemove = minList.removeLast()
                del self.node_map[toRemove.key]

            newNode = Node(key,value)
            self.min_freq = 1
            if self.min_freq not in self.freq_map:
                newList = LinkedList()
            else:
                newList = self.freq_map[self.min_freq]
            newList.addtoHead(newNode)
            self.freq_map[self.min_freq] = newList

            self.node_map[key] = newNode
            
    def update(self, node):
        cnt = node.cnt
        oldList = self.freq_map[cnt]
        oldList.removeNode(node)
        if self.min_freq == cnt and oldList.size == 0:
            self.min_freq += 1
        node.cnt += 1
        if node.cnt not in self.freq_map:
            newDLL = LinkedList()
            self.freq_map[node.cnt] = newDLL
        
        newDLL = self.freq_map[node.cnt]
        newDLL.addtoHead(node)
        self.freq_map[node.cnt] = newDLL   
        
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
