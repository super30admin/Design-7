'''
Solution:
1.  The main Data Structures used would be a Doubly-Linked List, and HashMaps. A node in DLL
    also contains the frequency of occurrence of that key.
2.  Maintain an actual HashMap to store Nodes in LFU. Another HashMap to maintain DLLs of
    each frequency so that if there is a tie, use the concept of LRU instead of LFU.

Time Complexity:    O(1) for all operations
Space Complexity:   O(C) for Nodes in both HashMaps where C - capacity of LFU Cache

--- Passed all testcases successfully on Leetcode.
'''


class Node:
    
    #   Node class whose instance is a part of our Doubly-Linked List
    #   also contains the extra info of frequency.
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.freq = 1
        self.prev = None
        self.next = None


class DLList:
    
    def __init__(self):
        self.head = Node(-1, -1)        #   dummy head
        self.tail = Node(-1, -1)        #   dummy tail
        self.head.next = self.tail      #   head's next is initially pointing to tail
        self.tail.prev = self.head      #   tail's prev is initially pointing to head
        self.size = 0                   #   size of DLL
        
    def printCache(self):

        #   helper function to print elements stored in cache at a particular point of time

        currNode = self.head.next
        while (currNode != self.tail):
            print(currNode.key, currNode.value)
            currNode = currNode.next

        print('---DONE---')
        
    def insertAtHead(self, node):

        #   Time Complexity:    O(1)    |   Space Complexity:   O(1)
        #   insert's a node at the head position (next node of dummy head)

        #   make correct references to the next and prev of the node in consideration
        node.prev = self.head
        node.next = self.head.next

        #   attach node's reference to dummy head and node's next node
        self.head.next = node
        node.next.prev = node
        
        #   increment size as new Node is added
        self.size += 1

        return

    def remove(self, node):

        #   Time Complexity:    O(1)    |   Space Complexity:   O(1)
        #   removes a node keeping intact its next and prev nodes.
        node.prev.next = node.next
        node.next.prev = node.prev
        
        #   decrement size as a Node is deleted
        self.size -= 1

        return
    
    def removeLast(self):
        
        #   remove the last Node without explicitly being provided with the Node
        cursor = self.tail.prev
        self.remove(cursor)
        return cursor
        

class LFUCache:

    def __init__(self, capacity: int):
        
        self.elementsInLFUMap = {}          #   HashMap of actual elements present in the Cache
        self.frequencyMap = {}              #   HashMap containing frequency info (DLLs)
        self.min = float('inf')             #   minimum frequency at each state
        self.size = 0                       #   current size of the Cache
        self.capacity = capacity            #   overall capacity of LFU
        
    def __update(self, node) -> None:
        
        #   updates the frequency HashMap

        #   extract the DLL of the Node's frequency and remove the Node from that DLL
        #   as frequency gets updated
        priorList = self.frequencyMap[node.freq]
        priorList.remove(node)
        
        #   after removal, if DLL is empty and if that is existing min freq => update min freq
        if (priorList.size == 0):
            if (node.freq == self.min):
                self.min += 1
        
        #   update the Node's frequency by 1 
        node.freq += 1
        
        #   if updated frequency is not in frequency Map => create a new DLL
        if (node.freq not in self.frequencyMap):
            newList = DLList()
            self.frequencyMap[node.freq] = newList
            
        #   now get the DLL of new frequency and insert the Node at the head of that DLL
        newList = self.frequencyMap[node.freq]
        newList.insertAtHead(node)
            

    def get(self, key: int) -> int:
        
        #   if key not present at all => return -1
        if (key not in self.elementsInLFUMap):
            return -1
        
        #   get the Node info from original HashMap and update the Node's info
        #   return the Node's value
        node = self.elementsInLFUMap[key]
        self.__update(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        
        #   edge case check
        if (self.capacity == 0):
            return
        
        #   if key already in actual HashMap => update the Node's info with new value
        if (key in self.elementsInLFUMap):
            node = self.elementsInLFUMap[key]
            node.value = value
            self.__update(node)
         
        #   if not present   
        else:
            
            #   create a new Node
            node = Node(key, value)
            
            #   if Cache is full => remove LFU Node using min freq and DLL corresponding to that
            if (self.size == self.capacity):
                minList = self.frequencyMap[self.min]
                removed = minList.removeLast()
                del self.elementsInLFUMap[removed.key]
                self.size -= 1
            
            #   if freq = 1 doesn't exist in DLLs Map => create that and update the min freq to 1
            if (1 not in self.frequencyMap):
                newList = DLList()
                self.frequencyMap[1] = newList
            
            #   insert this Node at the head of freq = 1 DLL
            newList = self.frequencyMap[1]
            newList.insertAtHead(node)
            self.elementsInLFUMap[key] = node
            self.min = 1  
            self.size += 1                       
            


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)