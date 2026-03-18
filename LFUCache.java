/**
 * Uses HashMap (key → node) and HashMap (freq → DLL) to maintain LFU ordering with LRU tie-breaking.
 * Each access increases frequency and moves node to higher freq list; minfreq tracks least freq for eviction.
 * Eviction removes LRU node from the lowest frequency list to maintain O(1) operations.
 *
 * Time Complexity: O(1) for get() and put()
 * Space Complexity: O(capacity)
 */

class DLLNode {
    DLLNode prev;
    DLLNode next;
    int freq;
    int key;
    int val;

    public DLLNode(int key, int val) {
        this.freq = 1; // New nodes always start with freq = 1
        this.key = key;
        this.val = val;
    }
}

class DLList {
    int listSize;
    DLLNode head;
    DLLNode tail;

    public DLList() {
        this.listSize = 0;
        this.head = new DLLNode(0, 0);
        this.tail = new DLLNode(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public void addNode(DLLNode node) {
        // Always add right after head → acts as most recently used within same freq
        DLLNode next = head.next;
        node.prev = head;
        node.next = next;
        head.next = node;
        next.prev = node;
        listSize++;
    }

    public void removeNode(DLLNode node) {
        DLLNode next = node.next;
        DLLNode prev = node.prev;
        prev.next = next;
        next.prev = prev;
        listSize--;
    }

    public DLLNode removeTail() {
        // Removes LRU node within this frequency bucket
        if (listSize > 0) {
            DLLNode prev = tail.prev;
            removeNode(prev);
            return prev;
        }
        return null;
    }
}

class LFUCache {

    int capacity;
    int curSize;
    int minfreq;
    HashMap<Integer, DLLNode> cache;
    HashMap<Integer, DLList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.curSize = 0;
        this.minfreq = 0;

        cache = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        DLLNode currnode = cache.get(key);
        if (currnode == null) {
            return -1;
        }
        updateNode(currnode); // Access bumps frequency
        return currnode.val;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return; // Edge case: no storage allowed
        }

        if (cache.containsKey(key)) {
            DLLNode currnode = cache.get(key);
            currnode.val = value;
            updateNode(currnode);
        } else {

            curSize++;
            if (curSize > capacity) {
                // Evict from lowest frequency bucket using minfreq
                DLList minfreDlList = freqMap.get(minfreq);
                DLLNode delNode = minfreDlList.removeTail();
                cache.remove(delNode.key);
                curSize--;
            }

            // Reset minfreq since new node has freq = 1
            minfreq = 1;

            DLLNode newNode = new DLLNode(key, value);
            DLList currList = freqMap.getOrDefault(1, new DLList());
            currList.addNode(newNode);
            freqMap.put(1, currList);
            cache.put(key, newNode);
        }
    }

    public void updateNode(DLLNode node) {
        int curFreq = node.freq;
        DLList curList = freqMap.get(curFreq);
        curList.removeNode(node);

        // Critical invariant:
        // If this node was the ONLY node at minfreq, and we removed it → minfreq must increase
        if (curFreq == minfreq && curList.listSize == 0) {
            minfreq++;
        }

        node.freq++;

        // Move node to next frequency list (acts like LRU within that freq)
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        newList.addNode(node);
        freqMap.put(node.freq, newList);
    }
}