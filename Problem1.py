"""
I implemented the LFU (Least Frequently Used) Cache using two hash maps: one (key_node) for quick key-to-node access and another (freq_list) mapping frequencies to doubly linked lists that maintain nodes in least recently used order within the same frequency. Each node tracks its key, value, and frequency. On every get or put, the node's frequency is updated and moved to the corresponding frequency list. When capacity is reached, the least frequently used and least recently used node is evicted. This design guarantees O(1) time complexity for both operations.
Time Complexity: O(1)
Space Complexity: O(n)
"""
class LFUCache:
    class Node:
        def __init__(self, key, value):
            self.key = key
            self.value = value
            self.freq = 1
            self.prev = None
            self.next = None

    class DLList:
        def __init__(self):
            self.head = LFUCache.Node(0, 0)
            self.tail = LFUCache.Node(0, 0)
            self.head.next = self.tail
            self.tail.prev = self.head
            self.size = 0

        def insert_to_head(self, node):
            node.next = self.head.next
            node.prev = self.head
            self.head.next.prev = node
            self.head.next = node
            self.size += 1

        def remove(self, node):
            node.prev.next = node.next
            node.next.prev = node.prev
            self.size -= 1

        def remove_last(self):
            if self.size == 0:
                return None
            last_node = self.tail.prev
            self.remove(last_node)
            return last_node

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.size = 0
        self.minFreq = 0
        self.key_node = {} 
        self.freq_list = defaultdict(LFUCache.DLList)

    def update(self, node):
        freq = node.freq
        self.freq_list[freq].remove(node)

        if freq == self.minFreq and self.freq_list[freq].size == 0:
            self.minFreq += 1

        node.freq += 1
        self.freq_list[node.freq].insert_to_head(node)

    def get(self, key: int) -> int:
        if key not in self.key_node:
            return -1
        node = self.key_node[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.key_node:
            node = self.key_node[key]
            node.value = value
            self.update(node)
        else:
            if self.size == self.capacity:
                to_remove = self.freq_list[self.minFreq].remove_last()
                if to_remove:
                    del self.key_node[to_remove.key]
                    self.size -= 1

            new_node = LFUCache.Node(key, value)
            self.key_node[key] = new_node
            self.freq_list[1].insert_to_head(new_node)
            self.minFreq = 1
            self.size += 1