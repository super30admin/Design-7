class Node:
    def __init__(self, k, v):
        self.key = k
        self.val = v
        self.count = 1
        self.next = None
        self.prev = None


# Doubly linked list
class DLL:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head

    def __len__(self):
        return 0 if self.head.next == self.tail else 1

    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node

    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next

    def removeTail(self):
        nodeToRemove = self.tail.prev
        self.removeNode(nodeToRemove)
        return nodeToRemove


class LFUCache:

    def __init__(self, capacity: int):
        self.nodeMap = {}
        self.freqMap = {}
        self.cap = capacity
        self.min = 1

    # O(1)
    def get(self, key: int) -> int:
        if key not in self.nodeMap: return -1
        node = self.nodeMap.get(key)
        self.update(node)
        return node.val

    # O(1)
    def put(self, key: int, value: int) -> None:
        if key in self.nodeMap:
            node = self.nodeMap.get(key)
            node.val = value
            self.update(node)
        else:

            if self.cap == 0: return
            if self.cap <= len(self.nodeMap):
                minFreq = self.freqMap.get(self.min)
                rmNode = minFreq.removeTail()
                del self.nodeMap[rmNode.key]
            newNode = Node(key, value)
            self.min = 1
            li = self.freqMap.get(self.min, DLL())
            li.addToHead(newNode)
            self.freqMap[self.min] = li
            self.nodeMap[key] = newNode

    def update(self, node):
        oldCount = node.count
        oldFreqList = self.freqMap.get(node.count)
        oldFreqList.removeNode(node)
        if oldCount == self.min and len(oldFreqList) == 0:
            self.min += 1
        node.count += 1
        newList = self.freqMap.get(node.count, DLL())
        newList.addToHead(node)
        self.freqMap[node.count] = newList

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)