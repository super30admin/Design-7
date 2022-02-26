package design7;

import java.util.*;

public class LFUCache {
	//Time Complexity : Mentioned above functions
  	//Space Complexity : O(n)
  	//Did this code successfully run on Leetcode : Yes
  	//Any problem you faced while coding this : No
	class Node {
        int key;
        int val;
        int count;
        Node next;
        Node prev;
        
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }
    
    class DLLNode {
        Node head;
        Node tail;
        int size;
        
        public DLLNode() {
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }
        
        public void addToHead(Node node) {
            Node next = head.next;
            head.next = node;
            node.next = next;
            next.prev = node;
            node.prev = head;
            size++;
        }
        
        public Node removeLast() {
            Node last = tail.prev;
            removeNode(last);
            return last;
        }
        
        public void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }
    }
    
    int min = 0;
    int capacity;
    Map<Integer, Node> mainMap;
    Map<Integer, DLLNode> freqMap;
    public LFUCache(int capacity) {
        mainMap = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    // O(1)
    public int get(int key) {
        if(!mainMap.containsKey(key))
            return -1;
        else {
            Node node = mainMap.get(key);
            update(node);
            return node.val;
        }
    }
    
    // O(1)
    public void put(int key, int value) {
        if(mainMap.containsKey(key)) {
            Node node = mainMap.get(key);
            node.val = value;
            update(node);
        } else {
            if(capacity == 0)
                return;
            if(mainMap.size() == capacity) {
                DLLNode oldList = freqMap.get(min);
                Node last = oldList.removeLast();
                mainMap.remove(last.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLLNode newDll = freqMap.getOrDefault(min, new DLLNode());
            newDll.addToHead(newNode);
            mainMap.put(key, newNode);
            freqMap.put(min, newDll);
        }
    }
    
    // O(1)
    public void update(Node node) {
        int freq = node.count;
        DLLNode oldDll = freqMap.get(freq);
        oldDll.removeNode(node);
        freqMap.put(freq, oldDll);
        if(freq == min && oldDll.size == 0) {
            freqMap.remove(freq);
            min++;
        }
        
        node.count++;
        DLLNode newDll = freqMap.getOrDefault(node.count, new DLLNode());
        newDll.addToHead(node);
        freqMap.put(node.count, newDll);
    }
}
