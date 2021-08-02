# // Time Complexity : O(1)
# // Space Complexity : O(1)
# // Did this code successfully run on Leetcode : Yes
# // Any problem you faced while coding this : No
#
#
# // Your code here along with comments explaining your approach

class Node(object):
    def __init__(self, key, value):
        self.val = value
        self.key = key
        self.next = None
        self.prev = None
        self.counter = 1


class DDL(object):
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.count = 0
        self.head.next = self.tail
        self.tail.prev = self.head

    def addToHead(self, node):
        self.count += 1
        node.prev = self.head
        node.next = self.head.next
        self.head.next = node
        node.next.prev = node

    def removeNode(self, node):
        self.count -= 1
        node.prev.next = node.next
        node.next.prev = node.prev
        return node


class LFUCache(object):

    def __init__(self, capacity):
        """
        :type capacity: int
        """
        self.hmap = {}
        self.countmap = {}
        self.size = capacity
        self.min = 0

    def get(self, key):
        """
        :type key: int
        :rtype: int
        """
        if (key in self.hmap):
            self.put(key, self.hmap[key].val)
            return self.hmap[key].val
        return -1

    def put(self, key, value):
        """
        :type key: int
        :type value: int
        :rtype: None
        """
        # key already present
        if (key in self.hmap and self.size > 0):
            self.hmap[key].val = value
            oldCount = self.hmap[key].counter
            self.hmap[key].counter += 1
            if (oldCount == self.min and self.countmap[oldCount].count == 1):
                # need to update the mini as well
                self.min = oldCount + 1
                self.countmap[oldCount].removeNode(self.hmap[key])
                # del self.countmap[oldCount]
                if (oldCount + 1 in self.countmap):
                    self.countmap[oldCount + 1].addToHead(self.hmap[key])
                else:
                    self.countmap[oldCount + 1] = DDL()
                    self.countmap[oldCount + 1].addToHead(self.hmap[key])

            else:
                self.countmap[oldCount].removeNode(self.hmap[key])
                if (oldCount + 1 in self.countmap):
                    self.countmap[oldCount + 1].addToHead(self.hmap[key])
                else:
                    self.countmap[oldCount + 1] = DDL()
                    self.countmap[oldCount + 1].addToHead(self.hmap[key])
            # print(key,value,self.min)
            # print(self.countmap[self.min].count)

        elif (self.size > 0):
            # key was not present
            if (self.size == len(self.hmap)):

                # capacity is full
                # remove LfU or LRU
                # remove from hmap as well
                newNode = Node(key, value)
                oldNode = self.countmap[self.min].removeNode(self.countmap[self.min].tail.prev)
                self.min = 1
                self.countmap[self.min].addToHead(newNode)
                self.hmap[key] = newNode
                del self.hmap[oldNode.key]


            else:
                # capacity is not full
                newNode = Node(key, value)
                self.min = 1
                self.hmap[key] = newNode
                if (self.min not in self.countmap):
                    # not present
                    self.countmap[self.min] = DDL()
                self.countmap[self.min].addToHead(newNode)