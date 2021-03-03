#The node class defines the characteristics of the node to be created
class Node:									#O(1)
    def __init__(self,key:int,value:int):
        self.key=key
        self.val=value
        self.count=1
        self.prev=None
        self.next=None

#the Doubly linked list is initiated and the functions to add node at head
#remove a particular node or last node is defined in the following class       
class DLList:								#O(n)
    def __init__(self):
        self.head=Node(-1,-1)
        self.tail=Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev=self.head
        self.size=0
        
    def addToHead(self,node:Node)->None:
        node.next=self.head.next
        node.prev=self.head
        self.head.next=node
        node.next.prev=node
        self.size+=1
        
    def removeNode(self,node:Node)->None:
        node.next.prev=node.prev
        node.prev.next=node.next
        self.size-=1
        
    def removeLast(self)->Node:
        tailPrev=self.tail.prev
        self.removeNode(tailPrev)
        return tailPrev
        
class LFUCache:
	#the cache is initiated with two hash dictionaries a minimum variable and capacity of the cache
    def __init__(self, capacity: int):				#O(n)
        self.dict={}
        self.dll={}
        self.m=-inf
        self.capacity=capacity

    #the get function finds the key in the hash and returns its value after updating the value
    def get(self, key: int) -> int:					#O(1)
        if key not in self.dict:
            return -1
        self.update(self.dict[key])
        return self.dict[key].val
    #the put function finds the key in the hash and  updates its value after which the node is updated with the value
    #if the key does not exists and the capacity is reached  last node with frequency min is removed and the new node is added.
    #before adding the new node it is checked if  node with same key value is used and frequency is updated before adding.
    def put(self, key: int, value: int) -> None:	#O(1)
        if self.capacity==0:
            return
        if key in self.dict:
            node=self.dict[key]
            node.val=value
            self.update(node)
        else:
            if self.capacity==len(self.dict):
                minList=self.dll[self.m]
                toRemove=minList.removeLast()
                del self.dict[toRemove.key]
            newNode=Node(key,value)
            self.m=1
            if 1 not in self.dll:
                newList=DLList()
                self.dll[1]=newList
            newList = self.dll[1]
            newList.addToHead(newNode)
            self.dict[key] = newNode
            
    def update(self,node:Node)->None:
        oldList=self.dll[node.count]
        oldList.removeNode(node)
        if node.count==self.m and oldList.size==0:
            self.m+=1
        node.count+=1
        if node.count not in self.dll:
            newList=DLList()
            self.dll[node.count]=newList
        newList=self.dll[node.count]
        newList.addToHead(node)
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)