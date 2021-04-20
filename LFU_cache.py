from collections import defaultdict
class Node:
    def __init__(self,key,val,next=None, prev = None):
        self.key = key
        self.val = val
        self.next = next
        self.prev = prev
        self.freq = 1


class DoublyLinkedList:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
    
    def isEmpty(self):
        if(self.head.next==self.tail):
            return True
        else:
            return False
        
    def remove(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        node.prev = None
        node.next = None
    
    def add_to_front(self, node):
        self.tail.prev.next = node
        node.prev = self.tail.prev
        node.next = self.tail
        self.tail.prev = node

class LFUCache:
    def __init__(self, capacity: int):
        self.freqMap = defaultdict(DoublyLinkedList)
        self.nodeMap = {}
        self.capacity = capacity
        self.minimum = float("inf")
        
    def __update(self, node):
        ll = self.freqMap[node.freq]
        ll.remove(node)
        if(ll.isEmpty()):
            if(self.minimum == node.freq):
                self.minimum += 1
        node.freq +=1
        ll = self.freqMap[node.freq]
        ll.add_to_front(node)
    
    def __add_to_cache(self, key:int, value:int) -> None:
        node = Node(key, value)
        self.minimum = 1
        self.nodeMap[key] = node
        ll = self.freqMap[self.minimum]
        ll.add_to_front(node)
    
    def __clean_cache(self) -> None:
        ll = self.freqMap[self.minimum]
        node = ll.head.next
        ll.remove(node)    
        del self.nodeMap[node.key]
           
    def get(self, key: int) -> int:
        if(key in self.nodeMap.keys()):
            node = self.nodeMap[key]
            self.__update(node)
            return node.val
        else:
            return -1
        
    def put(self, key: int, value: int) -> None:
        if(self.capacity==0):
            return
        if(key in self.nodeMap.keys()):
            node = self.nodeMap[key]
            node.val = value
            self.__update(node)
        else:
            if(len(self.nodeMap)==self.capacity):
                self.__clean_cache()
            self.__add_to_cache(key,value)
