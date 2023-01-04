# Time Complexity : O(1) 
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : Nothing specific
class Node:
    def __init__(self,key: int,val: int):
        self.key=key
        self.val=val
        self.freq=1
        self.nex=None
        self.prev=None
class DLList:
    def __init__(self):
        self.head=None
        self.tail=None
        self.size=0
        
    #This will add the node at the head of the doublylinkedlist
    def addathead(self,node: Node):
        if((self.head==None and self.tail==None) or self.size==0):
            #This is empty doubly linked list
            self.head,self.tail=node,node
        else:
            temp=self.head
            node.nex=temp
            temp.prev=node
            self.head=node
        self.size+=1
        
    #This will remove the node from a doublylinkedlist
    def removeNode(self,node: Node):
        if(self.size==1):
            #If only one node is present 
            self.head,self.tail=None,None
        elif(node==self.head):
            #If we want to remove the head node
            temp=node.nex
            node.nex=None
            temp.prev=None
            self.head=temp
        elif(node==self.tail):
            #If we want to remove the tail node
            temp=node.prev
            node.prev=None
            temp.nex=None
            self.tail=temp
        else:
            #If we want to remove any node between the tail and head node
            temp1,temp2=node.prev,node.nex
            temp1.nex,temp2.prev=temp2,temp1
        self.size-=1
        
    #This is special method when we reach capacity and have tie
    def removeLastNode(self):
        lastnode=self.tail
        self.removeNode(lastnode)
        return lastnode
            
        
            
class LFUCache:

    def __init__(self, capacity: int):
        self.hashmap1={}#This will hold key:Node
        self.hashmap2={}#For frequency, This will hold freq:DLL
        self.capacity=capacity
    def updatehashmap(self,node):
        oldlist=self.hashmap2[node.freq]
        oldlist.removeNode(node)
        #We need to check and update the min
        if((self.min == node.freq) and (oldlist.size == 0)):
            self.min+=1
        node.freq+=1#Update the frequency
        
        if(node.freq in self.hashmap2):
            newlist=self.hashmap2[node.freq]    
        else:
            newlist= DLList()
            self.hashmap2[node.freq]=newlist
        
        newlist.addathead(node)
    
    def get(self, key: int) -> int:
        if(key in self.hashmap1):
            node=self.hashmap1.get(key)
            self.updatehashmap(node)#This will update the hashmap
            return node.val
        else:
            return -1    

    def put(self, key: int, value: int) -> None:
        #This is edge Case
        if(self.capacity==0):
            return
        if(key in self.hashmap1):
            #We need to updates it value
            node=self.hashmap1.get(key)
            node.val=value
            self.updatehashmap(node)
        else:
            #We need to create a new node and modify the hashmap as needed
            if(self.capacity==len(self.hashmap1)):
                #we need to remove one of the existing node
                oldlist=self.hashmap2.get(self.min)
                tailprev=oldlist.removeLastNode()
                self.hashmap1.pop(tailprev.key)
                del tailprev
            newnode=Node(key,value)
            self.min=1
            self.hashmap1[key]=newnode
            if(self.min in self.hashmap2):
                dllist=self.hashmap2[self.min]
            else:
                dllist=DLList()
                self.hashmap2[self.min]=dllist
            dllist.addathead(newnode)
                
            

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)