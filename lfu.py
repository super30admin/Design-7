# Time complexity:
# get(key): O(1)
# put(key, value): O(1)
# Space complexity:
# O(capacity)

from collections import defaultdict

class Node:
    def __init__(self, key, val, count):
        self.key = key
        self.val = val
        self.count = count

class DoublyLinkedList:
    def __init__(self):
        self.head = Node(None, None, 0)
        self.tail = Node(None, None, 0)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
    
    def __len__(self):
        return self.size
    
    def append(self, node):
        node.prev = self.tail.prev
        node.next = self.tail
        self.tail.prev.next = node
        self.tail.prev = node
        self.size += 1
    
    def pop(self, node=None):
        if self.size == 0:
            return
        if not node:
            node = self.head.next
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
        return node

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.key2node = {}
        self.count2dll = defaultdict(DoublyLinkedList)
        self.minCount = None

    def get(self, key: int) -> int:
        if key not in self.key2node:
            return -1
        node = self.key2node[key]
        self.count2dll[node.count].pop(node)
        if node.count == self.minCount and len(self.count2dll[node.count]) == 0:
            self.minCount += 1
        node.count += 1
        self.count2dll[node.count].append(node)
        return node.val

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        if key in self.key2node:
            node = self.key2node[key]
            node.val = value
            self.count2dll[node.count].pop(node)
            if node.count == self.minCount and len(self.count2dll[node.count]) == 0:
                self.minCount += 1
            node.count += 1
            self.count2dll[node.count].append(node)
        else:
            if len(self.key2node) == self.capacity:
                node = self.count2dll[self.minCount].pop()
                del self.key2node[node.key]
            node = Node(key, value, 1)
            self.key2node[key] = node
            self.count2dll[1].append(node)
            self.minCount = 1

