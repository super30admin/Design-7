#All TC on leetcode passed

class Node:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.count = 1
        self.next = None
        self.prev = None

class DLList:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
    
    #Time complexity - O(1)
    #Space complexity - O(1)
    def addToHead(self,node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size+=1

    #Time complexity - O(1)
    #Space complexity - O(1)
    def removeLast(self):
        node = self.tail.prev
        self.removeNode(node)
        return node

    #Time complexity - O(1)
    #Space complexity - O(1)
    def removeNode(self,node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size-=1


class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.keyMap = collections.defaultdict(Node)
        self.frqMap = collections.defaultdict(DLList)
        self.min = 0
        
    #Here we return val of existing key in node and update the frq of node to +1 and also frqmap is updated with new entry and old entry is deleted
    #Time complexity - O(1)
    #Space complexity - O(1)
    def get(self, key: int) -> int:
        if key not in self.keyMap:
            return -1 
        node = self.keyMap[key]
        self.updateList(node)
        return node.val
    
    #Time complexity - O(1)
    #Space complexity - O(1)
    def updateList(self, node):
        oldCnt = node.count
        oldList = self.frqMap[oldCnt]
        node.count = oldCnt+1
        newCnt = node.count
        oldList.removeNode(node)
        if self.min==oldCnt and oldList.size==0:
            self.min+=1
        newList = self.frqMap[newCnt] 
        newList.addToHead(node)
        self.frqMap[newCnt] = newList


    #Here we update val of existing key in node, or add new node to map. To add new node if capacity is full then we delete least frq node and least recent used node.
    #Time complexity - O(1)
    #Space complexity - size of keyMap and freqMap
    def put(self, key: int, value: int) -> None:
        if key in self.keyMap:
            node = self.keyMap[key]
            node.val = value
            self.updateList(node)
        else:
            if self.capacity == len(self.keyMap):
                oldList = self.frqMap[self.min]
                tailNode = oldList.removeLast()
                self.keyMap.pop(tailNode.key)
            
            node = Node(key, value)
            self.min = 1
            self.keyMap[key] = node
            dlList = self.frqMap[1]
            dlList.addToHead(node)
            self.frqMap[1] = dlList





# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)