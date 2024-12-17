# Approach:
# 1. Dictionary to store the Key - value pair
# 2. Dictionary containing Frequency as Key and value as linkedList in the order of used
# 3. When there is a put request check for capacity and check if the key is not in dictionary - if there is capacity insert into the dictionary 1 and then insert into the frequency dictionary (freq : 1) and insert at head in the values linked list
# 4. Else if it exceeds capacity then remove LFU(freq) - LRU(end of linkedlist in LFU)
# 5. For Get request if its present increment the freq count and make it as head
# Implementing LinkedList: I am taking Doubly LinkedList, functions that i need is insertathead, remove(remove at end, remove anywhere in middle)
class LFUCache:
    # Imp: Storing the frequency in the Node
    class Node:
        def __init__(self, key, value):
            self.key = key
            self.value = value
            self.prev = None
            self.next = None
            self.freq = 1

    class DoublyLinkedList:
        def __init__(self):
            self.head = LFUCache.Node(None, None)
            self.tail = LFUCache.Node(None, None)
            self.head.next = self.tail
            self.tail.prev = self.head

        def insert_at_head(self, node):
            node.next = self.head.next
            node.prev = self.head
            self.head.next.prev = node
            self.head.next = node

        def remove_node(self, node):
            node.prev.next = node.next
            node.next.prev = node.prev

        def pop_tail(self):
            if self.head.next == self.tail:
                return None
            tail_node = self.tail.prev
            self.remove_node(tail_node)
            return tail_node

        def is_empty(self):
            return self.head.next == self.tail

    def __init__(self, capacity):
        self.capacity = capacity
        self.min_freq = 0
        self.size = 0
        self.dict1 = {}  # Key: key-value (node)
        self.dict2 = defaultdict(self.DoublyLinkedList)

    def update(self, node):
        # Remove the node from current frequency dict2; increment the frequency and add it to head of new frequency

        current_freq = node.freq
        self.dict2[current_freq].remove_node(node)

        # Increment the min_freq as we need to remove it from LFU; But this contains the empty
        if self.dict2[current_freq].is_empty() and current_freq == self.min_freq:
            self.min_freq += 1

        node.freq += 1
        self.dict2[node.freq].insert_at_head(node)

    def get(self, key: int) -> int:
        if key not in self.dict1:
            return -1
        # Get the node and update its frequecy
        node = self.dict1[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:

        if key in self.dict1:
            node = self.dict1[key]
            node.value = value
            self.update(node)
        else:
            if self.size == self.capacity:
                # LFU -> Remove the LRU
                removed_node = self.dict2[self.min_freq].pop_tail()
                del self.dict1[removed_node.key]
                self.size -= 1

            # Add new node
            new_Node = self.Node(key, value)
            self.min_freq = 1
            self.dict1[key] = new_Node
            self.dict2[self.min_freq].insert_at_head(new_Node)
            self.size += 1

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)