package Dec17;

import java.util.HashMap;
import java.util.Map;

class LFUCache {

    /*
    Time complexity: O(1). Update, put, get : all operations are O(1)
    */
    
    class Node {
        int key; int val;
        int count;
        Node prev; Node next;
        private Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }   
    }
    
    class DoublyLinkedList {
        
        // we need to maintain size to track minimum
        Node head, tail;
        int size;
        private DoublyLinkedList() {
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        
         private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        private Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }   
    }
    
    Map<Integer, Node> map;
    Map<Integer, DoublyLinkedList> freqMap;
    int min; 
    int capacity;
    
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int k) {
        if (map.containsKey(k)) {
            
            Node node = map.get(k);
            // increase freq
            // place the node with updated freq in the DLL
            // do all this in a separate function
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int k, int v) {
        // Case 1: map already contains key
        if (map.containsKey(k)) {
            Node node = map.get(k);
            node.val = v;
            update(node);
        } 
        // Case 2: map doesnt already contain key
        else {
        // check if capacity is full, then remove least freq and (least recently used in case of tie)
            if (capacity == 0) { // edge case for Leetcode
                return;
            }
            if (capacity == map.size()) {
                DoublyLinkedList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(k, v);
            min = 1;
            DoublyLinkedList minList = freqMap.getOrDefault(min, new DoublyLinkedList());
            minList.addToHead(newNode);
            freqMap.put(1, minList);
            map.put(k, newNode);      
        }
        
    }
    
    private void update(Node node) {
        int cnt = node.count;
        DoublyLinkedList oldList = freqMap.get(cnt);  // from freq map
        oldList.removeNode(node);
        if (cnt == min && oldList.size == 0) {
            min++;
        }
        node.count++;
        DoublyLinkedList newList = freqMap.getOrDefault(min, new DoublyLinkedList());
        newList.addToHead(node);
        freqMap.put(node.count, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */