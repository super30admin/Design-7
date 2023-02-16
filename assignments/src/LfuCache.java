// Approach: Use 2 hashmaps main map <int, node> and frequency map <int, doubly linked list>
// Time: O(1) for get and put in average case
// Space: O(n) where n = total no. of operations

import java.util.*;

class LfuCache {

    class Node {
        int key, val, count;
        Node next, prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;     // frequency
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size;
        public DLList() {
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
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
            this.size--;
        }
        public Node removeLast() {
            Node toRemove = this.tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int min, capacity;

    public LfuCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        updateNode(node);   // remove from old freq list and add it to head of new freq list
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            updateNode(node);
        }
        else {
            if (capacity == 0) return;
            if (capacity == map.size()) {
                // remove a node
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            min =1;
            // add it to freqMap
            DLList minList = freqMap.getOrDefault(min, new DLList());
            minList.addToHead(newNode);
            freqMap.put(min, minList);
        }
    }
    private void updateNode(Node node) {
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount);
        oldList.removeNode(node);
        if (oldCount == min && oldList.size == 0) min++;
        node.count++;
        int newCount = node.count;
        DLList newCountList = freqMap.getOrDefault(newCount, new DLList());
        newCountList.addToHead(node);
        freqMap.put(newCount, newCountList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */