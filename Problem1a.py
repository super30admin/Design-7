# Define a Doubly Linked List Node structure
'''
Time complexity - O(1)
Space complexity - O(capacity)
'''


class Node:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.freq = 1
        self.prev = None
        self.next = None

# Define a Doubly Linked List structure


class DoublyLinkedList:
    def __init__(self):
        self.head = Node(None, None)
        self.tail = Node(None, None)
        self.head.next = self.tail
        self.tail.prev = self.head

    # Insert a node at the front of the doubly linked list
    def insert_at_front(self, node):
        node.next = self.head.next
        self.head.next.prev = node
        node.prev = self.head
        self.head.next = node

    # Remove a node from the doubly linked list
    def remove(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev

    # Check if the doubly linked list is empty
    def is_empty(self):
        return self.head.next == self.tail

# Define the LFUCache class


class LFUCache:
    def __init__(self, capacity):
        self.capacity = capacity
        self.key_node_map = {}    # Stores key-to-node mapping
        self.freq_node_map = {}   # Stores frequency-to-doubly-linked-list mapping
        self.min_freq = 0         # Store the minimum frequency in the cache

    # Update the frequency of a node and reposition it in the appropriate frequency list
    def _update_freq(self, node):
        freq = node.freq
        self.freq_node_map[freq].remove(node)

        if self.min_freq == freq and self.freq_node_map[freq].is_empty():
            self.min_freq += 1

        node.freq += 1
        if node.freq not in self.freq_node_map:
            self.freq_node_map[node.freq] = DoublyLinkedList()
        self.freq_node_map[node.freq].insert_at_front(node)

    # Get the value of a key from the cache
    def get(self, key):
        if key not in self.key_node_map:
            return -1

        node = self.key_node_map[key]
        self._update_freq(node)
        return node.val

    # Put a key-value pair into the cache
    def put(self, key, value):
        if self.capacity == 0:
            return

        if key in self.key_node_map:
            node = self.key_node_map[key]
            node.val = value
            self._update_freq(node)
        else:
            if len(self.key_node_map) >= self.capacity:
                min_freq_list = self.freq_node_map[self.min_freq]
                del_key = min_freq_list.tail.prev.key
                min_freq_list.remove(min_freq_list.tail.prev)
                del self.key_node_map[del_key]

            new_node = Node(key, value)
            self.key_node_map[key] = new_node
            self.min_freq = 1
            if 1 not in self.freq_node_map:
                self.freq_node_map[1] = DoublyLinkedList()
            self.freq_node_map[1].insert_at_front(new_node)
