'''
Problem: LFU Cache
Time Complexity: O(1)
Space Complexity: O(n*m), where n is the number of keys in the cache and m is size of each node
Did this code successfully run on Leetcode: Yes
Any problem you faced while coding this: No
Your code here along with comments explaining your approach:
        created hasmap to store key and node pair
        created a separate hashmap for freq as key and doubly linked list as value
        this DLL maintains the nodes in LRU fashion
'''

class Node:
    def __init__(self, key, value):
        self.key = key
        self.val = value
        self.freq = 1
        self.next = None
        self.prev = None

class DLinkList:
    def __init__(self):
        self.size = 0
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
    
    def addtohead(self, node):
        node.prev = self.head
        node.next = self.head.next
        self.head.next = node
        node.next.prev = node
        self.size+=1

    def removenode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size-=1

    def removetail(self):
        node = self.tail.prev
        self.removenode(node)
        return node

class LFUCache:

    def __init__(self, capacity: int):
        self.cap = capacity
        self.map = {}
        self.fmap = {}
        self.min = 0
        
    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            oldfreq = node.freq
            node.freq+=1

            #remove from old list
            oldlist = self.fmap[oldfreq]
            oldlist.removenode(node)

            #add to new list
            newlist = self.fmap.get(node.freq, DLinkList())
            newlist.addtohead(node)

            #add to fmap
            self.fmap[node.freq] = newlist

            #checkif mnimum has changed
            if self.min == oldfreq and self.fmap[oldfreq].size == 0:
                self.min+=1

            return node.val
        else:
            return -1
        

    def put(self, key: int, value: int) -> None:
        if key in self.map:
            node = self.map[key]
            node.val = value
            oldfreq = node.freq
            node.freq+=1

            #remove from old list
            oldlist = self.fmap[oldfreq]
            oldlist.removenode(node)

            #add to new list
            newlist = self.fmap.get(node.freq, DLinkList())
            newlist.addtohead(node)

            #add to fmap
            self.fmap[node.freq] = newlist

            #checkif mnimum has changed
            if self.min == oldfreq and self.fmap[oldfreq].size == 0:
                self.min+=1

        else:
            if self.cap == len(self.map):
                #capacity full remove lfu
                dllist = self.fmap[self.min]
                removenode = dllist.removetail()
                del self.map[removenode.key]
                if dllist.size == 0:
                    self.min+=1
            
            #add new node
            newnode = Node(key, value)
            self.map[key] = newnode
            addlist = self.fmap.get(1, DLinkList())
            addlist.addtohead(newnode)
            self.fmap[1] = addlist
            self.min = 1


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)