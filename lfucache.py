"""
Approach:
#O(1)
#O(N)

Data Structures:

Frequency Table: A dictionary to store the mapping of different frequency values with values as DLLs storing (key, value,count) pairs as nodes
>> in case of tie, should select lru value from lfu dll
main Dicitionary: Nodes in the DLL are stored as values for each key pushed into the program

Algorithm:

get(key):

If key is not present in cache, return -1
Get the node from the mainmap
Update the node frequency
Remove the node from the DLL of node's previous frequency
Add the node to the DLL with the node's updated frequency
Update min frequency value if required

put(key, value):

If key is present in mainmap
Similar logic to that of get function
Only difference being that we need to update the value here

If key not present in mainmap
If the mainmap has already reached it's capacity, delete the tail node from the DLL with least frequency
Create the new node with the (key, value) pair passed as arguments
Add the node to the frequency table with frequency key = 1
Add the node to the mainmap
Update min frequency to be 1
"""


class Node(object):
    def __init__(self,key,value):
        self.key=key
        self.value=value
        self.count=1 #default 
        self.prev=None
        self.next=None
class DLL(object):
    def __init__(self):
        self.head=Node(-1,-1)
        self.tail=Node(-1,-1)
        self.head.next=self.tail
        self.tail.prev=self.head
        self.size=0
    def addToHead(self,node):
        node.next=self.head.next
        node.prev=self.head
        self.head.next.prev=node
        self.head.next=node
        self.size+=1
    def removeNode(self,node):
        node.next.prev=node.prev
        node.prev.next=node.next
        self.size-=1
    def removelast(self):
        tailprev=self.tail.prev
        self.removeNode(tailprev)
        return tailprev
    #override length of DLL method
    def __len__(self):
        return self.size
    
class LFUCache(object):
    def __init__(self, capacity):
        """
        :type capacity: int
        """
        self.capacity=capacity
        #min frequency in freqmap>default taken as 1
        self.min=1
        self.map={}
        self.freqmap={}
        

    def get(self, key):
        """
        :type key: int
        :rtype: int
        """
        if key in self.map:
            node=self.map[key]
            #update freq in freqmap
            self.update(node)
            return node.value
        return -1

    def put(self, key, value):
        """
        :type key: int
        :type value: int
        :rtype: None
        """
        if key in self.map:
            node=self.map[key]
            node.value=value
            self.update(node)
        else:
            #if capacity provided is zero only> return
            if self.capacity==0:
                return
            #reached max capacity
            if self.capacity==len(self.map):
                #get dll of lfu
                removelist=self.freqmap[self.min]
                #remove lru of this list
                removedNode=removelist.removelast()
                #delete entry of node from main map as well
                del self.map[removedNode.key]
            #input new node with freq=1> update minfreq as well of freqmap
            newNode=Node(key,value)
            self.min=1
            self.map[key]=newNode
            #if entry with corresponding freq exists in freqmap
            #then get that dll else create new dll
            newList=self.freqmap.get(self.min,DLL())
            newList.addToHead(newNode)
            self.freqmap[self.min]=newList
    #update freq in freqmap
    def update(self,node):
        count=node.count
        node.count+=1
        oldlist=self.freqmap[count]
        oldlist.removeNode(node)
        #if that min value changes bcoz of present node count
        if self.min==count and len(oldlist)==0:
            self.min+=1
        newList=self.freqmap.get(node.count,DLL())
        newList.addToHead(node)
        self.freqmap[node.count]=newList
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)