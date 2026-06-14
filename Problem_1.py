class Node:
    def __init__(self, key: int, val: int):
        self.key = key
        self.val = val
        self.freq = 1
        self.prev = None
        self.next = None


class DoublyLinkedList:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def addToHead(self, node: Node):
        nxt = self.head.next
        self.head.next = node
        node.next = nxt
        node.prev = self.head
        nxt.prev = node
        self.size += 1

    def removeNode(self, node: Node):
        nxt = node.next
        prv = node.prev
        prv.next = nxt
        nxt.prev = prv
        self.size -= 1

    def removeLast(self) -> Node:
        if self.size > 0:
            node = self.tail.prev
            self.removeNode(node)
            return node
        return None


class LFUCache:

    def __init__(self, capacity: int):
        self.cacheMap = {}
        self.freqMap = {}
        self.minFreq = 0
        self.capacity = capacity

    def get(self, key: int) -> int:
        if key not in self.cacheMap:
            return -1

        currNode = self.cacheMap[key]
        self.updateList(currNode)
        return currNode.val

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.cacheMap:
            node = self.cacheMap[key]
            node.val = value
            self.updateList(node)
        else:
            if self.capacity == len(self.cacheMap):
                minList = self.freqMap[self.minFreq]
                toRemove = minList.removeLast()
                if toRemove:
                    del self.cacheMap[toRemove.key]

            newNode = Node(key, value)
            self.cacheMap[key] = newNode

            if 1 not in self.freqMap:
                self.freqMap[1] = DoublyLinkedList()

            self.freqMap[1].addToHead(newNode)
            self.minFreq = 1

    def updateList(self, node: Node) -> None:
        freq = node.freq
        oldList = self.freqMap[freq]
        oldList.removeNode(node)

        if freq == self.minFreq and oldList.size == 0:
            self.minFreq += 1

        node.freq += 1

        if node.freq not in self.freqMap:
            self.freqMap[node.freq] = DoublyLinkedList()

        self.freqMap[node.freq].addToHead(node)


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
