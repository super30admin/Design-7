#As taugnt in class usiing diictionary and doubly linked list to solve this problem
#Time complexity: O(n)
#Space complexity: O(2n)
class Node:
    def __init__(self,key,val):
        self.key = key
        self.val = val
        self.cnt = 1
        self.next = None

class Dlist:
    size = 0
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        head.next = tail
        tail.prev = head
        
        
    def addToHead(self,node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        size = size + 1
        
    def removeNode(self,node):
        node.prev.next = node.next
        node.next.prev = node.prev
        size = size - 1
    
    def removeLast(self,node):
        tailPrev = self.tail.prev
        self.removeNode(tailprev)
        return tailprev
        
    
class LFUCache:

    def __init__(self, capacity: int):
        self.map = dict()
        self.freqMap = dict()
        self.capacity = capacity
        self.mini = 0
        

    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            self.update(node)
            return node.val
        return -1
            
        

    def put(self, key: int, value: int) -> None:
        if key in self.map:
            node = self.map[key]
            node.val = value
            self.update(node)
        else:
            if len(self.map) == self.capacity:
                minli = self.freqMap[min]
                toremove = minli.removeLast()
                del self.map[key]
            newNode = Node(key,value)
            min = 1
            minli = self.freqMap[min]
            self.map[key] = newNode
            minli.addToHead(newNode)
            self.freqMap[1] = minli
            self.map[key] =newNode
    
    def update(self, node):
        cnt = node.cnt
        oldList = self.freqMap[cnt]
        if cnt == min and len(oldList)==0:
            min = min + 1
        oldlist.removeNode(node)
        node.cnt = node.cnt + 1
        newList = self.freqMap[node.cnt]
        neweList.addToHead(node)
        self.freqMap[node.cnt] = newList
        
