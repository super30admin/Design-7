class Node:
    def __init__(self, key, value, freq):
        self.key = key
        self.value = value
        self.freq = freq
        self.prev = None
        self.next = None

class DLList:
    def __init__(self):
        self.head = Node(-1, -1, -1)
        self.tail = Node(-2, -2, -2)
        self.head.next = self.tail
        self.tail.prev = self.head

class LFUCache:
    def __init__(self, capacity: int):
        self.freqListMap = dict() # freq: DLList
        self.keyNodeMap = dict() # key: Node
        self.minFreq = float('inf')
        self.cap = capacity
    
    def update(self, node):
        freq = node.freq

        node.prev.next = node.next
        node.next.prev = node.prev

        node.freq+=1

        if freq+1 not in self.freqListMap:
            self.freqListMap[freq+1] = DLList()
        
        dll = self.freqListMap[freq+1]
        first = dll.head.next
        first.prev = node
        node.next = first
        node.prev = dll.head
        dll.head.next = node
        
        if self.minFreq == freq:
            OldDll = self.freqListMap[freq]
            if OldDll.head.next.key == -2:
                self.minFreq+=1

    def get(self, key: int) -> int:
        if key not in self.keyNodeMap:
            return -1
        
        node = self.keyNodeMap[key]
        self.update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if key not in self.keyNodeMap:
            if len(self.keyNodeMap)==self.cap:
                # remove node from its list
                lfuList = self.freqListMap[self.minFreq]
                lfuNode = lfuList.tail.prev
                prev = lfuNode.prev
                prev.next = lfuList.tail
                lfuList.tail.prev = prev
                
                # remove node from hashmap
                self.keyNodeMap.pop(lfuNode.key)

            self.minFreq = 1
            node = Node(key, value, 1)
            self.keyNodeMap[key] = node
            if 1 not in self.freqListMap:
                self.freqListMap[1] = DLList()

            dll = self.freqListMap[1]
            first = dll.head.next
            first.prev = node
            node.next = first
            node.prev = dll.head
            dll.head.next = node
        
        else:
            node = self.keyNodeMap[key]
            # update value of existing node
            node.value = value
            self.update(node)
             


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)