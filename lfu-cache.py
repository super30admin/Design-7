import heapq

class Node:
    def __init__(self, key, value, freq, timestamp):
        self.key = key
        self.value = value
        self.freq = freq
        self.timestamp = timestamp

    def __lt__(self, other):
        if self.freq != other.freq:
            return self.freq < other.freq
        return self.timestamp < other.timestamp

class LFUCache:

    def __init__(self, capacity):
        self.capacity = capacity
        self.time = 0
        self.map = {}
        self.pq = []

    def get(self, key):
        if key not in self.map:
            return -1

        node = self.map[key]
        self.pq.remove(node)
        heapq.heapify(self.pq)
        node.freq += 1
        node.timestamp = self.time
        self.time += 1
        heapq.heappush(self.pq, node)
        return node.value

    def put(self, key, value):
        if self.capacity == 0:
            return

        if key in self.map:
            node = self.map[key]
            self.pq.remove(node)
            heapq.heapify(self.pq)
            node.value = value
            node.freq += 1
            node.timestamp = self.time
            self.time += 1
            heapq.heappush(self.pq, node)
        else:
            if len(self.map) == self.capacity:
                lfu = heapq.heappop(self.pq)
                del self.map[lfu.key]
            new_node = Node(key, value, 1, self.time)
            self.time += 1
            self.map[key] = new_node
            heapq.heappush(self.pq, new_node)
