# 460. LFU Cache
class LFUCache:
    class Node:
        def __init__(self,key,val):
            self.key = key
            self.val = val
            self.freq = 1
            self.prev = None
            self.next = None

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.Map = {}
        self.freqMap = {}
        self.minFreq = 0
        
    def update(self,node):
        oldFreq = node.freq
        oldFreqList = self.freqMap[oldFreq]
        oldFreqList.remove(node)

        if oldFreq == self.minFreq and oldFreqList.size == 0:
            self.minFreq += 1

        newFreq = oldFreq + 1
        node.freq = newFreq

        if newFreq not in self.freqMap:
            self.freqMap[newFreq] = DLL()

        newFreqList = self.freqMap[newFreq]
        newFreqList.addAtStart(node)
    
    def get(self, key: int) -> int:
        if key not in self.Map: return -1
        
        node = self.Map[key]
        self.update(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0 : return 

        if key in self.Map:
            node = self.Map[key]
            node.val = value
            self.update(node)
        else:
            if self.capacity <= len(self.Map):
                freqLL = self.freqMap[self.minFreq] 
                endNode = freqLL.removeAtEnd()
                del self.Map[endNode.key]

            newNode = self.Node(key,value)
            self.Map[key] = newNode
            self.minFreq = 1

            if 1 not in self.freqMap:
                self.freqMap[1] = DLL()

            self.freqMap[1].addAtStart(newNode)
            
class DLL:
    def __init__(self):
        self.head = LFUCache.Node(-1,-1)
        self.tail = LFUCache.Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def addAtStart(self,node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next.prev = node
        self.head.next = node
        self.size += 1

    def remove(self,node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
        return node

    def removeAtEnd(self):
        if self.size == 0: return None
        endNode = self.tail.prev
        self.remove(endNode)
        return endNode


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)