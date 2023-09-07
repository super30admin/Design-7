# Time Complexity: O(1)
# Space Complexity: O(n)
# Did this problem run on Leetcode: Yes
# Any issues faced doing this problem: No

class ListNode:
    def __init__(self, key, value):
        self.key = key
        self.val = value
        self.freq = 1

class LFUCache(object):
    def __init__(self, capacity):
        """
        :type capacity: int
        """
        self.capacity = capacity
        self.cache = dict() 
        self.usage = collections.defaultdict(collections.OrderedDict)
        self.LF = 0

    def get(self, key):
        """
        :type key: int
        :rtype: int
        """
        if key not in self.cache:
            return -1
        node = self.cache[key]
        self.update(node, node.val)
        return node.val
        
    def put(self, key, value):
        """
        :type key: int
        :type value: int
        :rtype: None
        """
        if self.capacity == 0: 
            return
        if key not in self.cache: 
            if len(self.cache) >= self.capacity:
                k, v = self.usage[self.LF].popitem(last = False)
                self.cache.pop(k)
            node = ListNode(key, value)
            self.cache[key] = node
            self.usage[1][key] = value
            self.LF = 1
        else: 
            node = self.cache[key]
            node.val = value
            self.update(node, value)

    def update(self, node, newVal):
        k, f = node.key, node.freq
        self.usage[f].pop(k)
        if not self.usage[f] and self.LF == f:
            self.LF += 1
        self.usage[f+1][k] = newVal
        node.freq += 1
        
# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)