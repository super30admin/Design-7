#Time Complexity : O(1)
#Space Complexity :O(N)
#Did this code successfully run on Leetcode :No
#Any problem you faced while coding this : No

from collections import defaultdict


class LFUCache:

    def __init__(self, capacity: int):
        """
        :type capacity: int
        """
        self.capacity = capacity
        self.size = 0
        self.cache = {}
        self.frequencyMap = defaultdict(LinkedList)
        self.minFreq = 0
        

    def get(self, key: int) -> int:
        """
        :type key: int
        :rtype: int
        """
        if key not in self.cache:
            return -1
        
        tempNode = self.cache[key]
        self.frequencyMap[tempNode.freq].removeNode(tempNode)
        if self.frequencyMap[tempNode.freq].head is None:
            del self.frequencyMap[tempNode.freq]
            if self.minFreq == tempNode.freq:
                self.minFreq += 1
        
        self.cache[key].freq += 1
        self.frequencyMap[self.cache[key].freq].insertAtTail(self.cache[key])
        return self.cache[key].value
        

    def put(self, key: int, value: int) -> None:
        """
        :type key: int
        :type value: int
        :rtype: None
        """
        
        if self.capacity == 0:
            return
        
        if key in self.cache:
            self.get(key)
            self.cache[key].value = value
            return
        
        if self.size == self.capacity:
            del self.cache[self.frequencyMap[self.minFreq].head.key]
            self.frequencyMap[self.minFreq].removeHead()
            if self.frequencyMap[self.minFreq].head is None:
                del self.frequencyMap[self.minFreq]
            self.size -= 1
        
        self.minFreq = 1
        self.cache[key] = LinkedListNode(key, value, self.minFreq)
        self.frequencyMap[self.cache[key].freq].insertAtTail(self.cache[key])
        self.size += 1
    
    def printFrequencyMap(self):
        print("frequncyMap:")
        for freq, linkedList in self.frequencyMap.items():
            print("frequency", freq)
            print("linked list", printLinkedList(linkedList))

def printLinkedList(linkedList):
    current = linkedList.head
    nodes = []
    while current is not None:
        nodes.append(str(current.value))
        current = current.next
    return " -> ".join(nodes)
    
    
class LinkedListNode:
    def __init__(self, key, value, freq):
        self.key = key
        self.value = value
        self.freq = freq
        self.next = None
        self.prev = None

class LinkedList:
    def __init__(self):
        self.head = None
        self.tail = None
    
    def insertAtTail(self, node):
        node.prev, node.next = None, None
        if self.tail is None:
            self.head = node
            self.tail = node
        else:
            self.tail.next = node
            node.prev = self.tail
            self.tail = node
    
    def removeNode(self, node):
        if node is None:
            return
        
        if node.prev is not None:
            node.prev.next = node.next
        if node.next is not None:
            node.next.prev = node.prev
        if node == self.head:
            self.head = self.head.next
        if node == self.tail:
            self.tail = self.tail.prev
    
    def removeHead(self):
        self.removeNode(self.head)

        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
