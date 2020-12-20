# Time:- get:- O(1) put:- O(1)
# Space:- O(c) capacity of the LFUCache
# Approach:- Maintain a DLL for each level of frequency. DLL will maintain which is the LRU element. The level of frequency 
# tells us the the frequency of the elements at that level. When we get or put an element we will update the frequency of 
# the element by taking it out from an existing frequency level and put it into the next frequency level. When the capacity
# of the LFU cache is reached we will evict from the end(LRU element) of the LF level.
# To directly access all the elements maintain them in a hashmap(key-> DLLNode). To store the node according the frequency
# maintain a frequency map(frequency->[DLL(head),DLL(tail)]). To access the lfu element maintain a minlevel

from collections import defaultdict


class DLL:
    def __init__(self,key=None,value=None,freq=None):
        # storing the key in the node for when we remove the lru key we will delete the key from the hashmap.
        # we will need the key at that time
        self.key=key
        self.value=value
        self.freq=freq
        self.prev=None
        self.next=None
class LFUCache:

    def __init__(self, capacity: int):
        # key->DLLNode
        self.hashmap={}
        # freq->[head,tail]
        self.freqmap={}
        self.minfreq=1
        self.capacity=capacity

    # create and return the head and tail pointer of a DLL
    def createDLL(self):
        head=DLL()
        tail=DLL()
        head.next=tail
        tail.prev=head
        return [head,tail]

    # remove a DLL node from the DLL 
    def removenodefromanywhereDLL(self,node):
        prev=node.prev
        next=node.next
        prev.next=next
        next.prev=prev
        node.prev=None
        node.next=None

    # append to the head of the DLL 
    def appendtoDLL(self,head,node):
        next=head.next
        head.next=node
        node.prev=head
        node.next=next
        next.prev=node

    # remove from the end of the DLL 
    def removefromendDLL(self,tail):
        nodetoberemoved=tail.prev
        tail.prev=tail.prev.prev
        nodetoberemoved.prev=None
        nodetoberemoved.next=None
        tail.prev.next=tail

    # remove a node from the frequency map 
    def removefromfreqmap(self,node,freq):
        self.removenodefromanywhereDLL(node)
        head=self.freqmap[freq][0]
        tail=self.freqmap[freq][1]
        # if no more nodes in the level we need to delete this level from the frequency map
        # if this was the minfrequency level we will update the minfrequency+1 because we will eventually add it 
        # to the next level
        if head.next==tail:
            del self.freqmap[freq]
            if freq==self.minfreq:
                self.minfreq+=1

    # add a node to the frequency map 
    def addtofreqmap(self,node,freq):
        if freq not in self.freqmap:
            self.freqmap[freq]=self.createDLL()
        head=self.freqmap[freq][0]
        self.appendtoDLL(head,node)

        
    # if the key is in the hashmap we need to take it out from that frequency level and add it to the next level
    def get(self, key: int) -> int:
        if key in self.hashmap:
            node=self.hashmap[key]
            self.hashmap[key].freq+=1
            freq=self.hashmap[key].freq
            self.removefromfreqmap(node,freq-1)
            self.addtofreqmap(node,freq)
            return self.hashmap[key].value
        return -1
        
    def put(self, key: int, value: int) -> None:
        # edge case  
        if self.capacity==0:
            return
        # if capacity of the LFU cache has been reached then we need to evict the least recently used element
        # from the least frequently used level. Edge case:- We dont need to evict if the key is already in the 
        # LFU cache 
        if len(self.hashmap)==self.capacity and key not in self.hashmap:
            tail=self.freqmap[self.minfreq][1]
            head=self.freqmap[self.minfreq][1]
            del self.hashmap[tail.prev.key]
            self.removefromendDLL(tail)
            if head.next==tail:
                del self.freqmap[self.minfreq]
        
        # if the key is in the hashmap we need to take it out from that level and append it to the next frequency level
        if key in self.hashmap:
            self.hashmap[key].freq+=1
            self.hashmap[key].value=value
            freq=self.hashmap[key].freq
            self.removefromfreqmap(self.hashmap[key],freq-1)
            self.addtofreqmap(self.hashmap[key],freq)
        # Seeing this key for the first time will set the frequency to 1 and add it to the frequency map 
        else:
            freq=1
            self.minfreq=1
            node=DLL(key,value,1)
            self.addtofreqmap(node=node,freq=freq)
            self.hashmap[key]=node