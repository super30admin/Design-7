class ListNode:
    def __init__(self, key, value):
        self.val = value
        self.key = key
        self.freq = 1
        self.next = None
        self.prev = None
class DLL:
    def __init__(self):
        self.size = 0
        self.head = ListNode(None, None)
        self.tail = ListNode(None, None)
        
        self.head.next = self.tail
        self.tail.prev = self.head
        
    def addToHead(self, node):
        headNext = self.head.next
        node.next = headNext
        self.head.next = node
        node.prev = self.head
        headNext.prev = node
        self.size+=1
        
    def removeLastNode(self):
        tail = self.tail.prev 
        self.removeNode(tail)
        return tail
        
    def removeNode(self, node):
        before = node.prev
        after = node.next
        before.next = after
        after.prev = before
        self.size-=1

class LFUCache:
    def __init__(self, capacity: int):
        self.cache = defaultdict(ListNode)
        self.capacity = capacity
        self.size = 0
        self.frequency = defaultdict(DLL)
        self.currentMin = 1
        
    def get(self, key: int) -> int:
        #if present:
        if key in self.cache:
            nodeToReturn = self.cache[key]
            self.updateNodes(nodeToReturn)
            return nodeToReturn.val
        else:
            return -1

    def updateNodes(self, node):
        #here we move the nodes from one frequency the other
        currentFreq = node.freq
        self.frequency[currentFreq].removeNode(node)
        if self.frequency[currentFreq].size==0 and currentFreq == self.currentMin:
            self.currentMin+=1
        node.freq+=1
        self.frequency[node.freq].addToHead(node)
        
    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return 
        
        if key in self.cache:
            #here we have to update 
            nodeToUpdate = self.cache[key]
            nodeToUpdate.val = value
            self.updateNodes(nodeToUpdate)
            return nodeToUpdate.val
        else:
            #here we have to check if there is space to add an extra node
            if self.size == self.capacity:
                #there is no space, we gotta pop
                nodeToDelete = self.frequency[self.currentMin].removeLastNode()
                del self.cache[nodeToDelete.key]
                self.size-=1
            #there is space we can simply add to self.cache and update the node in the frequency table OR we just made space
            newNode = ListNode(key, value)
            self.cache[key] = newNode
            self.frequency[1].addToHead(newNode)
            self.currentMin = 1
            self.size+=1
            
Time: O(1)
Space: O(1)


