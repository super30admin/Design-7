// Approach :
// 1. A map with key and value as Node (key , val , freq) - for regular get operations
// 2. For all put/get operations need to track the count frequency as well. So another map with key and value as DLL Node - Since order of the nodes matter when there are same freq 
// Time :
// Space : 
class LFUCache {
    class Node { // Node structure
        int key, val;
        int freq;
        Node prev, next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1; // freq for a new node
        }
    }

    class DLList {
        Node head, tail;
        int size;

        public DLList() {
            // dummy head and tail
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;

        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
            this.size--;
        }
    }

    HashMap<Integer, Node> map; // key: node
    // order of the nodes matter when there are same freq
    HashMap<Integer, DLList> freqMap; // freq: nodes
    int minFreq; // minimum freqMap in freqMap
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    // to update node in freqMap
    public void update(Node node) {
        int oldFreq = node.freq;
        DLList oldFreqList = freqMap.get(oldFreq);
        oldFreqList.removeNode(node);
        if (oldFreq == minFreq && oldFreqList.size == 0) {
            minFreq++; // single node in freq 1 is removed, so new minFreq becomes 1++ -> 2
        }
        int newFreq = oldFreq + 1;
        node.freq = newFreq; // update freq of the node
        DLList newFreqList = freqMap.getOrDefault(newFreq, new DLList()); // newFreq:1
        newFreqList.addToHead(node);
        freqMap.put(newFreq, newFreqList);
    }

    public int get(int key) { //TC: O(1)
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        update(node); // put on existing node
        return node.val;
    }

    public void put(int key, int value) { //TC: O(1)
        // key is already in cache
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            if (map.size() == capacity) { // if capacity is full, create space -> add node
                DLList minFreqList = freqMap.get(minFreq);
                Node nodeToRemove = minFreqList.tail.prev; // remove from tail
                minFreqList.removeNode(nodeToRemove);
                map.remove(nodeToRemove.key);
            }
            Node newNode = new Node(key, value); // fresh node
            minFreq = 1;
            DLList minFreqList = freqMap.getOrDefault(minFreq, new DLList());
            minFreqList.addToHead(newNode);
            freqMap.put(minFreq, minFreqList);
            map.put(key, newNode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */