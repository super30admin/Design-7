/*
* Did this code successfully run on Leetcode : YES
* 
* Any problem you faced while coding this : NO
* 
* Time Complexity: all operations except constructor: O(1)
* 
* Space Complexity: O(1)
* 
*/

import java.util.HashMap;

class LFUCache {
    class Node {
        int key;
        int value;
        int freq;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    };

    class DLList {
        int size;
        Node head;
        Node tail;

        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.next = this.head;
        }

        public void addNode(Node node) {
            node.next = this.head.next;
            node.prev = this.head;
            this.head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        }

        public Node getLastNode() {
            return this.tail.prev;
        }
    };

    int cacheCapacity;

    int minFreq;

    HashMap<Integer, Node> hmap;

    HashMap<Integer, DLList> freqMap;

    public LFUCache(int capacity) {
        this.cacheCapacity = capacity;

        this.hmap = new HashMap<>();

        this.freqMap = new HashMap<>();
    }

    private void updateCache(Node node) {
        DLList oldDLList = freqMap.get(node.freq);

        oldDLList.removeNode(node);

        if (node.freq == minFreq && oldDLList.size == 0) {
            minFreq++;
        }

        node.freq++;

        DLList newDLList = freqMap.getOrDefault(node.freq, new DLList());

        newDLList.addNode(node);

        freqMap.put(node.freq, newDLList);
    }

    private void deleteLeastFrequentlyUsed() {
        DLList list = freqMap.get(minFreq);

        Node lastNode = list.getLastNode();

        list.removeNode(lastNode);

        hmap.remove(lastNode.key);
    }

    public int get(int key) {
        if (!hmap.containsKey(key)) {
            return -1;
        }

        Node node = hmap.get(key);

        updateCache(node);

        return node.value;
    }

    public void put(int key, int value) {
        if (hmap.containsKey(key)) {
            Node node = hmap.get(key);
            node.value = value;

            updateCache(node);
        } else {
            Node newNode = new Node(key, value);

            // check if cache is full
            if (hmap.size() == cacheCapacity) {
                deleteLeastFrequentlyUsed();
            }

            hmap.put(key, newNode);
            DLList list = freqMap.getOrDefault(1, new DLList());
            list.addNode(newNode);
            freqMap.put(1, list);
            minFreq = 1;
            // System.out.println("minFreq reset to:"+minFreq);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
