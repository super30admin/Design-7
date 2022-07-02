'''
Time Complexity:    O(1) 
Space Complexity:   O(C)
Did it run on leet code: Not successful for all test cases 17/26 cases working
'''
class Node:
    def __init__(self,key,value,freq=0):
        self.key = key
        self.value = value
        self.freq = freq
        self.next = None
        self.prev = None

class doublyLL:
    def __init__(self):
        self.head = Node(-1,-1) # dummy head
        self.tail = Node(-1,-1) # dummy tail
        
        # update head and tail rfr's
        self.head.next = self.tail
        self.tail.prev = self.head        
        
        # size of doubly linked list
        self.size = 0
        
    def insert(self,objNewNode):
        
        # update the objNewNode rfr's
        objNewNode.prev = self.head
        objNewNode.next = self.head.next
        
        # update self.head rfr's
        self.head.next.prev = objNewNode
        self.head.next = objNewNode
        
        # update size
        self.size += 1
        
    def remove(self,deleteNode):
        
        if self.size == 0:
            return -1
        
        # rfr to prevNode and nextNode
        prevNode = deleteNode.prev
        nextNode = deleteNode.next
        
        # update prevNode rfr's
        prevNode.next = deleteNode.next
        
        # update nextNode rfr's
        nextNode.prev = deleteNode.prev
        
        # update deleteNode rfr
        deleteNode.next = None
        deleteNode.prev = None
        
        # update size
        self.size -= 1
        
        return deleteNode
        
    def printDll(self):
        if self.size <= 0:
            return "Nothing :("
        
        currentNode = self.head.next
        while currentNode != self.tail:
            print(f"Key is {currentNode.key}\t Value is:\t {currentNode.value}\t Freq is:\t{currentNode.freq}")
            currentNode = currentNode.next
        
class LFUCache:

    def __init__(self, capacity: int):
        # define maxCapacity
        self.maxCapacity = capacity
        
        # define freqDict
        self.freqDict = {}
        self.minFrequency = 0
        
        # define keyDict
        self.keyDict = {}
    
    def update(self,getNode):
        # 1. Get the dLL from the frequcencyDict
        dll = self.freqDict[getNode.freq]
        
        # 2. remove the get node from the dll
        updated_getNode = dll.remove(getNode)
        
        '''chk the size of dll -- base chk'''
        if dll.size == 0:
            # remove the freq entry from the freqDict and update the minfreq
            del self.freqDict[updated_getNode.freq]
            self.minFrequency += 1
        
        # 4. update the freq by +1
        updated_getNode.freq = updated_getNode.freq+1
        
        # 5. freq-pair in freqDict -- both new or existing-node
        if updated_getNode.freq not in self.freqDict:
            self.freqDict[updated_getNode.freq] = doublyLL()
        self.freqDict[updated_getNode.freq].insert(updated_getNode)
        
        # 6. return the value
        return updated_getNode
    '''end of update function'''
    
    def get(self, key: int) -> int:
        # base-case
        if key not in self.keyDict:
            return -1
        
        # logic-case
        # 1. get the node
        getNode = self.keyDict[key]
        
        '''call the update function'''
        updated_getNode = self.update(getNode)
        
        # 2. return the value
        # print("Function Get:\t",vars(updated_getNode)) 
        return updated_getNode.value
    '''end of get function'''
    
    def put(self, key: int, value: int) -> None:

        if key in self.keyDict:
            # update case -- lot of similarities with get() method
            '''call the update function'''
            updated_getNode = self.update(getNode)
            # update the value
            updated_getNode.value = value
        
        else:
            '''add the newNode to the lru cache'''
            # 1. create node
            objNewNode = Node(key,value,1)
            
            if len(self.keyDict) == self.maxCapacity:
                # insert case -- remove the lfu node
                # 1. get the lfu node
                dll = self.freqDict[self.minFrequency]
                # 2. remove the lru node from the dll
                lruNode = dll.remove(dll.tail.prev)            
                lruNode.next = None
                lruNode.prev = None
                '''base-chk'''
                if dll.size == 0:
                    # remove the freq entry from the freqDict and update the minfreq
                    del self.freqDict[lruNode.freq]
                    self.minFrequency += 1
                # 3. delete the entry from the keyDict
                del self.keyDict[lruNode.key]
            '''lru node is deleted'''
        
            # else --- just insert
            '''add the newNode to the lfu cache'''
            # 2. create entry in freqDict
            self.minFrequency = 1
            if self.minFrequency not in self.freqDict:
                self.freqDict[self.minFrequency] = doublyLL()
            # 3. insert into dll
            self.freqDict[self.minFrequency].insert(objNewNode)
            # 4. make an entry in keyDict
            self.keyDict[key] = objNewNode
            
            
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)

lfu = LFUCache(2)
lfu.put(1,1)   # cache=[1,_], cnt(1)=1
lfu.put(2, 2)   # cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1)      # return 1
                 # cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3)   # 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 # cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2)      # return -1 (not found)
lfu.get(3)      # return 3
                 # cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4)   # Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 # cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1)      # return -1 (not found)
lfu.get(3)      # return 3
                 # cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4)      # return 4
                 # cache=[4,3], cnt(4)=2, cnt(3)=3