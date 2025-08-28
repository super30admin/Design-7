'''
460 LFU Cache
https://leetcode.com/problems/lfu-cache/description/
Design and implement a data structure for a Least Frequently Used (LFU) cache.
https://en.wikipedia.org/wiki/Least_frequently_used

Implement the LFUCache class:
    LFUCache(int capacity) Initializes the object with the capacity of the data structure.
    int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
    void put(int key, int value) Update the value of the key if present, or inserts the key if not already present. When the cache reaches its capacity, it should invalidate and remove the least frequently used key before inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be invalidated.

To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use counter for a key in the cache is incremented either a get or put operation is called on it.

The functions get and put must each run in O(1) average time complexity.


Example 1:
Input
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]
Explanation
// cnt(x) = the use counter for key x
// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[4,3], cnt(4)=2, cnt(3)=3

Constraints:
1 <= capacity <= 104
0 <= key <= 105
0 <= value <= 109
At most 2 * 105 calls will be made to get and put.

Solution:
1. Maintain two hash maps - map and freq_map.
map: <key, Node(key,value,freq)>
freq_map: <freq, head of DLL>
The first hash map "map" maps the key to a Node (containing key, value, freq).
The second hash map "freq_map" maps the freq of a key to the head of a doubly linked list (DLL). Every node in the DLL contains nodes with the same freq. Thus, the freq_map acts like a list of DLLs and each DLL is a list of nodes.
The minimum value of the key in freq_map is the min freq. The min value is tracked by a variable 'LFUCache.min'. Thus, the DLL corresponding to min freq ('LFUCache.min') is the LFU DLL. The first node in the LFU DLL is the LRU node.


Whenever we access or update a key, we increase its frequency count and move it to the updated bucket.
If the cache is full and a new key needs to be inserted, we evict the least frequently used one from the lowest frequency list.
This way, both frequency and recency are tracked, and we always get or put in O(1) time.

https://www.youtube.com/watch?v=gd6jTRJf85I
Time: O(1) (put, get), Space: O(2N) (N = capacity, 2N -> 2 hash maps)
'''
from collections import defaultdict

class LFUCache:
    class Node:
        def __init__(self, key, value, freq=1, next=None, prev=None):
            self.key = key
            self.value = value
            self.freq = freq
            self.next = next
            self.prev = prev

        def __str__(self):
            node = self
            result = ""
            while node:
                result += f"({node.key},{node.value}) [{node.freq}] ->"
                node = node.next
            result += "None"
            return result

    class DLL:
        def __init__(self):
            self.head = LFUCache.Node(-1,-1)
            self.tail = LFUCache.Node(-1,-1)
            self.head.next = self.tail
            self.tail.prev = self.head
            self.size = 0

        def add_to_head(self, node):
            next = self.head.next

            next.prev = node
            node.next = next
            node.prev = self.head
            self.head.next = node

            self.size += 1

        def remove_node(self, node):
            prev = node.prev
            next = node.next

            prev.next = next
            next.prev = prev

            node.prev = None
            node.next = None

            self.size -= 1

        def remove_last_node(self):
            node = self.tail.prev
            if node != self.head:
                self.remove_node(node)
            return node

    def __init__(self, capacity: int):
        self.map = defaultdict(LFUCache.Node)
        self.freq_map = defaultdict(LFUCache.DLL)
        self.min = 0 # track min freq (LFU)
        self.capacity = capacity

    def __str__(self):
        for k in self.map:
            result = f"map[{k}]: Node({self.map[k].key}, {self.map[k].value}, {self.map[k].freq})"
            print(result)

        for k in self.freq_map:
            print(f"freq_map[{k}]:", end = " ")
            print(self.freq_map[k].head)
        return f"min freq = {self.min}"


    def get(self, key: int) -> int:
        if key not in self.map:
            return -1

        # Step 1: Fetch the value
        node = self.map[key]
        value = node.value

        # Step 2: Update the freq_map
        self.update_freq_map(node)

        return value

    def put(self, key: int, value: int) -> None:
        if key in self.map:
            node = self.map[key]
            node.value = value
            self.update_freq_map(node)
        else:
            if len(self.map) == self.capacity:
                # get LFU DLL
                old_dll = self.freq_map[self.min]
                # remove LRU node from LFU DLL
                last_node = old_dll.remove_last_node()
                del self.map[last_node.key]

            node = LFUCache.Node(key, value)
            self.map[key] = node
            self.min = 1

            self.freq_map[node.freq].add_to_head(node)

    # Update the freq_map
    def update_freq_map(self, node) -> None:
        # a) Remove node from old DLL
        old_freq = node.freq
        old_dll = self.freq_map[old_freq]
        old_dll.remove_node(node)

        # b) Add node to new DLL
        new_freq = old_freq + 1
        if new_freq not in self.freq_map:
            self.freq_map[new_freq] = LFUCache.DLL()
        self.freq_map[new_freq].add_to_head(node)

        # c) Update the node's freq value
        node.freq = new_freq

        # d) Update self.min
        if self.min == old_freq:
            if old_dll.size == 0:
                self.min += 1

def run_LFUCache():
    tests = [ (["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get", "put"],
               [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4], [3,30]],
               [None, None, None, 1, None, -1, 3, None, -1, 3, 4, None]),
    ]
    for test in tests:
        operations, nums, ans = test[0], test[1], test[2]
        result = []
        for operation, num in zip(operations, nums):
            if operation == "LFUCache":
                lfu = LFUCache(num[0])
                x = None
            elif operation == "put":
                x=lfu.put(num[0], num[1])
            elif operation == "get":
                x=lfu.get(num[0])
            print(f"{operation}({num}): {x}")
            print(f"{lfu}\n")
            result.append(x)

        print(f"\nOperations = {operations}")
        print(f"nums = {nums}")
        print(f"result = {result}")
        success = (ans==result)
        print(f"Pass: {success}")
        if not success:
            print("Failed")
            return

run_LFUCache()
