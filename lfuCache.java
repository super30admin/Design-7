// Time Complexity: O(1) for both get and put operations
// Space Complexity: O(capacity) for the storage of nodes and frequency lists
class LFUCache {
    // Inner class to represent a node in the doubly linked list
    class Node {
        int key;
        int val;
        int freq; // Frequency of access
        Node next;
        Node prev;

        Node(int key, int val) {
            this.freq = 1; // Initially, the frequency is 1
            this.key = key;
            this.val = val;
        }
    }

    // Inner class to represent a doubly linked list of nodes with the same frequency
    class LinkedList {
        int size;
        Node head; // Dummy head node
        Node tail; // Dummy tail node

        LinkedList() {
            this.size = 0;
            this.head = new Node(-1, -1); // Dummy head node with placeholder values
            this.tail = new Node(-1, -1); // Dummy tail node with placeholder values
            this.head.next = tail; // Link dummy head to dummy tail
            this.tail.prev = head; // Link dummy tail to dummy head
        }

        // Remove a node from the linked list
        private void removeNode(Node node) {
            node.prev.next = node.next; // Bypass the node to remove it
            node.next.prev = node.prev;
            this.size--; // Decrement the size of the list
        }

        // Add a node to the linked list before the dummy tail
        private void addNode(Node node) {
            node.next = tail; // Link the new node to the dummy tail
            tail.prev.next = node; // Link the previous node to the new node
            node.prev = tail.prev; // Update the new node's previous link
            tail.prev = node; // Update the dummy tail's previous link
            this.size++; // Increment the size of the list
        }
    }

    HashMap<Integer, Node> keyMap; // Map from key to node
    HashMap<Integer, LinkedList> freqMap; // Map from frequency to linked list of nodes with that frequency
    int capacity;
    int min; // Minimum frequency in the cache

    public LFUCache(int capacity) {
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
        this.min = 0; // Set to 0 initially to reflect an empty cache
    }

    // Get the value associated with the key and update its frequency
    public int get(int key) {
        if (!keyMap.containsKey(key)) {
            return -1; // Key does not exist in the cache
        }

        Node node = keyMap.get(key);
        int freq = node.freq;

        LinkedList dl = freqMap.get(freq);
        dl.removeNode(node);
        
        // If the frequency list becomes empty, remove it and update `min`
        if (dl.size == 0) {
            freqMap.remove(freq);
            if (min == freq) {
                min++; // Update `min` if necessary
            }
        }

        // Increment the frequency of the node
        freq++;
        node.freq = freq;

        // Add the node to the new frequency list
        freqMap.putIfAbsent(freq, new LinkedList());
        LinkedList newList = freqMap.get(freq);
        newList.addNode(node);

        return node.val; // Return the value of the node
    }

    // Put a key-value pair into the cache
    public void put(int key, int value) {
        if (capacity == 0) {
            return; // Edge case: if the cache capacity is 0, do nothing
        }

        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            node.val = value; // Update the value of the existing key
            get(key); // Update the frequency using the get method
            return;
        }

        // If the cache is full, remove the least frequently used node
        if (keyMap.size() >= capacity) {
            LinkedList minFreqList = freqMap.get(min);
            Node nodeToRemove = minFreqList.head.next; // The first real node after the dummy head
            minFreqList.removeNode(nodeToRemove);
            keyMap.remove(nodeToRemove.key);

            // Remove the frequency list if it becomes empty
            if (minFreqList.size == 0) {
                freqMap.remove(min);
            }
        }

        // Add the new key-value pair
        Node newNode = new Node(key, value);
        keyMap.put(key, newNode);
        freqMap.putIfAbsent(1, new LinkedList());
        freqMap.get(1).addNode(newNode);
        
        // Reset `min` to 1 as a new node with freq 1 is added
        min = 1;
    }
}
