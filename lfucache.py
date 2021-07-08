# Time complexity - O(1)
# Space complexity - O(C) # O(C) - for key node mapping, O(C) for cache 
# Did this code run on leetcode? - yes

# Class Node
class Node:
    def __init__(self, key, val):
        self.key = key
        self.value = val
        self.cnt = 1
        self.next = self.prev = None
        
# Class to create a soubly linked list.
# contains methods to be performed on a doubly linked list.
class DLList:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.size = 0               # size of doubly linked list will be zero, initially.
        # connect head to tail pointers
        self.head.next = self.tail
        self.tail.prev = self.head
    
    def addNodeToHead(self, node):
        # add node to the head
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size += 1
    
    def removeNode(self, node):
        # remove node from anywhere in the linked list.
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
        
    def removeNodeFromTail(self):
        # remove node from the end of the linked list.
        node = self.tail.prev
        self.tail.prev = node.prev
        node.prev.next = self.tail
        self.size -= 1
        return node
    
# Class to create LFU cache.
class LFUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.cache = dict()     # dictionary that keeps mapping from count -> doubly linked list.
        self.key_node = dict()  # dictionary that keeps the mapping of key with the nodes.
        self.min_v = 0
        self.size = 0           # size to avoid checking the size using key_node hash map.

    def get(self, key: int) -> int:
        if self.min_v == 0 or self.size==0:
            return -1
        if key not in self.key_node:      # if key is not found in the key node.
            return -1
        self.update(self.key_node[key])   # update the count of the key.
        return self.key_node[key].value         # return the value
        
    # function to remove the node from the old linked list corresponding to the old count and update to the new linked list for the new count.
    def update(self, node):
        dllist = self.cache.get(node.cnt) # fetch the linked list
        dllist.removeNode(node)           # remove the existing node from doubly linked list.
        
        if node.cnt == self.min_v and dllist.size == 0:  # increment the minimum value.
            self.min_v += 1
        node.cnt += 1                     # increment the count.
        new_dllist = self.cache.get(node.cnt, DLList())
        new_dllist.addNodeToHead(node)        # add the new node to the head of the doubly linked list.
        self.cache[node.cnt] = new_dllist     # assign the new dll list back.
        

    def put(self, key: int, value: int) -> None:
        # if key is not present in the hash map, create it.
        if self.capacity == 0:
            return 
        if key not in self.key_node:
            self.size += 1
            
            if self.size > self.capacity:
                dllist = self.cache.get(self.min_v)       # fetch the linked list corresponding to the min. value
                node = dllist.removeNodeFromTail()   # no need to update the min_v since it will be anyway updated to 1 later.
             
                del self.key_node[node.key]          # remove node from the key node hash map.
                self.size -= 1
                
            new_node = Node(key, value)
            self.key_node[key] = new_node
            self.min_v = 1
            dllist = self.cache.get(self.min_v, DLList())
            dllist.addNodeToHead(new_node)
            self.cache[new_node.cnt] = dllist
        else:
            # update the node
            new_node = self.key_node[key]
            new_node.value = value
            self.update(new_node)
            self.key_node[key] = new_node
        

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)