class Node:
    def __init__(self, value, freq):
        self.value = value
        self.freq = freq
        
class LFUCache:

    def __init__(self, cap: int):
        self.freq = defaultdict(OrderedDict) 
        self.d = {}
        self.minfreq = -1
        self.cap = cap

    def get(self, key: int) -> int:
        if key not in self.d:
            return -1
        n = self.d[key]
        od = self.freq[n.freq]
        del od[key]
        if not od and self.minfreq == n.freq:
            self.minfreq += 1
        n.freq += 1
        od = self.freq[n.freq]
        od[key] = n
        return n.value

    def put(self, key: int, value: int) -> None:
        if self.get(key) != -1:
            self.d[key].value = value
            return
        if self.cap == 0:
            return
        if len(self.d) == self.cap:
            od = self.freq[self.minfreq]
            k, v = od.popitem(last=False)
            del self.d[k]
        n = Node(value, 1)
        self.d[key] = n
        self.freq[1][key] = n
        self.minfreq = 1

%TC:O(n)
%SC:O(n)