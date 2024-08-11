import java.util.HashMap;
import java.util.Map;

/****
 Using doubly linked list and 2 hashmaps (1st -> key to node mapping, 2nd -> freq to doubly linked list mapping)
 TC - O(1)
 SC - O(n)
 */
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

        //add to doubly linked list
        private void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            node.next.prev = node;
            head.next = node;
            size++;
        }

        //delete from doubly linked list
        private void removeNode(Node node) {
            node.prev.next= node.next;
            node.next.prev = node.prev;
            size--;
        }

        //delete last node from doubly linked list
        private Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    public int get(int key) {

        if(nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            //update its frequency
            update(node);
            return node.val;
        }

        return -1;

    }

    public void put(int key, int value) {
        //check if key present in nodeMap
        if(nodeMap.containsKey(key)) {
            //update freqMap
            Node node = nodeMap.get(key);
            update(node);
            //update map
            node.val = value;
            return;
        }

        if(capacity == 0)
            return;

        if(nodeMap.size() == capacity) {
            //evict least frequently used node
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