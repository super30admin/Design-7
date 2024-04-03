'''
Time Complexity - O(1). For all the operations
Space Complexity - O(n). We are maintaining maps for storing the nodes and also the linked list for frequencies

Works on Leetcode
'''



class Node:
    def __init__(self, key, value):
        #initialize a node
        self.key = key
        self.val = value
        self.count = 1
        self.next = None
        self.prev = None

class DLList:
    def __init__(self):
        #create the doubly linked list
        self.head = Node(-1, -1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
    
    def addToHead(self, node):
        #insert at head of a doubly linked list
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size+=1

    def removeNode(self, node):
        #remove a Node from a doubly linked list
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size-=1
    
    def removeTail(self):
        #remove from the tail of a doubly linked list
        node = self.tail.prev
        self.removeNode(node)
        return node

class LFUCache:
    def __init__(self, capacity: int):
        #initiating the LFU cache
        self.min = 2 * (10**5) + 1
        self.map = {}
        self.freqMap = {}
        self.capacity = capacity
    
    def update(self, node):
        #update the frequency count of the node, move from freqList to new freqList, make changes to min accordingly
        oldList = self.freqMap.get(node.count)
        oldList.removeNode(node)
        if node.count == self.min and oldList.size == 0:
            self.min+=1
        node.count+=1
        newList = self.freqMap.get(node.count, DLList())
        newList.addToHead(node)
        self.freqMap[node.count] = newList

    def get(self, key: int) -> int:
        #get a node with key, update the count accordingly, return -1 if not present
        if key not in self.map:
            return -1
        node = self.map.get(key)
        self.update(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        #insert a node
        if key in self.map:
            #if node previously present, update
            node = self.map.get(key)
            node.val = value
            self.update(node)
        else:
            #insert a fresh Node
            if self.capacity == len(self.map):
                #if the the cache is at capacity, remove least frequent node
                minFreq = self.freqMap.get(self.min)
                toRemove = minFreq.removeTail()
                del self.map[toRemove.key]
            #create new node, its frequency will be 1 and add cache
            newNode = Node(key,value)
            self.min = 1
            minFreq = self.freqMap.get(self.min, DLList())
            minFreq.addToHead(newNode)
            self.freqMap[self.min] = minFreq
            self.map[key] = newNode

        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)