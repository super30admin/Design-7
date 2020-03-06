class LFUCache:
    """
        Time Complexity - O(1)
    """

    class Node:

        def __init__(self, key: int, val: int):
            self.key = key
            self.val = val
            self.count = 1
            self.next = None
            self.prev = None

    class DLList:

        def __init__(self):
            self.head = LFUCache.Node(None, None)
            self.tail = LFUCache.Node(None, None)
            self.head.next = self.tail
            self.tail.prev = self.head
            self.size = 0

        def add_to_head(self, node) -> None:
            node.next = self.head.next
            node.prev = self.head
            self.head.next.prev = node
            self.head.next = node
            self.size += 1

        def remove_node(self, node) -> None:
            node.prev.next = node.next
            node.next.prev = node.prev
            self.size -= 1

        def remove_last(self):
            last_node = self.tail.prev
            self.remove_node(last_node)
            return last_node

    def __init__(self, capacity: int):
        self.dic = {}
        self.freq_dic = {}
        self.cache_size = 0
        self.capacity = capacity
        self.min = float('-inf')

    def get(self, key: int) -> int:
        # exists
        if key in self.dic:
            node = self.dic[key]
            self.update(node)
            return node.val
        # does not exists
        return -1

    def put(self, key: int, value: int) -> None:
        # edge case
        if self.capacity == 0:
            return

        # Case 1: Key is already there in cache
        if key in self.dic:
            node = self.dic[key]
            node.val = value
            self.update(node)
        else:
            # Case 2: Key not present
            node = self.Node(key, value)
            # Cache is full
            if self.cache_size == self.capacity:
                # get the DLL with minimum value
                min_list = self.freq_dic[self.min]
                to_be_removed = min_list.remove_last()
                self.dic.pop(to_be_removed.key)
                self.cache_size -= 1
            if 1 not in self.freq_dic:
                self.freq_dic[1] = self.DLList()
            self.freq_dic[1].add_to_head(node)
            self.dic[key] = node
            self.min = 1
            self.cache_size += 1

    # updating node in freq map
    def update(self, node) -> None:
        # get the DLL with this count
        old_list = self.freq_dic[node.count]
        old_list.remove_node(node)
        if node.count == self.min and old_list.size == 0:
            self.min += 1
        node.count += 1
        if node.count not in self.freq_dic:
            self.freq_dic[node.count] = self.DLList()
        self.freq_dic[node.count].add_to_head(node)
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
