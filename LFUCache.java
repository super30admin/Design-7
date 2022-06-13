// Time Complexity: O(1).
// Space Complexity: O(n) where n is the capacity.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class LFUCache {
    class Node {
        int key, value, freq;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
        Node prev, next;
    }
    
    class DoubleList {
        Node head, tail;
        int size;
        
        public DoubleList() {
            head = new Node(-1, -1);    // Dummy head
            tail = new Node(-1, -1);    // Dummy tail
            head.next = tail;
            tail.prev = head;
        }
        
        private void addToHead(Node node) {
            node.next = head.next;      //dummy's next.
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        private void remove(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLRU() {
            Node lastNode = tail.prev;
            remove(lastNode);
            return lastNode;        // To use in the 'map' hashmap.
        }
    }
    
    HashMap<Integer, Node> map;             // [key, Node]
    HashMap<Integer, DoubleList> freqMap;   // [Node's frequency, list]
    int capacity, min;      // min for tracking the minimum freq node.

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    private void update(Node node) {
        DoubleList oldList = freqMap.get(node.freq);
        oldList.remove(node);       // oldList is never null.
        if(min == node.freq && oldList.size == 0) {     
            min++;          // Prev min key in freqMap becomes null.
        }
        node.freq++;
        DoubleList newList = freqMap.getOrDefault(node.freq, new DoubleList());     // Get existing list if exists.
        newList.addToHead(node);                // Recent nodes are added to head of list.
        freqMap.put(node.freq, newList);        // Add to new freq key the updated list (value).
    }
    
    public void put(int key, int value) {
        if(capacity == 0) {
            return;
        }
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
            return;             
        }
        if(capacity == map.size()) {
            DoubleList oldList = freqMap.get(min);
            Node lastNode = oldList.removeLRU();
            map.remove(lastNode.key);       // inbuilt.
        }
        // Node appears for first time.
        Node node = new Node(key, value);
        min = 1;
        DoubleList newList = freqMap.getOrDefault(1, new DoubleList());    // min is 1.
        newList.addToHead(node);    // Update list if exists.
        map.put(key, node);         // Update map.
        freqMap.put(min, newList);  // Update freqMap.
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */