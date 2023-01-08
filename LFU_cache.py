from collections import defaultdict
    ## T.C = O(1)
    ## S.C = O(n)

class Node:
    def __init__(self, key=-1, val=-1):
        self.key = key
        self.val = val
        self.frequency = 1
        self.next = None
        self.prev = None

class DoublyLL:

    def __init__(self):
        self.head = Node()
        self.tail = Node()
        self.head.next, self.tail.prev = self.tail, self.head
        self.size = 0
    
    def addToHead(self, node):
        node.next = self.head.next
        self.head.next.prev = node
        node.prev = self.head
        self.head.next = node
        self.size += 1
    
    def removeNode(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1
    
    def removeLastNode(self):
        last_node = self.tail.prev
        self.removeNode(last_node)
        return last_node



class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.hm = {}
        self.hm_freq = defaultdict(self.def_val)
        self.min = 0

    def def_val(self):
        return None

    def get(self, key: int) -> int:
        if key not in self.hm.keys():
            return -1
        node = self.hm[key]
        self.update(node)
        return node.val
        

    def put(self, key: int, value: int) -> None:
        if key in self.hm.keys():
            node = self.hm[key]
            update(node)
            node.val = value
            return
        
        if self.capacity == len(self.hm):
            old_dll = self.hm_freq[self.min]
            node = old_dll.removeLastNode()
            self.hm.pop(node.key)
        
        node = Node(key, value)
        self.min = 1
        self.hm[key] = node
        new_dll = self.hm_freq[node.frequency]
        if not new_dll:
            self.hm_freq[node.frequency] = DoublyLL()
        
        self.hm_freq[node.frequency].addToHead(node)

    
    def update(self, node):
        old_dll = self.hm_freq[node.frequency]
        old_dll.removeNode(node)
        
        if self.min == node.frequency and old_dll.size == 0:
            self.min += 1
        
        node.frequency += 1
        new_dll = self.hm_freq[node.frequency]
        if not new_dll:
            self.hm_freq[node.frequency] = DoublyLL()
        
        self.hm_freq[node.frequency].addToHead(node)


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)