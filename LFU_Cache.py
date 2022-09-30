# Time complexity : O(1) for all operations
# Space complexitu : O(capacity)
# Leetcode : Solved and submitted

# Define Node class for attributes key, val and counter along with next and prev pointers
class Node:
    def __init__(self, key, value):
        self.key = key
        self.val = value
        self.count = 1
        self.next = None
        self.prev = None

# create a DList class to define the default doubly linked list and its functions
class DList:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
    
    # function defined to insert a node at the head of the linked list
    def insertAtHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size += 1
    
    # function defined to remove any node
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1

    # function defined to remove the last node of the list
    def removeLast(self):
        toRemove = self.tail.prev
        self.removeNode(toRemove)
        return toRemove
    
class LFUCache:
    # default values of the Hashmap and other variables
    def __init__(self, capacity: int):
        self.capcity = capacity
        # this is used to keep track of the key and the corresponding Node
        self.hashmap = {}
        # this will keep a freq count and a DList for insertion and removal
        self.freqmap = {}
        self.min_freq = float('inf')
        self.size = 0
        
    # function to remove node from old freq and put into new one
    def update(self, node):
        # fetch the old list from the old count
        old_list = self.freqmap[node.count]
        # remove the node from the old list
        old_list.removeNode(node)
        
        # if the size of old list became 0 and node count is the min freq we have, then we updated
        # the min_freq as well
        if old_list.size == 0:
            if node.count == self.min_freq:
                self.min_freq += 1
        
        # increment the count
        node.count += 1
        
        # if the new count as key in not in the freq map, then create a new Dlist and assign to that key
        if node.count not in self.freqmap:
            newList = DList()
            self.freqmap[node.count] = newList
        
        # insert the node into the new Dlist and update the freqMap
        newList = self.freqmap[node.count]
        newList.insertAtHead(node)
        
        
    def get(self, key: int) -> int:
        # if the key is not present in the hashmap then return -1
        if key not in self.hashmap:
            return -1
        # fetch the node from the hashmap with key
        node = self.hashmap[key]
        # update the count of the node, which would change it's frequency count by 1
        self.update(node)
        
        # return the node value
        return node.val

    def put(self, key: int, value: int) -> None:
        # base case, if capacity is 0, then just return
        if self.capcity == 0:
            return
        
        # if key is present in the hashmap
        if key in self.hashmap:
            # fetch the node and update it's value and also the freq
            node = self.hashmap[key]
            node.val = value
            self.update(node)
        else:
            #  if node is not present in hashmap, then create a node
            node = Node(key, value)
            # check if the capacity is full
            if self.size == self.capcity:
                # fetch the list with min frequeny, remove the last node and decement the size of DList
                minList = self.freqmap[self.min_freq]
                rem = minList.removeLast()
                del self.hashmap[rem.key]
                self.size -= 1
            
            # check if the frequency 1 is not present, as the new node will get the freq as 1
            # if present then just append to the existing list
            if 1 not in self.freqmap:
                # create a new Dlist and assign to 1
                newList = DList()
                self.freqmap[1] = newList
                
            # fetch the list and update the min frequency
            newList = self.freqmap[1]
            self.min_freq = 1
            # add the new node to the head of the list and increment the size
            newList.insertAtHead(node)
            self.size += 1
            
            # place the list and node to the respective Hashmaps
            self.freqmap[1] = newList
            self.hashmap[key] = node


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
