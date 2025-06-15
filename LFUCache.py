# Time Complexity : O(1) for get and put operations
# Space Complexity : O(n)
    # We are using a hashmap to store the key and node mapping
    # and another hashmap to store the frequency and doubly linked list mapping.
class ListNode:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.next = None
        self.prev = None
        self.freq = 1
    
class DLList:
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
        node.prev.next = node.next
        node.next.prev = node.prev
        node.next = None
        node.prev = None
        self.size -= 1

class LFUCache:

    def __init__(self, capacity: int):
        self.nodeMap = {}
        self.freqMap = {}
        self.minFreq = 1
        self.capacity = capacity

    def update(self, node):
        oldFreq = node.freq
        oldFreqList = self.freqMap[oldFreq]
        oldFreqList.removeNode(node)
        del self.nodeMap[node.key]
        if oldFreq == self.minFreq and oldFreqList.size == 0:
            self.minFreq += 1
        self.freqMap[oldFreq] = oldFreqList
        newFreq = oldFreq + 1
        node.freq = newFreq
        if newFreq not in self.freqMap:
            self.freqMap[newFreq] = DLList()
        newFreqList = self.freqMap[newFreq]
        newFreqList.addToHead(node)
        self.nodeMap[node.key] = node

    def get(self, key: int) -> int:
        if key not in self.nodeMap:
            return -1
        node = self.nodeMap[key]
        self.update(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if key in self.nodeMap:
            node = self.nodeMap[key]
            node.val = value
            self.update(node)
        else:
            if len(self.nodeMap) == self.capacity:
                minFreqList = self.freqMap[self.minFreq]
                lastNode = minFreqList.tail.prev
                minFreqList.removeNode(lastNode)
                del self.nodeMap[lastNode.key]
            self.minFreq = 1
            node = ListNode(key, value)
            minFreqList = self.freqMap.get(self.minFreq, DLList())
            minFreqList.addToHead(node)
            self.nodeMap[key] = node
            self.freqMap[self.minFreq] = minFreqList