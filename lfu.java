// Time Complexity : 0(1)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class LFUCache {

    int capacity;
    Map<Integer, Node> nodeMap;
    Map<Integer, DLList> freqMap;
    int min;


    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public class Node{
        int key;
        int val;
        int freq;
        Node prev;
        Node next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }

    public class DLList{
        Node head;
        Node tail;
        int size;
        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        private void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            node.next.prev = node;
            head.next = node;
            size++;
        }

        private void removeNode(Node node) {
            node.prev.next= node.next;
            node.next.prev = node.prev;
            size--;
        }
        private Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    public int get(int key) {

        if(nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            update(node);
            return node.val;
        }

        return -1;

    }

    public void put(int key, int value) {
        if(nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            update(node);
            node.val = value;
            return;
        }

        if(capacity == 0)
            return;

        if(nodeMap.size() == capacity) {
            DLList oldList = freqMap.get(min);
            Node lastNode = oldList.removeLastNode();
            nodeMap.remove(lastNode.key);
        }

        Node newNode = new Node(key, value);
        nodeMap.put(key, newNode);
        min =1;
        DLList newList = freqMap.getOrDefault(min, new DLList());
        newList.addToHead(newNode);
        freqMap.put(min, newList);
    }

    private void update(Node node) {
        DLList oldList = freqMap.get(node.freq);
        oldList.removeNode(node);
        if(min == node.freq && oldList.size == 0)
            min++;

        node.freq++;
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        newList.addToHead(node);
        freqMap.put(node.freq, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */