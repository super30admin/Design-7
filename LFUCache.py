class LFUCache:
    class Node:
        def __init__(self, key, val):
            self.key = key
            self.val = val
            self.freq = 1
            self.next = None
            self.prev = None
    
    class DLList:
        def __init__(self):
            self.head = LFUCache.Node(-1,-1)
            self.tail = LFUCache.Node(-1,-1)
            self.head.next = self.tail
            self.tail.prev = self.head
            self.size = 0
        
        def insertNodeToHead(self, node):
            node.prev = self.head
            node.next = self.head.next
            self.head.next.prev = node
            self.head.next = node
            self.size+=1

        def removeNode(self,node):
            node.prev.next = node.next
            node.next.prev = node.prev
            node.next = None
            node.prev = None
            self.size-=1

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.map = {}
        self.freqmap = {}
        self.minfreq = 0
    
    def update(self, node):
        oldfreq = node.freq
        oldfreqList = self.freqmap[oldfreq]
        oldfreqList.removeNode(node)

        if oldfreq == self.minfreq and oldfreqList.size == 0:
            self.minfreq+=1

        newfreq = oldfreq+1
        node.freq = newfreq
        newfreqList = self.freqmap.get(newfreq, LFUCache.DLList())
        newfreqList.insertNodeToHead(node)
        self.freqmap[newfreq] = newfreqList

    def get(self, key: int) -> int:
        if key not in self.map: return -1
        node = self.map[key]
        self.update(node)
        return node.val
        

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        
        if key in self.map:
            node = self.map[key]
            node.val = value
            self.update(node)
        else:
            if self.capacity == len(self.map):
                minfreqlist = self.freqmap[self.minfreq]
                lastnode = minfreqlist.tail.prev
                minfreqlist.removeNode(lastnode)
                del self.map[lastnode.key]
            self.minfreq = 1
            newNode = LFUCache.Node(key,value)
            self.map[key] = newNode
            minfreqlist = self.freqmap.get(self.minfreq, LFUCache.DLList())
            minfreqlist.insertNodeToHead(newNode)
            self.freqmap[self.minfreq] = minfreqlist

        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)