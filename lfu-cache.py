class Node:
    def __init__(self, value, count):
        self.value = value
        self.count = count

class LFUCache:
    # tc O(1, per one method call for all methods), sc O(n, overall for all calls). 
    def __init__(self, capacity: int):
        self.cache = {}
        self.counts = defaultdict(OrderedDict)
        self.capacity = capacity
        self.mincount = None

    def get(self, key: int) -> int:
        if key not in self.cache:
            return -1
        
        node = self.cache[key]
        del self.counts[node.count][key]

        node.count += 1
        self.counts[node.count][key] = node

        if not self.counts[self.mincount]:
            self.mincount += 1 

        return node.value

    def put(self, key: int, value: int) -> None:
        if key in self.cache:
            self.cache[key].value = value
            self.get(key)
            return
        
        if len(self.cache) == self.capacity:
            # remove item
            lfukey, _ = self.counts[self.mincount].popitem(last=False)
            del self.cache[lfukey]
        
        newnode = Node(value, 1)
        self.cache[key] = newnode
        self.counts[1][key] = newnode
        self.mincount = 1




        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)