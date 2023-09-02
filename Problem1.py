class Node:
    def __init__(self,key,value):
        self.key=key
        self.val=value
        self.next=None
        self.prev=None
        self.cnt=1

class DLL:
    def __init__(self):
        self.head=Node(-1,-1)
        self.tail=Node(-1,-1)
        self.head.next=self.tail
        self.tail.prev=self.head
        self.size=0

    def addToHead(self,currNode):
        currNode.next=self.head.next
        currNode.prev=self.head
        self.head.next=currNode
        currNode.next.prev=currNode
        self.size+=1

    def removeNode(self,currNode):
        currNode.next.prev=currNode.prev
        currNode.prev.next=currNode.next
        self.size-=1

    def removeLast(self):
        toRemove=self.tail.prev
        self.removeNode(toRemove)
        return toRemove
            
class LFUCache:
    def __init__(self, capacity: int):
        self.map={}
        self.fmap={}
        self.capacity=capacity
        self.minFreq=0
   
    def update(self, currNode):
        oldCnt=currNode.cnt
        oldFlist=self.fmap[oldCnt]
        oldFlist.removeNode(currNode)
        if oldCnt==self.minFreq and oldFlist.size==0:
            self.minFreq+=1
        
        currNode.cnt=oldCnt+1
        newFlist=self.fmap.get(currNode.cnt, DLL())
        newFlist.addToHead(currNode)
        self.fmap[currNode.cnt]=newFlist


    def get(self, key: int) -> int:
        if key not in self.map.keys():
            return -1
        currNode=self.map[key]
        self.update(currNode)
        return currNode.val
        

    def put(self, key: int, value: int) -> None:
        if key in self.map.keys():
            currNode=self.map[key]
            currNode.val=value
            self.update(currNode)
        else:
            if self.capacity==len(self.map):
                currDLL=self.fmap[self.minFreq]
                toremove=currDLL.removeLast()
                del self.map[toremove.key]
            newNode=Node(key,value)
            self.minFreq=1
            if self.minFreq not in self.fmap:
                self.fmap[self.minFreq]=DLL()
            self.fmap[self.minFreq].addToHead(newNode)
            self.map[key]=newNode



# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)