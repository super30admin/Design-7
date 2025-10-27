// Time Complexity : O(1) for get and put
// Space Complexity :O(capacity) for storing nodes and frequency lists.
// Did this code successfully run on Leetcode : Yes 
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
/*
Using two hash maps one of type integer and node to fecth the node using key in O(1)
ANd other map to store the frequency list of type integer and Doubly linked list so that insertion and removal of node takes place in O(1)
Get operation - if the given key is in first map return its value and update its frequency
Put operation - If updating the existing key value then simply update the node and update its frequency, if inserting a new key then check for the capacity
If capacity is not full insert new node with the frequency 1, if capacity is full get the node with minimum freq from freq map and remove it from freq map and frist map as well\
update freq operation - Get the original freq from main map and remove that node from freq map, if its the last node and it was the min freq then increase its freq by 1
Get the new freq, create a new list if not already present and insert the node at that freq

*/

class LFUCache {
    class Node {
        int key, value, freq;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.value = val;
            this.freq = 1;
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size;

        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void insertToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        public Node removeTail() {
            Node tailPrev = tail.prev;
            remove(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int minFreq;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public void updateFreq(Node node) {
        int oldFreq = node.freq;
        DLList freqList = freqMap.get(oldFreq); //get the list coresponding to this freq
        freqList.remove(node); //remove that node from list
        if (oldFreq == minFreq && freqList.size == 0) { //if it was the last list its freq should be increased by 1
            minFreq++;
        }
        int newFreq = oldFreq + 1;
        //update this freq in map as well
        node.freq = newFreq;
        //get the list coresponding to this new freq if not there create one
        DLList newFreqList = freqMap.getOrDefault(newFreq, new DLList());
        //inseert this node to this list
        newFreqList.insertToHead(node);
        freqMap.put(newFreq, newFreqList);
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        //map contains key
        //Get the node corresponding to that key
        //Update its freq
        Node node = map.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        if (map.containsKey(key)) {
            //node already present in map just update its value and update its freq
            Node node = map.get(key);
            node.value = value;
            updateFreq(node);
        } else {
             //if node not present in map
        //check for the capacity

        //capacity is full
        if (capacity == map.size()) {
            //Need to delete a node with minimum frequency
            DLList freqList = freqMap.get(minFreq);
            Node nodeToRemove = freqList.removeTail();
            map.remove(nodeToRemove.key);
        }
            //capacity is not full insert the node
            minFreq = 1;
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            DLList newFreqList = freqMap.getOrDefault(minFreq, new DLList());
            newFreqList.insertToHead(newNode);
            freqMap.put(minFreq, newFreqList);

        }
       

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
