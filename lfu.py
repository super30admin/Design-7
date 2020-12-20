# time-O(1)
# space-O(k)-hashmaps
# two hashmaps are maintained- one to keep the frequency- freq as key and val in the form of DLL(for the order) and the next is a mapping of key to node of(key,val,freq)
class Node:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.next = None
        self.prev = None
        self.count = 1


class DLL:
    def __init__(self):
        self.size = 0
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.prev = self.head

    def add(self, node):
        node.next = self.head.next
        self.head.next = node
        node.next.prev = node
        node.prev = self.head
        self.size += 1

    def remove(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def removelast(self):
        last = self.tail.prev
        self.remove(last)
        return last


class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.map = {}
        self.freqmap = {}
        self.min = float('inf')

    def update(self, node):
        count = node.count
        dll = self.freqmap[count]
        dll.remove(node)
        if dll.size == 0 and self.min == count:
            self.min += 1
        count += 1
        if count not in self.freqmap:
            newlist = DLL()
            self.freqmap[count] = newlist
        newdll = self.freqmap[count]
        newdll.add(node)

    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            self.update(node)
            return node.val
        return -1

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
        if key in self.map:
            node = self.map[key]
            node.val = value
            self.update(node)
        else:
            node = Node(key, value)
            if len(self.map) == self.capacity:
                minnodes = self.freqmap[self.min]
                last = minnodes.removelast()
                del self.map[last.key]
            print(key, value)

            if 1 not in self.freqmap:
                newlist = DLL()
                self.freqmap[1] = newlist
            newlist = self.freqmap[1]
            newlist.add(node)
            self.map[key] = node
            self.min = 1
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)