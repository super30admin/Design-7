// Time: O(1) | Space: O(No of incoming Nodes + Max of frequencies * nodes)

class LFUCache {
    // basic node, which constitutes of prev next pointers to remove from a certain freq list in O(1)
    static class Node {
        Node prev;
        Node next;
        int key;
        int val;
        int frequency;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.frequency = 1;
        }
    }
    // maintaing a doubly linked list at every frequency in the frequency map
    // the order of the nodes will be in LRU manner
    static class DDLList {
        Node head;
        Node tail;
        int size;
        public DDLList() {
            this.head = new Node(-1,-1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }
        public void addToHead(Node node) {
            node.prev = this.head;
            node.next = this.head.next;
            this.head.next.prev = node;
            this.head.next = node;
            this.size++;
        }
        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        }
    }
    // key map to access the respective node in O(1)
    // upon its frequency saved, we find out the node in the respective freqency map
    Map<Integer, Node> keyMap;
    Map<Integer, DDLList> freqMap;
    int min;
    int capacity;
    public LFUCache(int capacity) {
        keyMap = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
        this.min = Integer.MAX_VALUE;
    }
    // when we want to update a certain node, we are doing the below
    // we first find the node in keyMap,
    // we then find its respective frequency list in freqMap
    // we increase the frequency of current node by 1
    // if there's no node left in the previous frequency count after removing the current
    // and if the min set as current node's prev frequency, we update it by 1 too
    // then we find the frequency list for the incremented frequency in freqMap
    // add it to the head of list as the earlier nodes will be least recently used in that frequency level
    public Node getNode(int key) {
        Node existingNode = keyMap.get(key);
        int currFreq = existingNode.frequency;
        DDLList list = freqMap.get(currFreq);
        list.removeNode(existingNode);
        if(list.size == 0 && currFreq == min) {
            min++;
        }

        currFreq++;
        existingNode.frequency = currFreq;
        DDLList newList = freqMap.getOrDefault(currFreq, new DDLList());
        newList.addToHead(existingNode);

        freqMap.put(currFreq, newList);
        return existingNode;
    }

    public int get(int key) {
        if(!keyMap.containsKey(key)) return -1;
        return getNode(key).val;
    }

    public void put(int key, int value) {
        if(capacity <= 0) return;
        // if we have the node already present
        // just update its value
        if(keyMap.containsKey(key)) {
            getNode(key).val = value;
        } else {
            // if fresh node arrived/
            // and capacity is full
            // remove the least frequently and recently used node from the freqMap and keyMap
            if(this.capacity == keyMap.size()) {
                DDLList list = freqMap.get(min);
                if(list.size > 0) {
                    Node tail = list.tail.prev;

                    keyMap.remove(tail.key);                                                        list.removeNode(tail);
                }
            }
            // then add the freshNode with frequency 1
            Node newNode = new Node(key, value);
            min = 1;
            keyMap.put(key, newNode);
            DDLList newList = freqMap.getOrDefault(1, new DDLList());
            freqMap.put(1, newList);
            newList.addToHead(newNode);
        }
    }


}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */