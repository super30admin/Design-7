class LFUCache {

    class Node {
        int key; 
        int value;
        int freq;
        Node next;
        Node prev;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DLL {
        Node head;
        Node tail;
        int size;
        DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }

        private void insertHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            ++size;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;  
            node.next = null;
            node.prev = null;
            size--;
        }
    }


    Map<Integer, Node> nodeMap;
    Map<Integer, DLL> countMap;
    int min;
    int capacity;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.min = 0;
        nodeMap = new HashMap<>();
        countMap = new HashMap<>();
    }
    
    public int get(int key) {
        if (!nodeMap.containsKey(key)) {
            return -1;
        }

        Node node = nodeMap.get(key);

        handleExistingKey(node);

        return node.value;  
    }

    private void handleExistingKey(Node node) {
        
        DLL dll = countMap.get(node.freq);
        dll.removeNode(node);

        if (min == node.freq && dll.size == 0) {
            min = node.freq + 1;
        }

        node.freq = node.freq + 1;
        nodeMap.put(node.key, node);
        dll = countMap.getOrDefault(node.freq, new DLL());

        dll.insertHead(node);

        countMap.put(node.freq, dll);
    }

    public void put(int key, int value) {

        if (!nodeMap.containsKey(key)) {
            if (capacity == nodeMap.size()) {
                DLL dll = countMap.get(min);
                Node lastNode = dll.tail.prev;
                dll.removeNode(lastNode);
                nodeMap.remove(lastNode.key);
            }

            Node node = new Node(key, value);
            min = 1;
            nodeMap.put(key, node);
            DLL dll = countMap.getOrDefault(node.freq, new DLL());
            dll.insertHead(node);
            countMap.put(node.freq, dll);
            return;
        }

        Node node = nodeMap.get(key);
        node.value = value;
        handleExistingKey(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */