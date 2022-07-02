// Time Complexity :O(1) all methods
// Space Complexity :O(1)
// Did this code successfully run on Leetcode :Yes
// Any problem you faced while coding this :it took me a lot of time to think, code and debug

class LFUCache {
    private HashMap<Integer, DlNode> map;
    private HashMap<Integer, DLList> freqMap;
    private int capacity;
    private int min;

    // doubly linked list node class
    class DlNode {
        public int val;
        public int key;
        public int freq;
        public DlNode next;
        public DlNode prev;

        // constructor
        public DlNode(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }

    }

    // doubly linked list class
    class DLList {
        public DlNode head;
        public DlNode tail;
        public int size;

        public DLList() {
            // initializing head and tail
            this.head = new DlNode(-1, -1);
            this.tail = new DlNode(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void removeNode(DlNode node) {
            // removing particular node
            node.next.prev = node.prev;
            node.prev.next = node.next;

            this.size--;
        }

        public void addFront(DlNode node) {
            // adding node to front
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public DlNode removeTail() {
            DlNode tailPrev = tail.prev;

            removeNode(tailPrev);
            // we have to return it because we need to remove it from our main Hashmap
            return tailPrev;
        }
    }

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        // if key is there
        if (!map.containsKey(key)) {
            return -1;
        } else {
            DlNode node = map.get(key);
            update(node);
            return node.val;
        }
    }

    public void update(DlNode node) {
        // remove from current list and add to new list

        int oldCount = node.freq;
        DLList li = freqMap.get(oldCount);

        li.removeNode(node);

        if (oldCount == min && li.size == 0) {
            min++;
        }
        node.freq++;
        int newCount = node.freq;
        // add to new list
        DLList newLi = freqMap.getOrDefault(newCount, new DLList());
        newLi.addFront(node);
        freqMap.put(newCount, newLi);
    }

    public void put(int key, int value) {
        if (capacity == 0)
            return;
        // not fresh node
        if (map.containsKey(key)) {
            DlNode node = map.get(key);
            node.val = value;
            update(node);

        } else {
            // fresh if cap is full
            if (capacity == map.size()) {
                DLList minlist = freqMap.get(min);
                DlNode toRemove = minlist.removeTail();
                map.remove(toRemove.key);
            }
            DlNode newnode = new DlNode(key, value);
            min = 1;
            DLList dlist = freqMap.getOrDefault(1, new DLList());

            dlist.addFront(newnode);
            freqMap.put(1, dlist);
            map.put(key, newnode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */