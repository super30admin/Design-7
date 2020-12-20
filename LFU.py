"""
Time Complexity O(1)
Space Complexity 0(N) for values in Hashmap

Maintain 2 hasmap for nodes and to maintain frequency . freqMap has key as Frequency value and respective Doubly Link list as values.
"""

class Node:
    def __init__(self,key,val):
        self.key=key
        self.val=val
        self.cnts=1
        self.prev = None
        self.next = None

class DLL:
    
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next=self.tail
        self.tail.prev=self.head
        self.size=0
    
    def addTohead(self,node):
        node.next=self.head.next
        node.prev=self.head
        self.head.next=node
        node.next.prev=node
        self.size+=1
    
    def removeNode(self,node):
        node.prev.next=node.next
        node.next.prev=node.prev
        self.size-=1
    
    def removeLast(self):
        tailPrev=self.tail.prev
        self.removeNode(tailPrev)
        return tailPrev
        
        
class LFUCache:

    def __init__(self,capacity: int):
        self.map={}
        self.freqMap={}
        self.capacity=capacity
        self.min = 0
        
    def update(self,node):
        cnt=node.cnts
        oldList=self.freqMap[cnt]
        oldList.removeNode(node)
        if(cnt==self.min and oldList.size == 0):
            self.min += 1
        node.cnts+=1
        if node.cnts in self.freqMap:
            newList=self.freqMap[node.cnts]
        else:
            newList = DLL()
        self.freqMap[node.cnts] = newList
        newList.addTohead(node)

    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            self.update(node)
            return node.val
        return -1

    def put(self, key: int, value: int) -> None:
        if key in self.map:
            node = self.map[key]
            node.val=value
            self.update(node)
        else:
            if(self.capacity==0):
                return
            if(self.capacity==len(self.map)):
                newlist = self.freqMap[self.min] #DLL object
                removed = newlist.removeLast()
                del self.map[removed.key]
            if (1 not in self.freqMap):
                newList = DLL()
                self.freqMap[1] = newList
            node = Node(key, value)
            self.min = 1
            newList = self.freqMap[1]
            newList.addTohead(node)
            self.map[key] = node
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)