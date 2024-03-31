// TC: O(1) for all functions

// Approach: Used a key to node mapping and frequency to doubly linked list
// of nodes.

import java.util.HashMap;
import java.util.Map;

class LFUCache {
    private class Node {
        private int key;
        private int value;
        private int frequency;
        Node next;
        Node prev;

        public Node(int key, int value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    private class DLL {
        Node head;
        Node tail;

        public DLL() {
            this.head = new Node(-1, -1, -1);
            this.tail = new Node(-1, -1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addToTail(Node node) {
            node.prev = tail.prev;
            node.next = tail;
            node.prev.next = node;
            tail.prev = node;
        }

        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
        }

        public boolean isEmpty() {
            if (head.next == tail && tail.prev == head) {
                return true;
            }
            return false;
        }
    }

    private int capacity;
    private int size;
    private int minFrequency;
    private Map<Integer, Node> keyNodeMap;
    private Map<Integer, DLL> frequencyListMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 1;
        keyNodeMap = new HashMap<Integer, Node>();
        frequencyListMap = new HashMap<Integer, DLL>();
        // for frequencies 1
        frequencyListMap.put(1, new DLL());
    }

    public void update(Node node) {
        // remove from linkedlist
        DLL list = frequencyListMap.get(node.frequency);
        list.removeNode(node);

        if (minFrequency == node.frequency && list.isEmpty()) {
            minFrequency++;
        }

        node.frequency++;
        int newFrequency = node.frequency;
        // new list
        if (!frequencyListMap.containsKey(newFrequency)) {
            frequencyListMap.put(newFrequency, new DLL());
        }

        list = frequencyListMap.get(newFrequency);
        list.addToTail(node);
    }

    public int get(int key) {
        if (!keyNodeMap.containsKey(key)) {
            return -1;
        }

        Node node = keyNodeMap.get(key);
        update(node);

        return node.value;
    }

    public void put(int key, int value) {
        if (keyNodeMap.containsKey(key)) {
            Node node = keyNodeMap.get(key);
            node.value = value;
            update(node);
        } else {
            size++;

            if (capacity < size) {
                DLL list = frequencyListMap.get(minFrequency);
                Node toBeRemoved = list.head.next;
                list.removeNode(toBeRemoved);
                keyNodeMap.remove(toBeRemoved.key);
                size--;
            }

            DLL oneList = frequencyListMap.get(1);
            Node node = new Node(key, value, 1);
            keyNodeMap.put(key, node);
            oneList.addToTail(node);
            minFrequency = 1;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */