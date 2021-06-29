import collections


class dlNode:
    def __init__(self, capacity: int, value: int, frequency: int):
        self.capacity = capacity
        self.value = value
        self.frequency = 1
        self.next = None
        self.prev = None


class dlinkedlist:
    def __init__(self):
        self.head = dlNode(-1, -1)
        self.tail = dlNode(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head

    def add_to_front(self, node):
        if node is not None:
            ptr = self.head
            node.next = ptr.next
            ptr.next.prev = node
            ptr.next = node
            node.prev = ptr

    def isEmpty(self):
        if self.head.next == self.tail:
            return True
        else:
            return False

    def removeNode(self, node):

        node.prev.next = node.next
        node.next.prev = node.prev
        node.next = None
        node.prev = None

    def rlastNode(self):
        tail = self.tail.prev
        self.removeNode(tail)
        return tail


class LFUCache:

    def __init__(self, capacity: int):

        self.minfrequecny = 0
        self.addressmap = collections.defaultdict(lambda: dlNode)
        self.frequencytoNode = collections.defaultdict(lambda: dlinkedlist)
        self.capacity = capacity

    def get(self, key: int) -> int:
        if key not in self.addressmap.keys():
            return -1
        else:
            self.udate_cache(key)
            return self.addressmap[key].value

    def put(self, key: int, value: int) -> None:

        # key is in addressmap and size

        if key in self.addressmap.keys() and self.capacity > len(self.addressmap.keys()):
            node = self.addressmap[key]
            node.value = value
            freq = node.frequency
            oldlist = self.frequencytoNode[freq]
            oldlist.removeNode(node)
            if len(oldlist) == 0 and self.minfrequecny == freq:
                self.minfrequecny += 1
            node.frequency += 1

            if node.frequency not in self.frequencytoNode.keys():
                dl = dlinkedlist()
                self.frequencytoNode[node.frequency] = dl

            dl = self.frequencytoNode[node.frequency]

            dl.add_to_front(node)
            self.frequencytoNode[node.frequency] = dl

        newNode = dlNode(key, value)
        elif self.capacity > len(self.addressmap.keys()):

            self.addressmap[key] = newNode
             frequency = newNode.frequency
            self.minfrequecny = 1
        if frequency in self.frequencytoNode.keys():
            dl = self.frequencytoNode[frequency]
            dl.add_to_front(newNode)

        else:
            dl = dlinkedlist()
            dl.add_to_front(newNode)
            self.frequencytoNode[frequency] = dl

    elif self.capacity == len(self.addressmap.keys()):

    dl = self.frequencytoNode[self.minfrequecny]
    remove = dl.rlastNode()
    del self.addressmap[remove.key]
    self.addressmap[key] = newNode
    frequency = newNode.frequency
    self.minfrequecny = 1
    if frequency in self.frequencytoNode.keys():
        dl = self.frequencytoNode[frequency]
        dl.add_to_front(newNode)

    else:
        dl = dlinkedlist()
        dl.add_to_front(newNode)
        self.frequencytoNode[frequency] = dl

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)