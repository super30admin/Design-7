## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

// Time Complexity - 0(1)
// Space Complexity - 0(c) where the capacity refers to the maximum number of elements the cache can hold

class LFUCache {

    class Node {
        int key, value, freq;
        Node next, prev;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DLList {
        Node head, tail;
        int size;
        public DLList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }
        private void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        private Node removeLastNode() {
            Node lastnode = tail.prev;
            removeNode(lastnode);
            return lastnode;
        }
    }
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqmap;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqmap = new HashMap<>();  
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
            return;
        }
        if (capacity == 0) {
            return;
        }
        if (capacity == map.size()) {
            DLList oldlist = freqmap.get(min);
            Node lastnode = oldlist.removeLastNode();
            map.remove(lastnode.key);
        }
        Node node = new Node(key, value);
        min = 1;
        DLList newlist = freqmap.getOrDefault(min, new DLList());
        newlist.addToHead(node);
        map.put(key, node);
        freqmap.put(min, newlist);
    }
    private void update(Node node) {
        DLList oldlist = freqmap.get(node.freq);
        if (oldlist != null && oldlist.size != 0) {
            oldlist.removeNode(node);
        }
        if (min == node.freq && oldlist.size == 0) {
            min++;
        }
        node.freq++;
        DLList newlist = freqmap.getOrDefault(node.freq, new DLList());
        newlist.addToHead(node);
        freqmap.put(node.freq, newlist);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */


Problem2 H-Index (https://leetcode.com/problems/h-index/)

// Time complexity - 0(n) where n is the length of the input array citations
// Space complexity - 0(n)

class Solution {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int n = citations.length;
        int[] dp = new int[n + 1];

        for (int i = 0; i <citations.length; i++) {
            int index = citations[i];
            if (index >= n) {
                dp[n]++;
            }
            else {
                dp[index]++;
            }
        }
        int rSum = 0;
        for (int i = n; i >= 0; i--) {
            rSum = rSum + dp[i];
            if (rSum >= i) {
                return i;
            }
        }
        return -1;
    }
}


## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

I am unable to see the problem as i need to Subscribe to unlock.