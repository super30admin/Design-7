class LFUCache:
    class Node:                      
        def __init__(self, key, val):  # In the node, we'll be storing the key, value, and its frequency
            self.key = key
            self.val = val
            self.freq = 1
            self.prev = None
            self.next = None
    
    class DLList:
        def __init__(self):
            self.head = LFUCache.Node(-1, -1)  # Use LFUCache.Node to refer to the Node class
            self.tail = LFUCache.Node(-1, -1)  # Use LFUCache.Node to refer to the Node class
            self.head.next = self.tail
            self.tail.prev = self.head
            self.size = 0
        
        def addToHead(self, node):  # Add a node to the head of the doubly linked list
            node.prev = self.head
            node.next = self.head.next
            self.head.next = node
            node.next.prev = node
            self.size += 1
        
        def removeNode(self, node):  # Remove a node from the doubly linked list
            if self.size > 0:
                node.prev.next = node.next
                node.next.prev = node.prev
                self.size -= 1
        
        def removeTail(self):  # Remove the tail node (least frequently used) from the doubly linked list
            if self.size > 0:
                toRemove = self.tail.prev
                self.removeNode(toRemove)
                return toRemove

    def __init__(self, capacity: int):
        self.my_dict = {}       # Dictionary to store key-node pairs
        self.freq_dict = {}     # Dictionary to store frequency-DLList pairs
        self.mini = 0           # Minimum frequency (updated)
        self.capacity = capacity

    def get(self, key: int) -> int:
        if key not in self.my_dict:
            return -1
        node = self.my_dict[key]
        self.update(node)       # Update the node's frequency and position in the frequency list
        return node.val

    def update(self, node):
        oldfreq = node.freq      # Storing its current frequency into oldfreq variable
        oldList = self.freq_dict[oldfreq]    # Get the old frequency DLList
        oldList.removeNode(node)             # Remove the node from the old frequency DLList

        if oldfreq == self.mini and oldList.size == 0:  # If the frequency to which it belonged was the minimum frequency and the list is now empty
            self.mini += 1                  # Increment the minimum frequency to the next frequency

        node.freq += 1                      # Increase the frequency value in the node as it is used
        newFreq = node.freq

        # It is possible that the record with new frequency does not exist in the freq_dict
        # If yes, create a new DLList and add it to freq_dict
        newList = self.freq_dict.get(newFreq, self.DLList())   
        newList.addToHead(node)
        self.freq_dict[node.freq] = newList

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return

        if key in self.my_dict:
            node = self.my_dict[key]
            node.val = value
            self.update(node)           # Update the node's frequency and position in the frequency list
        else:
            if len(self.my_dict) >= self.capacity:
                # Remove the least frequently used node (tail of minimum frequency list)
                minFreq = self.freq_dict.get(self.mini, self.DLList())  # Initialize minFreq to a default DLList if not present
                toRemove = minFreq.removeTail()
                if toRemove:
                    del self.my_dict[toRemove.key]    # Remove the key from my_dict

            newNode = self.Node(key, value)
            minFreqList = self.freq_dict.get(1, self.DLList())  # Initialize minFreqList to a default DLList if not present
            minFreqList.addToHead(newNode)
            self.freq_dict[1] = minFreqList
            self.my_dict[key] = newNode  # Add the key-node pair to my_dict
            self.mini = 1  # Reset the minimum frequency to 1 for the newly added node


# Time Complexity:
# 1. get(key): O(1) - The get operation is constant time since it involves a dictionary lookup.
# 2. update(node): O(1) - The update operation involves removing and adding nodes to the doubly linked list, which takes constant time.
# 3. put(key, value): O(1) - The put operation is constant time since it involves a dictionary lookup and adding/removing nodes from the doubly linked list.

# Space Complexity:
# The space complexity is O(capacity) since we are using dictionaries to store nodes and doubly linked lists to manage the frequency of nodes. The space usage is proportional to the capacity of the LFU cache.
