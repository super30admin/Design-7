# Time Complexity: O(1)
# Space Complexity: O(n)
class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.count = 1
        self.prev = None
        self.next = None

class DList:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def add_to_head(self, node):
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

class LFUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.min = 0
        self.map = dict()              # key -> Node
        self.freqMap = defaultdict(DList)  # freq -> DList

    def update(self, node):
        count = node.count
        self.freqMap[count].remove(node)

        if count == self.min and self.freqMap[count].size == 0:
            self.min += 1

        node.count += 1
        self.freqMap[node.count].add_to_head(node)

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
            return

        if len(self.map) == self.capacity:
            node_to_remove = self.freqMap[self.min].remove_last()
            if node_to_remove:
                del self.map[node_to_remove.key]

        new_node = Node(key, value)
        self.map[key] = new_node
        self.freqMap[1].add_to_head(new_node)
        self.min = 1
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)