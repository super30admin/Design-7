# Time Complexity : O(1) for get and put operations
# Space Complexity : O(capacity) for the cache
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.freq = 1
        self.prev = None
        self.next = None

class DoublyLinkedList:
    def __init__(self):
        self.head = Node(0, 0)  # dummy head
        self.tail = Node(0, 0)  # dummy tail
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
    
    def add_to_front(self, node):
        """Add node to the front (most recently used)"""
        node.next = self.head.next
        node.prev = self.head
        self.head.next.prev = node
        self.head.next = node
        self.size += 1
    
    def remove_node(self, node):
        """Remove a specific node"""
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
    
    def remove_last(self):
        """Remove the least recently used node (from the end)"""
        if self.size == 0:
            return None
        node = self.tail.prev
        self.remove_node(node)
        return node

class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.cache = {}  # key -> node
        self.freq_map = {}  # freq -> DoublyLinkedList
        self.min_freq = 0
        self.size = 0

    def get(self, key: int) -> int:
        if key not in self.cache:
            return -1
        
        node = self.cache[key]
        self._update_frequency(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        
        if key in self.cache:
            # Update existing key
            node = self.cache[key]
            node.value = value
            self._update_frequency(node)
        else:
            # Insert new key
            if self.size >= self.capacity:
                # Remove least frequently used item
                self._remove_lfu()
            
            # Create new node
            node = Node(key, value)
            self.cache[key] = node
            
            # Add to frequency 1 list
            if 1 not in self.freq_map:
                self.freq_map[1] = DoublyLinkedList()
            self.freq_map[1].add_to_front(node)
            
            self.size += 1
            self.min_freq = 1

    def _update_frequency(self, node):
        """Update the frequency of a node and move it to the appropriate list"""
        old_freq = node.freq
        new_freq = old_freq + 1
        
        # Remove from old frequency list
        self.freq_map[old_freq].remove_node(node)
        
        # If old frequency list becomes empty and it was the minimum frequency
        if self.freq_map[old_freq].size == 0:
            del self.freq_map[old_freq]
            if self.min_freq == old_freq:
                self.min_freq = new_freq
        
        # Update node frequency
        node.freq = new_freq
        
        # Add to new frequency list
        if new_freq not in self.freq_map:
            self.freq_map[new_freq] = DoublyLinkedList()
        self.freq_map[new_freq].add_to_front(node)

    def _remove_lfu(self):
        """Remove the least frequently used item"""
        if self.min_freq not in self.freq_map:
            return
        
        freq_list = self.freq_map[self.min_freq]
        node_to_remove = freq_list.remove_last()
        
        if node_to_remove:
            del self.cache[node_to_remove.key]
            self.size -= 1
            
            # If this frequency list becomes empty, remove it
            if freq_list.size == 0:
                del self.freq_map[self.min_freq]
                # Find new minimum frequency
                if self.freq_map:
                    self.min_freq = min(self.freq_map.keys())
                else:
                    self.min_freq = 0


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)