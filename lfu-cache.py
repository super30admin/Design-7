# Time Complexity: O(1)
# Space Complexity: O(n)
class Node:
    def __init__(self,key:int,val:int):
        self.key=key
        self.val=val
        self.count=1
        self.next,self.prev=None,None
class DLL:
    def __init__(self):
        self.head=Node(-1,-1)
        self.tail=Node(-1,-1)
        self.head.next=self.tail
        self.tail.prev=self.head
        self.size=0
    def remove(self,node):
        node.prev.next=node.next
        node.next.prev=node.prev
        self.size-=1
    def addToFront(self,node):
        node.next=self.head.next
        node.prev=self.head
        self.head.next=node
        node.next.prev=node
        self.size+=1
    def removeLast(self):
        node=self.tail.prev
        self.remove(node)
        return node
class LFUCache:                   
    def __init__(self, capacity: int):
        self.capacity=capacity
        self.hmap={}
        self.freqMap={}
        self.mini=0
    def update(self,node):
        oldlist=self.freqMap[node.count]
        oldlist.remove(node)
        if node.count==self.mini and self.freqMap[node.count].size==0: self.mini+=1
        li=self.freqMap.get(node.count+1,DLL())
        li.addToFront(node)
        node.count+=1
        self.freqMap[node.count]=li
    def get(self, key: int) -> int:
        if key not in self.hmap: return -1
        node=self.hmap[key]
        self.update(node)
        return node.val
        
    def put(self, key: int, value: int) -> None:
        if key in self.hmap:
            self.hmap[key].val=value
            self.update(self.hmap[key])
        else:
            if self.capacity==0: return
            if len(self.hmap)==self.capacity:
                node=self.freqMap[self.mini].removeLast()
                del self.hmap[node.key]
            self.mini=1
            newNode=Node(key,value)
            self.hmap[key]=newNode
            li=self.freqMap.get(1,DLL())
            li.addToFront(newNode)
            self.freqMap[self.mini]=li
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)