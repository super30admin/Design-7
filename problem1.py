# Time complexity: get,put = O(1)
#Space Complexity : o(n) where n is the capacity of the linkedList

class Node:
    def __init__(self, key, val):
        self.prev = None
        self.next = None

        self.key = key
        self.val = val
        self.cnt = 1


class Dll:
    def __init__(self):
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.size = 0

        self.head.next = self.tail
        self.tail.prev = self.head

    def addTolist(self, node):
        node.next = self.head.next
        node.prev = self.head
        node.next.prev = node
        self.head.next = node

        self.size += 1

    def removeNode(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def removeLast(self):
        lastElem = self.tail.prev
        self.removeNode(lastElem)
        return lastElem


class LFUCache:

    def __init__(self, capacity: int):
        self.freqMap = {}
        self.hashMap = {}
        self._min = 1
        self.capacity = capacity

    def update(self, node):
        count = node.cnt
        dll = self.freqMap[count]
        if dll is not None:
            dll.removeNode(node)
        if dll.size == 0 and self._min == node.cnt:
            self._min += 1

        count += 1
        node.cnt = count

        newDll = self.freqMap.get(count, Dll())
        newDll.addTolist(node)
        self.freqMap[count] = newDll

    def get(self, key: int) -> int:
        if key not in self.hashMap:
            return -1

        node = self.hashMap[key]
        self.update(node)

        return node.val

    def put(self, key: int, value: int) -> None:
        if self.capacity == 0:
            return
            # if in the hashmap
        if key in self.hashMap:
            node = self.hashMap[key]
            node.val = value
            self.update(node)

        else:
            if len(self.hashMap) == self.capacity:
                dll = self.freqMap[self._min]
                node = dll.removeLast()
                del self.hashMap[node.key]

            newNode = Node(key, value)
            self._min = 1
            newDll = self.freqMap.get(self._min, Dll())
            newDll.addTolist(newNode)
            self.freqMap[self._min] = newDll
            self.hashMap[newNode.key] = newNode
