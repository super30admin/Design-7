package week10.day4;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    class Node {
        int key;
        int val;
        int count;
        Node prev;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLL {
        Node head;
        Node tail;
        int size;

        public DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = tail;
            this.tail.prev = head;
            this.size = 0;
        }

        private void addToHead(Node n) {
            n.next = head.next;
            n.prev = head;
            head.next = n;
            n.next.prev = n;
            size++; // don't forget
        }

        private void removeNode(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            // optional
            n.next = null;
            n.prev = null;
            size--; // don't forget
        }

        private Node removeTail() { // when capacity is full, get min and remove tail node from min frequency
                                    // list(map2)
            Node nodeToRemove = tail.prev; // last node before tail
            removeNode(nodeToRemove); // call remove utility method
            return nodeToRemove; // return last node. This will be used to remove the node from map1
        }
    }

    private int capacity;
    private int min;
    private Map<Integer, Node> map;
    private Map<Integer, DLL> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.min = 0;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            update(n);
            return n.val;
        }
        return -1;                  // if key not present return -1
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) { // existing node
            Node n = map.get(key);
            n.val = value;
            update(n);
        } else { // fresh node
            if (map.size() == capacity) {         // capacity full
                DLL minDLL = freqMap.get(min);  //remove LFU node from frequency map using min. Dont remove it from map1, as it might contain multiple nodes with same min value
                Node n = minDLL.removeTail();   // get the removed node and use it's key to remove it from map1
                map.remove(n.key);
            }
            // capacity not full, full
            Node newNode = new Node(key, value);
            min = 1;                            //min=1 as it's a new node

            DLL newDLL = freqMap.getOrDefault(1, new DLL());
            newDLL.addToHead(newNode);
            
            freqMap.put(1, newDLL);             // add new node to frequency map
            map.put(key, newNode);              // add new node to map1
        }

    }

    public void update(Node n) {
        // remove from old DLL
        int old_frequency = n.count;            // (1,1,2) old frequency = 2
        DLL old_dll = freqMap.get(old_frequency);   // get the list belonging to f = 2 in map 2
        old_dll.removeNode(n);                  // remove (1,1,2) from DLL

        if (old_frequency == min && old_dll.size == 0) { // if we removed ode from min list
            min++;                                          // min = 2 to 3
        }
        n.count++; // (1,1,3)

        // add Node to new frequency
        DLL newDLL = freqMap.getOrDefault(n.count, new DLL()); // get the list belonging to f=3 in map 2 or create new
                                                               // list
        newDLL.addToHead(n);

        freqMap.put(n.count, newDLL); // add new DLL to frequency map if it's newly created
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */