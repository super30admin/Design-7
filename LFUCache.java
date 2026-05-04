// O(1) time, O(capacity) space

import java.util.*;

class LFUCache {
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int minFrq;

    class Node{
        int key;
        int value;
        int frq;
        Node next, prev;

        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.frq = 1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;

        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }

        private void insertNodeToHead(Node node){
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
            this.size--;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    private void update(Node node){
        int oldFrq = node.frq;
        DLList oldFrqList = freqMap.get(oldFrq);
        oldFrqList.removeNode(node);

        if (oldFrq == minFrq && oldFrqList.size == 0) {
            minFrq++;
        }

        int newFrq = oldFrq + 1;
        node.frq = newFrq;
        DLList newFrqList = freqMap.getOrDefault(newFrq, new DLList());
        newFrqList.insertNodeToHead(node);
        freqMap.put(newFrq, newFrqList);
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        }
        else {
            if (map.size() == capacity) {
                DLList minFrqList = freqMap.get(minFrq);
                Node lastNode = minFrqList.tail.prev;
                minFrqList.removeNode(lastNode);
                map.remove(lastNode.key);
            }
            minFrq = 1;
            Node newNode = new Node(key, value);
            map.put(key, newNode);

            DLList minFrqList = freqMap.getOrDefault(minFrq, new DLList());
            minFrqList.insertNodeToHead(newNode);
            freqMap.put(minFrq, minFrqList);
        }
    }
}
