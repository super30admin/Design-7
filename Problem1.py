"""
// Time Complexity : o(1), all operations
// Space Complexity : o(1), capacity is defined
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
"""

from collections import defaultdict
class Node: #DLL node
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.freq = 1 #keep track of the freq of node
        self.prev = None
        self.next = None


class DLList: #DLL
    
    def __init__(self):
        self.head = Node(-1, -1) #dummy head and tail nodes       
        self.tail = Node(-1, -1)        
        self.head.next = self.tail      
        self.tail.prev = self.head      
        self.size = 0                   
   
        
    def insertAtHead(self, node):
        node.prev = self.head
        node.next = self.head.next

        self.head.next = node
        node.next.prev = node
        
        self.size += 1

    def remove(self, node):

        node.prev.next = node.next
        node.next.prev = node.prev
        
        self.size -= 1

    def removeLast(self):
    
        tail = self.tail.prev
        self.remove(tail)
        return tail
        

class LFUCache:

    def __init__(self, capacity: int):
        
        self.LFU_map = defaultdict()  #key to node 
        self.freq_map = defaultdict()
        self.min = 0             
        self.size = 0 #of lfu         
        self.capacity = capacity  
        
    def update_freq(self, node) -> None: #to update the freq_map for the element
     
        old = self.freq_map[node.freq]
        old.remove(node)
        
        if (old.size == 0):
            if (node.freq == self.min): #if the min freq list becomes empty, update min value
                self.min += 1
       
        node.freq += 1 #update freq of the node
        
        if (node.freq not in self.freq_map): #if freq not present, create a DLL for that frq
            newList = DLList()
            self.freq_map[node.freq] = newList
            
        newList = self.freq_map[node.freq]
        newList.insertAtHead(node) #add the node at the head of the corresponding list
            

    def get(self, key: int) -> int:
        
        if (key not in self.LFU_map): #if key not present
            return -1
        
        node = self.LFU_map[key] #get the node
        self.update_freq(node) #update the freq for the node
        return node.value

    def put(self, key: int, value: int) -> None:
        
        if (self.capacity == 0): #if capacity is 0, do nothing
            return
        
        if (key in self.LFU_map): #if key already present, update the value for the key and update its freq
            node = self.LFU_map[key]
            node.value = value
            self.update_freq(node)
         
        else:
            node = Node(key, value) #if not present
            
            if (self.size == self.capacity): #check if capacity is reached, if thats the case
                min_list = self.freq_map[self.min] #get the min freq list and remove last element from it
                removed = min_list.removeLast()
                del self.LFU_map[removed.key] #delete key from lfu
                self.size -= 1
            
            if (1 not in self.freq_map): # add the new node, if freq 1 not present in freq map, create a list for it and add the node
                newList = DLList()
                self.freq_map[1] = newList
            
            tmp = self.freq_map[1]
            tmp.insertAtHead(node)
            self.LFU_map[key] = node
            self.min = 1  
            self.size += 1                       
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)

