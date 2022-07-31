# Approach: Use 2 hashmaps, 1st one for key:node mapping and 2nd for freq:DLL mapping
# We need following steps to design a LFU cache:
# Use hashmap of key:nodes to get O(1) lookup time if an item is in cach
# in each node, store the freq count, and use that freq count to find the DLL
# in which the node will belong in the frq map. Every time you update value of an existing node
# or get a node, increase its freq count by 1 and remove it from oldCount freq list to newCount 
# Freq list. Update the minimum variable in LFU in 2 cases:
# Case1: When new item is added - it will be having frq 1 so min=1
# Case2: whatever element had min freq was removed and DLL for that freq is now empty
# TC get() -> O(1),  put -> O(1)
# SC O(N) -> N is proportional to capacity of the LFU

# node for doubly linked list
class Node:
    def __init__(self, k, v):
        self.key = k
        self.val = v
        self.count = 1
        self.next = None
        self.prev = None

# Doubly linked list class
class DLL:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
    
    
    def __len__(self):
        """returns 0 if len is 0 else 1, acts as isEmpty() more than len()"""
        return 0 if self.head.next == self.tail else 1
    
    # Adds given node to head of the DLL
    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node

    # Given a node reference, remove it from DLL
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
       
    # Remove the node just before tail (remove last node)
    # and returns its reference 
    def removeTail(self):
        nodeToRemove = self.tail.prev
        self.removeNode(nodeToRemove)
        return nodeToRemove
        
        
class LFUCache:

    def __init__(self, capacity: int):
        self.nodeMap = {}  # map of key(node_value)-> node
        self.freqMap = {}  # map of freq->DLL
        self.cap = capacity
        self.min = 1

    def get(self, key: int) -> int: # O(1)
        if key not in self.nodeMap: return -1
        node =  self.nodeMap.get(key)
        self.update(node)  # moves node from one freq list to other
        return node.val

    def put(self, key: int, value: int) -> None:
        if key in self.nodeMap: # Existing node update
            node = self.nodeMap.get(key) # get the node
            node.val = value # update its value
            self.update(node) # update its location in freq map
        else: # new key
            # if cap is 0 we can't add node to LFU cache
            if self.cap == 0 : return
            if self.cap <= len(self.nodeMap): # if capacity is full
                minFreq = self.freqMap.get(self.min) # find DDL with lowest freq
                rmNode = minFreq.removeTail() # remove the tail/last in DLL- LRU
                del self.nodeMap[rmNode.key] # also remove that node from nodeMap
            newNode = Node(key,value) # newNode always gets added to "min" =  1
            self.min = 1
            li = self.freqMap.get(self.min, DLL()) # if self.min doesn't exist then we instantiate new DLL() else get the existing DLL ref
            li.addToHead(newNode) # add newNode to DLL
            self.freqMap[self.min] = li # update freq map with updated DLL li
            self.nodeMap[key] = newNode # update node map with new key:newNode
        
    def update(self, node): # move from old freq list to new freq list
        oldCount = node.count # store current node count
        oldFreqList = self.freqMap.get(node.count) # find the corresponding freq list
        oldFreqList.removeNode(node) # remove the node from that freq list
        if oldCount == self.min and len(oldFreqList) == 0: # if oldcount was same as min and that list is now empty, update the min
            self.min += 1
        node.count += 1 # update node count
        # get or create DLL() for new node.count in freq list
        newList = self.freqMap.get(node.count, DLL())
        # add given node to newList
        newList.addToHead(node)
        # update freq map for node.count with newList
        self.freqMap[node.count] = newList

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)