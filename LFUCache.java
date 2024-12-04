//Leetcode Problem 460: LFU Cache
//T.C: O(1) - Constant time lookup via HashMap(s) :: S.C: O(n) - O(n+n) = O(2n) : where n is capacity of LFU Cache

import java.util.HashMap;

public class LFUCache {

    public class Node {
        int key, value, frequency;
        Node next, prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    public class DLList {
        Node head, tail;
        int size;

        public DLList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        public Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);

            return lastNode;
        }
    }

    int capacity, min;
    HashMap<Integer, Node> keyMap;
    HashMap<Integer, DLList> frequencyMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.min = 1;
        keyMap = new HashMap<>();
        frequencyMap = new HashMap<>();
    }
    
    public int get(int key) {
        if (!keyMap.containsKey(key)) return -1;
        
        Node oldNode = keyMap.get(key);

        updateFrequency(oldNode);

        return oldNode.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyMap.containsKey(key)) {
            Node oldNode = keyMap.get(key);
            updateFrequency(oldNode);
            oldNode.value = value;
            return;         
        }

        if (capacity == keyMap.size()) {
            DLList oldList = frequencyMap.get(min);
            Node oldNode = oldList.removeLastNode();
            keyMap.remove(oldNode.key);
        }

        min = 1;
        Node newNode = new Node(key, value);            
        keyMap.put(key, newNode);            
        DLList newList = frequencyMap.getOrDefault(min, new DLList());
        newList.addToHead(newNode);
        frequencyMap.put(min, newList);
    }

    public void updateFrequency(Node node) {
        //Retrieve the DLList where the node is present
        DLList oldList = frequencyMap.get(node.frequency);
        //Remove this node from the current DLList (which is mapped to the old frequency)
        oldList.removeNode(node);
        //Check if the frequency key is the min and the size of the oldList has reduced to 0 (in order to maintain the min value)
        if (min == node.frequency && oldList.size == 0) {
            min++;
        }
        //Increase the frequency of the node and add it to the new frequency key in the map
        node.frequency++;
        //Retrieve the new frequency key's DLList or create one if not present
        DLList newList = frequencyMap.getOrDefault(node.frequency, new DLList());
        //Add the removed node from the previous frequency key to the Head of the DLList
        newList.addToHead(node);
        //Add this list back to the updated frequency key
        frequencyMap.put(node.frequency, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */