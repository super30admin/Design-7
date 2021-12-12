# Time Complexity: O(1) for get() and put()
# Space Complexity: O(n), where n is the capacity

class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.freq = 1
        self.next = None
        self.prev = None


class DoublyLinkedList:
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

    def remove_node(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def remove_last(self):
        tail_prev = self.tail.prev
        self.remove_node(tail_prev)
        return tail_prev


class LFUCache:

    def __init__(self, capacity: int):
        """
        Initializing two HashMaps.
        One to store (key, Node) pair, and
        Other to store (frequency, List of nodes with LRU node at the tail)
        """
        self.keymap = defaultdict(Node)
        self.freqmap = defaultdict(DoublyLinkedList)
        self.capacity = capacity
        self.minfreq = float('inf')

    def get(self, key: int) -> int:
        """
        # If key is present in the map, update its freq and return its value
        """
        if key in self.keymap:
            node = self.keymap[key]
            self.update_frequency(node)
            return node.value
        return -1

    def put(self, key: int, value: int) -> None:
        """
        case1: If key is present in the map, simply update its value and freq
        """
        if key in self.keymap:
            node = self.keymap[key]
            self.update_frequency(node)
            node.value = value
            return
        """
        case2: If not present, create a new node and insert into the hashmaps.
        If capacity is full, remove the node with least freq and least recently used
        """
        if self.capacity == 0:
            return

        if self.capacity == len(self.keymap):
            removed_node = self.freqmap[self.minfreq].remove_last()
            self.keymap.pop(removed_node.key)

        newnode = Node(key, value)
        self.keymap[key] = newnode
        self.minfreq = 1
        self.freqmap[self.minfreq].add_to_head(newnode)

    def update_frequency(self, node) -> None:
        """
        Update the frequency of the node
        Remove the node from its old freq list and add it to the new freq list.

        """
        old_freq = node.freq
        node.freq += 1
        new_freq = node.freq
        self.freqmap[old_freq].remove_node(node)
        self.freqmap[new_freq].add_to_head(node)

        # Update the minimum frequency
        if old_freq == self.minfreq and self.freqmap[old_freq].size == 0:
            self.minfreq += 1
