# Time Complexity : O(1) for get and put operations
# Space Complexity : O(n)
# The code ran on LeetCode
# Maintain a hash map to store key -> node. Another hashmap to store freq -> Doubly linked list (store nodes in their recently used order). Remove the least frequently used node when capacity is reached, also remove the key from the hash map. If capacity is not reached, add node wrt frequency = 1.

class Node:    
    def __init__(self, key, val):
        self.key=key
        self.val=val
        self.prev=None
        self.next=None
        self.cnt=1

class Doublell:
    def __init__(self):
        self.size=0
        self.head=Node(0,0)
        self.tail=Node(0,0)
        self.head.next=self.tail
        self.tail.prev=self.head 

    def addNode(self, newnode):
        temp=self.head.next
        newnode.prev=self.head
        newnode.next=temp
        temp.prev=newnode
        self.head.next=newnode
        self.size+=1
        
    def deleteNode(self,delnode):
        prevv=delnode.prev
        nextt=delnode.next
        prevv.next=nextt
        nextt.prev=prevv
        self.size-=1
        
class LFUCache:
    def __init__(self, capacity: int):
        self.dic={}
        self.freqList={}
        self.capacity=capacity
        self.minFreq=1
        self.curSize=0
        
    def updateFreqList(self, node):
        del self.dic[node.key]
        self.freqList[node.cnt].deleteNode(node)
        if node.cnt==self.minFreq and self.freqList[node.cnt].size==0:
            self.minFreq+=1
        nextHigherFreqList=Doublell()
        if node.cnt + 1 in self.freqList:
            nextHigherFreqList=self.freqList[node.cnt+1]
        node.cnt+=1
        nextHigherFreqList.addNode(node)
        self.freqList[node.cnt]=nextHigherFreqList
        self.dic[node.key]=node
        
    def get(self, key: int) -> int:
        if key in self.dic:
            node=self.dic[key]
            value=node.val
            self.updateFreqList(node)
            return value
        return -1

    def put(self, key: int, value: int) -> None:
        if key in self.dic:
            node=self.dic[key]
            node.val=value
            self.updateFreqList(node)
        else:
            if self.curSize==self.capacity:
                dll=self.freqList[self.minFreq]
                del self.dic[dll.tail.prev.key]
                self.freqList[self.minFreq].deleteNode(dll.tail.prev)
                self.curSize-=1
            self.curSize+=1
            self.minFreq=1
            listFreq=Doublell()
            if self.minFreq in self.freqList:
                listFreq=self.freqList[self.minFreq]
            node=Node(key,value)
            listFreq.addNode(node)
            self.dic[key]=node
            self.freqList[self.minFreq]=listFreq
