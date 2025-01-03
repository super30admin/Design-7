# Design-7

## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)
## Time Comolexity:O(1) Space:O(N)
class LFUCache {
    int capacity;
    int minFreq;
    HashMap<Integer, Node> map; // key -> Node (stores key, value, and frequency)
    HashMap<Integer, DLList> freqMap; // frequency -> DLList of nodes with the same frequency

    class Node {
        int key;
        int value;
        int frq;  // frequency of the node
        Node next, prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frq = 1;  // default frequency is 1
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size;

        public DLList() {
            this.head = new Node(-1, -1);  // dummy head node
            this.tail = new Node(-1, -1);  // dummy tail node
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }

        // Add node to the head of the list (most recently used for this frequency)
        public void addNodeToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        // Remove a node from the list
        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
            this.size--;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFreq = 1; // initial minimum frequency is 1
    }

    // Helper function to update the frequency of a node
    private void update(Node node) {
        int oldFreq = node.frq;
        DLList oldFrqList = freqMap.get(oldFreq);
        oldFrqList.removeNode(node);

        if (oldFreq == minFreq && oldFrqList.size == 0) {
            minFreq++;  // increase minFreq when the old frequency list is empty
        }

        // Increment the frequency of the node
        int newFrq = oldFreq + 1;
        node.frq = newFrq;

        // Get the new frequency list, or create one if it doesn't exist
        DLList newFrqList = freqMap.getOrDefault(newFrq, new DLList());
        newFrqList.addNodeToHead(node);
        freqMap.put(newFrq, newFrqList);
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        
        // Get the node and update its frequency
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        // Case 1: If the key already exists, update its value and frequency
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        } else {
            // Case 2: If the cache is full, evict the least frequently used node
            if (map.size() == capacity) {
                DLList minFreqList = freqMap.get(minFreq);
                Node toRemove = minFreqList.tail.prev;
                minFreqList.removeNode(toRemove);
                map.remove(toRemove.key);
            }

            // Case 3: Insert a new key-value pair
            Node newNode = new Node(key, value);
            minFreq = 1;  // reset minFreq to 1 since the new node will have frequency 1
            DLList minFreqList = freqMap.getOrDefault(minFreq, new DLList());
            minFreqList.addNodeToHead(newNode);
            freqMap.put(minFreq, minFreqList);
            map.put(key, newNode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

## Problem2 H-Index (https://leetcode.com/problems/h-index/)
## Time Complexity:O(N) Space Complexity:O(1)
class Solution {

    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        int count = 0; 

        for (int i = n - 1; i >= 0; i--) { 
            if (citations[i] >= count + 1) {
                count++; 
            } else {
                break; 
            }
        }
        return count; 
    }
}

## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

