# Time Complexity : O(1)
# Space Complexity :
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach
# Using 2 HashMaps and Doubly Linked List.
# Using 1st HashMap to store key mapped to nodes along with frequencies
# Using 2nd HashMap to store frequency as key mapped to doubly linkedlist to add/remove nodes in O(1)


class ListNode:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.freq = 1
        self.prev = None
        self.next = None


class DLL:
    def __init__(self):
        self.head = ListNode(0, 0)
        self.tail = ListNode(0, 0)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def addNode(self, node):
        node.next = self.head.next
        node.prev = self.head
        self.head.next = node
        node.next.prev = node
        self.size += 1

    def removeNode(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def removeTail(self):
        tail = self.tail.prev
        self.removeNode(tail)
        return tail


class LFUCache:
    def __init__(self, capacity: int):
        self.dict = {}
        self.freqMap = collections.defaultdict(DLL)
        self.capacity = capacity
        self.min = 0

    def get(self, key: int) -> int:
        if key not in self.dict:
            return -1
        node = self.dict[key]
        value = self.dict[key].val
        self.update(node, key, value)
        return node.val

    def put(self, key: int, value: int) -> None:
        if not self.capacity:
            return
        if key in self.dict:
            node = self.dict[key]
            self.update(node, key, value)
        else:
            if len(self.dict) == self.capacity:
                prevTail = self.freqMap[self.min].removeTail()
                del self.dict[prevTail.key]
            node = ListNode(key, value)
            self.min = 1
            self.freqMap[self.min].addNode(node)
            self.dict[key] = node

    def update(self, node, key, value):
        #updating the oldfreq to newfreq
        node = self.dict[node.key]
        node.val = value
        prevfreq = node.freq
        node.freq += 1
        self.freqMap[prevfreq].removeNode(node)
        self.freqMap[node.freq].addNode(node)
        if self.min == prevfreq and self.freqMap[prevfreq].size == 0:
            self.min += 1


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)