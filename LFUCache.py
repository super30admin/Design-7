# -*- coding: utf-8 -*-
"""
TC:O(N)
SC:O(N)
"""

class LFUCache(object):

    def __init__(self, capacity):
        """
        :type capacity: int
        """
        self.max = capacity
        self.n = 0
        self.cache = {}
        self.freq = defaultdict(list)
    
    def recount(self, key):
        c,v = self.cache[key]
        self.freq[c].remove(key)
        if not self.freq[c]:
            self.freq.pop(c)
        self.cache[key][0] = c + 1
        self.freq[c+1].append(key)
    
    def insert(self, key, val):
        self.cache[key] = [1, val]
        self.freq[1].append(key)
        self.n += 1
    
    def remove(self):
        for k in sorted(self.freq.keys()):
            key = self.freq[k].pop(0)
            if not self.freq[k]:
                self.freq.pop(k)
            self.cache.pop(key)
            self.n -= 1
            break
        
    def get(self, key):
        """
        :type key: int
        :rtype: int
        """
        if self.max == 0:
            return -1
        if key not in self.cache:
            return -1
        
        self.recount(key)
        return self.cache[key][1]
        
    def put(self, key, val):
        """
        :type key: int
        :type value: int
        :rtype: None
        """
        if self.max == 0:
            return
        if key in self.cache:
            self.recount(key)
            self.cache[key][1] = val
        else:
            if self.n < self.max:
                self.insert(key, val)
            else:
                self.remove()
                self.insert(key, val)
