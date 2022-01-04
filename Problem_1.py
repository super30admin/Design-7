class ListNode:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.prev = None
        self.next = None
        self.cnt = 1
        
class DoubleLinkedList:
    def __init__(self):
        self.head = ListNode(-1, -1)
        self.tail = ListNode(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
        
    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size += 1
        
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1
        
    def removeLast(self):
        tailPrev = self.tail.prev
        self.removeNode(tailPrev)
        return tailPrev
        
class LFUCache:

    def __init__(self, capacity: int):
        self.hashmap = {}
        self.freqMap = {}
        self.capacity = capacity
        self.min = 0
        
    def get(self, key: int) -> int:
        if key not in self.hashmap:
            return -1
        node = self.hashmap.get(key)
        self.updateNode(node)
        return node.val
        

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        if key in self.hashmap:
            node = self.hashmap.get(key)
            node.val = value
            self.updateNode(node)
        else:
            if self.capacity == len(self.hashmap):
                minfreqList = self.freqMap.get(self.min)
                toRemove = minfreqList.removeLast()
                self.hashmap.pop(toRemove.key)
            newNode = ListNode(key, value)
            self.min = 1
            minList = self.freqMap.get(self.min, DoubleLinkedList())
            minList.addToHead(newNode)
            self.freqMap[self.min] = minList
            self.hashmap[key] = newNode
        
        
    def updateNode(self, node):
        count = node.cnt
        oldList = self.freqMap.get(count)
        oldList.removeNode(node)
        if node.cnt == self.min and oldList.size == 0:
            self.min += 1
        node.cnt += 1
        newList = self.freqMap.get(node.cnt, DoubleLinkedList())
        newList.addToHead(node)
        self.freqMap[node.cnt] = newList
        self.hashmap[node.key] = node
        
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)