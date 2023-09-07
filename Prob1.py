class Node(object):
    def __init__(self,key,val):
        self.val=val
        self.key=key
        self.cnt=1
        self.next=None
        self.prev=None
class DLL(object):
    def __init__(self):
        self.size=0
        self.head=Node(-1,-1)
        self.tail=Node(-1,-1)
        self.head.next=self.tail
        self.tail.prev=self.head
    def addToHead(self,node):
        node.prev=self.head
        node.next=self.head.next
        self.head.next=node
        node.next.prev=node
        self.size+=1
    def removeNode(self,node):
        node.next.prev=node.prev
        node.prev.next=node.next
        self.size-=1
    
    def tailRemove(self):
        toremove=self.tail.prev
        self.removeNode(toremove)
        return toremove
class LFUCache(object):
    def __init__(self, capacity):
        """
        :type capacity: int
        """
        self.hashmap={}
        self.freqmap={}
        self.capacity=capacity
        self.minn=0

    def get(self, key):
        """
        :type key: int
        :rtype: int
        """
        if key not in self.hashmap:
            return -1
        node=self.hashmap[key]

        self.update(node)
        
        return node.val

    def update(self,node):
        oldFreq=node.cnt
        oldList=self.freqmap[oldFreq]
        oldList.removeNode(node)
        if oldFreq==self.minn and oldList.size==0:
            self.minn+=1
        node.cnt+=1
        newCnt=node.cnt
        if newCnt not in self.freqmap:
            self.freqmap[newCnt]=DLL()
        newList=self.freqmap[newCnt]
        newList.addToHead(node)


    def put(self, key, value):
        """
        :type key: int
        :type value: int
        :rtype: None
        """
        if key in self.hashmap:
            node=self.hashmap[key]
            node.val=value
            self.update(node)
        else:
            if self.capacity==len(self.hashmap):
                # remove LRU node
                minFreq=self.freqmap[self.minn]
                toRemove=minFreq.tailRemove()
                self.hashmap.pop(toRemove.key)
            newnode=Node(key,value)
            self.minn=1
            if self.minn not in self.freqmap:
                self.freqmap[self.minn]=DLL()
            newList=self.freqmap[self.minn]
            newList.addToHead(newnode)
            self.hashmap[key]=newnode


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)