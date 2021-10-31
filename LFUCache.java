package Design7;

import java.util.HashMap;

public class LFUCache {
    class Node {
        int  key, val, freq;
        Node next, prev;
        Node(int key, int val, int freq){
            this.key = key;
            this.val = val;
            this.freq = freq;
        }
    }
    class DLinkedList {
        Node head;
        Node tail;
        int size = 0;
        DLinkedList(){
            head = new Node(-1, -1, 1);
            tail = new Node(-1, -1, 1);
            head.next = tail;
            tail.prev = head;
        }

        public boolean isEmpty(){
            return size == 0;
        }
    }
    HashMap<Integer, Node> keyMap = new HashMap<>();
    HashMap<Integer, DLinkedList> freqMap = new HashMap<>();
    int min = Integer.MAX_VALUE;
    int cap = 0;
    int size = 0;
    public LFUCache(int capacity) {
        this.cap = capacity;
    }

    private void remove(Node node){
        //remove node
        int cnt = node.freq;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;

        //reduce size of dll
        DLinkedList dll = freqMap.get(cnt);
        dll.size--;

        //if dll was at min and node was only element, update min
        if(node.freq == min && dll.isEmpty())
            min++;
        //remove from map if node was only element
        if(dll.isEmpty())
            freqMap.remove(cnt);
    }
    private void removeLfu(){
        //remove last element from lfu
        DLinkedList dll = freqMap.get(min);
        Node node = dll.tail.prev;
        dll.tail.prev = dll.tail.prev.prev;
        dll.tail.prev.next = dll.tail;
        dll.size--;

        //if only element, remove from freq map
        if(dll.isEmpty()){
            freqMap.remove(min);
        }
        //remove from key map
        keyMap.remove(node.key);
    }
    private void add(Node node){
        int cnt = node.freq;
        DLinkedList dll;
        if(freqMap.containsKey(cnt)){
            dll = freqMap.get(cnt);
        } else {
            dll = new DLinkedList();
        }
        node.next = dll.head.next;
        node.prev = dll.head;
        dll.head.next = node;
        node.next.prev = node;
        dll.size++;
        freqMap.put(cnt, dll);
    }

    public int get(int key) {
        if(cap==0)
            return -1;
        if(keyMap.containsKey(key)){
            Node node = keyMap.get(key);
            remove(node);
            node.freq++;
            add(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(cap == 0)
            return;
        if(keyMap.containsKey(key)){
            Node node = keyMap.get(key);
            node.val = value;
            remove(node);
            node.freq++;
            add(node);

        } else {
            Node node = new Node(key, value, 1);
            keyMap.put(key, node);
            add(node);
            size++;
            if(size > cap){
                removeLfu();
            }
            min = 1;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
