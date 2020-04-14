// Time Complexity : O(1) for both put and get operations
// Space Complexity : O(c) c-> capacity, two hashmaps used and one doubly linked list
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Approach:
// For performing both get and put operations in O(1), we need to have efficient datastructures for accessing any element. Therefore, a hashmap is used for maintaining the key and its respective node structure.
// While performing put, we need to find the exact position wherein the new node is supposed to be inserted.
// Since both frequency and position is required, a frequency map is created for efficient retrieval and insertion of the new node. frequency map is created by considering frequency as key and doubly linked list as value.

class LFUCache {
    
    Map<Integer, Node> map;
    Map<Integer, NodeList> freqMap;
    
    int capacity;
    int minFreq; // to keep track of the least frequency list
    
    
    // Node structure
    class Node {
        int key;
        int val;
        int freq;
        Node next;
        Node prev;
        
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 0;
        }
        
    }
    
    // Initial node list with dummy head and tail
    class NodeList {
        Node head, tail;
        int size; // to capture current length of list
        
        public NodeList() {
            head = new Node(0,0);
            tail = new Node(0,0);
    
            head.next = tail;
            tail.prev = head;
            
            size = 0;
        }
        
        public void addAtHead(Node currNode) {
            currNode.next = head.next;
            currNode.prev = head;
            head.next.prev = currNode;
            head.next = currNode;
            size++;
        }
        
        public void removeNode(Node currNode) {
            if(map.containsKey(currNode.key)) {
            currNode.next.prev = currNode.prev;
            currNode.prev.next = currNode.next;
            size--;}
        }
        
        public Node removeLastNode() { // returning node to make consistent delete in map as well
            Node last = tail.prev;
            removeNode(last);
            return last;
        }
    }
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        
        map = new HashMap<>();
        freqMap = new HashMap<>();
        minFreq = Integer.MAX_VALUE;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) //check if map contains the key or not
            return -1;
        Node currNode = map.get(key);
        ++currNode.freq; // increment frequency
        updateFreqMap(currNode); // add current node in the nodelist of incremented frequency
        return currNode.val;
    }
    
    public void updateFreqMap(Node currNode) {
        NodeList currList = freqMap.get(currNode.freq);
        int chkKey = currNode.freq-1; // to check if original frequency still contains nodes
        NodeList origList = freqMap.get(chkKey);
        if(origList != null ) {
            origList.removeNode(currNode); // remove current node from old frequency list
            if(origList.size == 0) {
                freqMap.remove(chkKey); // remove key if no more nodes for original freq exists
                if(chkKey == minFreq) {
                    minFreq++;
                }
            }
        }
        if(currList == null) {
            currList = new NodeList();
            freqMap.put(currNode.freq, currList);
        }
            
        // add node in new frequency list
        currList.addAtHead(currNode);
    }
    
    public void put(int key, int value) {
        if(capacity == 0) return;
        Node currNode = new Node(key, value);
        Node chkNode = map.get(key);
        if(chkNode != null) {
            chkNode.val = value;
            ++chkNode.freq;
            updateFreqMap(chkNode);
            return;
        }
        
        if(map.size() == capacity) {
            // evict last node from least frequency value
            // get current list
            NodeList currList = freqMap.get(minFreq);
            Node last = currList.removeLastNode();
            map.remove(last.key);
        }
        
        NodeList newList = freqMap.get(minFreq);
        if(newList == null) {
            newList = new NodeList();
            freqMap.put(1, newList);
            
        }
        ++currNode.freq;
        updateFreqMap(currNode);
        map.put(key, currNode);
        minFreq = 1;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
