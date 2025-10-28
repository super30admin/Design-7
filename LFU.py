class LFUCache:
    class Node:
        def __init__(self,key,value):
            self.key=key
            self.value=value
            self.frq=1
            self.prev=None
            self.next=None
    class DLList:
        def __init__(self):
            self.head=LFUCache.Node(-1,-1)
            self.tail=LFUCache.Node(-1,-1)
            self.tail.prev=self.head
            self.head.next=self.tail
            self.size=0
        
        def insertToHead(self,node):
            node.prev=self.head
            node.next=self.head.next
            self.head.next.prev=node
            self.head.next=node
            self.size+=1

        def removeNode(self,node):
            node.prev.next=node.next
            node.next.prev=node.prev
            node.next=None
            node.prev=None
            self.size-=1
            

    def __init__(self, capacity: int):
        self.capacity=capacity
        self.map={}
        self.Freqmap={}
        self.minFrq=0

    def update(self, node):
        oldFrq = node.frq
        oldFrqList = self.freqMap[oldFrq]
        oldFrqList.removeNode(node)

        if oldFrq == self.minFrq and oldFrqList.size == 0:
            self.minFrq += 1

        newFrq = oldFrq + 1
        node.frq = newFrq
        newFrqList = self.freqMap.get(newFrq, LFUCache.DLList())
        newFrqList.insertNodeToHead(node)
        self.freqMap[newFrq] = newFrqList

    def get(self, key: int) -> int:
        if key not in self.map:
            return -1
        node = self.map[key]
        self.update(node)
        return node.value

        

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.map:
            node = self.map[key]
            node.value = value
            self.update(node)
        else:
            if len(self.map) == self.capacity:
                minFrqList = self.freqMap[self.minFrq]
                lastNode = minFrqList.tail.prev
                minFrqList.removeNode(lastNode)
                del self.map[lastNode.key]

            self.minFrq = 1
            newNode = LFUCache.Node(key, value)
            self.map[key] = newNode
            minFrqList = self.Freqmap.get(self.minFrq, LFUCache.DLList())
            minFrqList.insertNodeToHead(newNode)
            self.freqMap[self.minFrq] = minFrqList
        


