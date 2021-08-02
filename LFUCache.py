class Node:
    def __init__(self, key, val):
        self.key=key
        self.val=val
        self.cnt=1
              
class DLL:
    def __init__(self):
        self.head=Node(-1,-1)
        self.tail=Node(-1, -1)
        self.head.next=self.tail
        self.tail.prev=self.head
        self.size=0
        
    def addToHead(self, node):
        node.next=self.head.next
        node.next.prev=node
        self.head.next=node
        node.prev=self.head
        self.size+=1
    
    def removeNode(self, node):
        node.prev.next=node.next
        node.next.prev=node.prev
        self.size-=1
        
    def removeTail(self):
        nodetoRemove=self.tail.prev
        # print(nodetoRemove.key, "removeTail")
        self.removeNode(nodetoRemove)
        return nodetoRemove

class LFUCache:
    """Using 2 hashmaps - one for key to (key, value, count) relation and other for cnt to the (key, value) pairs that have this particular count---add most recently used node to head
Time complexity-O(1) as we will be able to put and get in O(1)
Space complexity-O(n)"""
    def __init__(self, capacity: int):
        self.hashmapLL={}
        self.hashmap={}
        self.capacity=capacity
        self.min=0
        
    def get(self, key: int) -> int:
        if key not in self.hashmap:
            return -1
        Node=self.hashmap[key]
        self.updateNode(Node)            
        return Node.val
    
    def updateNode(self, node):
        oldLL=self.hashmapLL[node.cnt]
        oldLL.removeNode(node)
        if node.cnt==self.min and self.hashmapLL[node.cnt].size==0:
            self.min+=1
        node.cnt+=1
        if node.cnt not in self.hashmapLL:
            self.hashmapLL[node.cnt]=DLL()
        newLL=self.hashmapLL[node.cnt]
        newLL.addToHead(node)
        self.hashmap[node.key]=node
            
    def put(self, key: int, value: int) -> None:
        if self.capacity==0:
            return 
        if key in self.hashmap:
            node=self.hashmap[key]
            node.val=value
            self.updateNode(node)
            
        else:
            if self.capacity==len(self.hashmap):
                minll=self.hashmapLL[self.min]
                noderemoved=minll.removeTail()
                self.hashmap.pop(noderemoved.key)
            newNode=Node(key, value)
            self.min=1
            if self.min not in self.hashmapLL or self.hashmapLL[self.min]==None:
                minList=DLL()
            else:
                minList=self.hashmapLL[self.min]
            minList.addToHead(newNode)
            self.hashmapLL[self.min]=minList
            self.hashmap[key]=newNode
        
            
        
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)