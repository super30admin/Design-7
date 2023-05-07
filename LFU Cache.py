# Time Complexity: O(1) for get and put functions
# Space Complexity: O(n)
class LinkedList:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.count = 1
        self.next = None
        self.prev = None


class DLL:
    def __init__(self):
        # Need a queue to perform LRU when there is a tie in frequency
        self.head = LinkedList(None, None)
        self.tail = LinkedList(None, None)

        self.head.next = self.tail
        self.tail.prev = self.head


class LFUCache:
    def __init__(self, capacity: int):
        # Need hashmap for frequency
        self.hmap = {}
        # Need hashmap for keeping LRU LinkedLists based on Frequency
        self.countmap = collections.defaultdict(DLL)

        # Need a mincount if data exceeds capacity of LFU Cache
        self.min = 1

        # Total capacity allowed
        self.capacity = capacity
        # Keep count of total elements in LFU Cache
        self.maxcapacity = 0

    def get(self, key: int) -> int:
        # Get least frequently used key-value pair, if same freuqency duplicates exist, check LRU queue and fetch the least recently used
        # print("getting", key)
        # print(if key in self.hmap)
        # for k,v in self.hmap.items():
        #     print(k)
        if key not in self.hmap:
            return -1
        else:
            # Change place in the LRU of countmap
            currnode = self.hmap[key]
            cnt = self.hmap[key].count
            # Remove currnode from cnt's LinkedList
            currnode.prev.next = currnode.next
            currnode.next.prev = currnode.prev
            # Move currnode to cnt+1's LinkedList
            temp = self.countmap[cnt + 1].head.next
            self.countmap[cnt + 1].head.next = currnode
            currnode.next = temp
            temp.prev = currnode
            currnode.prev = self.countmap[cnt + 1].head
            # Increase count variable in the linkedlist as well
            currnode.count += 1
            if cnt == self.min and self.countmap[self.min].head.next.val == None:
                self.min += 1
            # Return value
            return currnode.val

    def put(self, key: int, value: int) -> None:
        # Case 1 key does not exist in map before

        if key not in self.hmap:
            # First Check if size is not exceeding
            if self.maxcapacity == self.capacity:
                # Remove Least Frequently used element
                remnode = self.countmap[self.min].tail.prev
                # while remnode.next.val != None:
                #     remnode = remnode.next
                # Remove remnode from cnt's LinkedList
                remnode.prev.next = remnode.next
                remnode.next.prev = remnode.prev
                del self.hmap[remnode.key]
                self.maxcapacity -= 1
                # print("Removing", remnode.val)

            # Create new entry in hmap with a new node
            self.hmap[key] = LinkedList(key, value)
            # Add 1 to maxcapacity
            self.maxcapacity += 1

            # append new node to 1's key in countmap's LinkedList
            temp = self.countmap[1].head.next
            self.countmap[1].head.next = self.hmap[key]
            self.hmap[key].next = temp
            temp.prev = self.hmap[key]
            self.hmap[key].prev = self.countmap[1].head
            # setting min to newly added elements count
            self.min = 1
            # print("Adding", key)

        # Case 2 key exists in map before
        else:
            currnode = self.hmap[key]
            cnt = self.hmap[key].count
            # Remove currnode from cnt's LinkedList
            currnode.val = value  # Update currnode's value
            currnode.prev.next = currnode.next
            currnode.next.prev = currnode.prev
            # Move currnode to cnt+1's LinkedList
            temp = self.countmap[cnt + 1].head.next
            self.countmap[cnt + 1].head.next = currnode
            currnode.next = temp
            temp.prev = currnode
            currnode.prev = self.countmap[cnt + 1].head
            # Increase count variable in the linkedlist as well
            self.hmap[key].count += 1

        if self.countmap[self.min].head.next.val == None:
            self.min += 1
        # print("Here putting", key)
        # print("Min",self.min)
        # print("Capacity", self.maxcapacity)
        # for k,v in self.hmap.items():
        #     print(k)

# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)