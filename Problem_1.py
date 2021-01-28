# LFU Cache 

# Time Complexity : O(1)
# Space Complexity : O(N) where N=number of nodes which is capacity
# Did this code successfully run on Leetcode : Yes, with Runtime: 312 ms and Memory Usage: 23.9 MB
# Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
# Approach
"""
Using 2 hasmaps, 1st hashmap stores key value pairs key as key and value as node having key,value and frequency.
This node is stored as doubly linked list and present in 2nd hashmap which stores key as frequency. 
Calling get() function will check in 1st hashmap and return the value. Its frequency will also be checked
and in 2nd hashmap if not present will be added to the head and if present will be incremented.
Calling put() function will update both hashmaps.

"""
class Node:                             # Designing Class Node
    def __init__(self,key,value):
        self.key=key
        self.value=value
        self.count=1            #count is frequency 
        self.prev=None
        self.next=None
        
class DLL(object):          # functions required 
    def __init__(self):         # designing doubly linked list
        self.head=Node(-1,-1)   # dummy node
        self.tail=Node(-1,-1)   # dummy node
        self.head.next=self.tail
        self.tail.prev=self.head
        self.size=0
        
    def addToHead(self,node):
        node.next=self.head.next
        node.prev=self.head
        self.head.next.prev=node # Both head and tail have doubly linked list
        self.head.next=node
        self.size=self.size+1
        
    def removeNode(self,node): 
        node.next.prev=node.prev
        node.prev.next=node.next
        self.size=self.size-1
        
    def removelast(self): # this function will remove from hashmap 1 so its key is required
        tailprev=self.tail.prev
        self.removeNode(tailprev)
        return tailprev
    
    #override length of DLL method
    def __len__(self):
        return self.size
    
class LFUCache(object):
    def __init__(self, capacity):
        self.capacity=capacity
        self.minimum=1
        self.map={}  # dictionary/hashmap 1 storing key,value pairs
        self.freqmap={} # dictionary/hashmap 2
        

    def get(self, key):
        if key in self.map:
            node=self.map[key]
            self.update(node) #update freq in freqmap ie hashmap 2
            return node.value
        return -1

    def put(self, key, value):
        if key in self.map:
            node=self.map[key]
            node.value=value
            self.update(node)
        else:
            if self.capacity==0: #if capacity provided is zero only> return
                return
            if self.capacity==len(self.map): #reached max capacity
                removelist=self.freqmap[self.minimum]
                removedNode=removelist.removelast()
                del self.map[removedNode.key]
            newNode=Node(key,value) #input new node with freq=1> update minfreq as well of freqmap
            self.minimum=1
            self.map[key]=newNode
            newList=self.freqmap.get(self.minimum,DLL())
            newList.addToHead(newNode)
            self.freqmap[self.minimum]=newList
            
    def update(self,node):
        count=node.count
        node.count+=1
        oldlist=self.freqmap[count]
        oldlist.removeNode(node)
        if self.minimum==count and len(oldlist)==0:
            self.minimum+=1
        newList=self.freqmap.get(node.count,DLL())
        newList.addToHead(node)
        self.freqmap[node.count]=newList