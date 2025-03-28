# Problem 1 : LFU Cache
# Time Complexity : 
'''
get() - O(1)
put() - O(1)
'''
# Space Complexity : O(n) where n is the total number of node ie capacity
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this :
'''
None
'''

# Your code here along with comments explaining your approach
from collections import defaultdict

# class Node of double linked list for storing key, value, counter, pointers for next node and previous node
class Node:
    def __init__(self, key, val):
        # initialize key and value to the given values and counter to 1. Pointer should be None
        self.key = key
        self.val = val
        self.counter = 1
        self.prev = None
        self.next = None

# class for list for double linked list which stores head and tail of the list
class DLList:
    def __init__(self):
        # initialize the head and tail as dummy node
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        # set the next of head to tail and previous of tail to head
        self.head.next = self.tail
        self.tail.prev = self.head
        # initially set the size to 0
        self.size = 0

    # function for adding the node to the head of the list
    def addToHead(self, node):
        # set the previous of node to head
        node.prev = self.head
        # set the next of node to next node of head
        node.next = self.head.next
        # set the previous of next node of head to node
        self.head.next.prev = node
        # set the next of head to node
        self.head.next = node
        # increment the size of the list
        self.size += 1
    
    # function for removing the specific node from the list
    def removeNode(self, node):
        # set the previous of next node of node to next of node
        node.prev.next = node.next
        # set the next of previous node of node to previous of node
        node.next.prev = node.prev
        # decremen the size of the list
        self.size -=1
    
    # function to get the previous node of tail
    def removeTailPrev(self):
        # set the previous node of tail in tailPrev
        tailPrev = self.tail.prev
        # remove the previous node of the tail of the list
        self.removeNode(tailPrev)
        # return the previous node of the tail
        return tailPrev

class LFUCache:
    def __init__(self, capacity: int):
        # set the global capacity to capacity
        self.capacity = capacity
        # define the originalMap where key is key and value is the node
        self.originalMap = {}
        # define the frequencyMap where the key is the frequency of the key and value is double linked list of node 
        self.frequencyMap = defaultdict(DLList)
        # set the min value to 0
        self.min = 0
    
    # update the function for update the new position of the node in double linked list
    def update(self, node: Node) -> None:
        # get the counter value of node
        oldCounter = node.counter
        # get the list of double linked list for the old counter value
        oldList = self.frequencyMap[oldCounter]
        # remove the node from the list
        oldList.removeNode(node)

        # check if the oldCounter is min and the size is 0 and if it then iincrement the min value
        if oldCounter == self.min and oldList.size == 0:
            self.min += 1
        
        # increment the counter value
        node.counter += 1
        newCount = node.counter
        # get the list of frequency of newCount
        newList = self.frequencyMap[newCount]
        # add the node to the head to the new list
        newList.addToHead(node)
        

    def get(self, key: int) -> int:
        # check if the key is not in the originalMap
        if key not in self.originalMap:
            # if the value is not present then return -1
            return -1
        # get the node from the originalMap where key is the key
        node = self.originalMap[key]
        # call the update function for the node
        self.update(node)
        # return the value of the node
        return node.val
        

    def put(self, key: int, value: int) -> None:
        # check if the capacity is 0 and if it is then return
        if self.capacity == 0:
            return
        # check if the key is an existing on by checking in originalMap
        if key in self.originalMap:
            # get the node from originalMap from the key
            node = self.originalMap[key]
            # update the val of node with the value
            node.val = value
            # call the update function for the node
            self.update(node)
        # else the node is new one
        else:
            # check if the capacity is full
            if len(self.originalMap) == self.capacity:
                # if capacity is full then get the minimum list(list of minimum frequency) from the frequencyMap by using the min as the key
                minList = self.frequencyMap[self.min]
                # get the previous node of tail of the minimum list(which is a least recently used)
                toRemove = minList.removeTailPrev()
                # delete the node from the originalMap
                del self.originalMap[toRemove.key]
            
            # if there is a capacity in cache
            # create a new node for the given key and value
            newNode = Node(key, value)
            # set the minimum to 1
            self.min = 1
            # get the list from frequencyMap for the frequency 1
            newList = self.frequencyMap[1]
            # add the new node to head of the list
            newList.addToHead(newNode)
            # add the new node to originalMap
            self.originalMap[key] = newNode
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)