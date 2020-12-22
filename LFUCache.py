#Time Complexity : O(1)
#Space Complexity : O(N)
#Did this code successfully run on Leetcode : Yes

class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.frequency = 1
        self.next = None
        self.prev = None

class doublyLinkedList:
    def __init__(self):
        self.head = Node(None, None)
        self.tail = Node(None, None)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def __len__(self):
        return self.size

    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        node.next.prev = node
        self.head.next = node
        self.size += 1

    def deleteNode(self, node=None):
        if self.size == 0:
            return

        if not node:
            node = self.tail.prev

        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

        return node

class LFUCache:
    def __init__(self, capacity: int):
        self.map = {}
        self.frequencies = collections.defaultdict(doublyLinkedList)
        self.capacity = capacity
        self.minFrequency = 0

    def update(self, node):
        count = node.frequency
        self.frequencies[count].deleteNode(node)

        if count == self.minFrequency and not self.frequencies[count]:
            self.minFrequency += 1

        node.frequency += 1
        self.frequencies[node.frequency].addToHead(node)

    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            self.update(node)
            return node.value
        return -1

    def put(self, key: int, value: int) -> None:
        #if key exists in main map already
        if self.capacity == 0:
            return

        if key in self.map:
            node = self.map[key]
            self.update(node)
            node.value = value

        #if key doesn't exist in main map
        else:
            #if capacity is full
            if self.capacity == len(self.map):
                node = self.frequencies[self.minFrequency].deleteNode()
                del self.map[node.key]

            newNode = Node(key, value)
            self.minFrequency = 1
            self.frequencies[self.minFrequency].addToHead(newNode)
            self.map[key] = newNode


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
