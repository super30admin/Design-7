class Node:
    def __init__(self, key, value):
        self.key = key
        self.val = value
        self.count = 1
        self.prev = None
        self.next = None


class DLList:

    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def addTohead(self, node):
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
        self.capacity = capacity
        self.hashMap = {}
        self.freqMap = {}
        self.min = 0
        # self.size = 0

    def get(self, key: int) -> int:
        if key not in self.hashMap:
            return -1
        node = self.hashMap[key]
        self.update(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if key in self.hashMap:
            node = self.hashMap[key]
            node.val = value
            self.update(node)
        else:
            if self.capacity == 0:
                return
            if self.capacity == len(self.hashMap):
                minList = self.freqMap[self.min]
                toRemove = minList.removeLast()
                del self.hashMap[toRemove.key]

            newNode = Node(key, value)
            self.min = 1
            newList = self.freqMap.get(1, DLList())
            newList.addTohead(newNode)
            self.freqMap[1] = newList
            self.hashMap[key] = newNode

    def update(self, node):
        prevList = self.freqMap[node.count]
        prevList.removeNode(node)

        if self.min == node.count and prevList.size == 0:
            self.min += 1

        node.count += 1
        newList = self.freqMap.get(node.count, DLList())
        newList.addTohead(node)
        self.freqMap[node.count] = newList

# Doubly linked List and hashMap
# Time Complexity : O(1)
# Space Complexity: O(n). capacity

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
