class LFUCache {

    class Node {
        int key, value;
        int freq;
        Node prev, next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DLList {
        Node head, tail;
        int size;
        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            size = 0;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size += 1;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size -= 1;
        }

        private Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }
        if(capacity == 0) {
            return;
        }
        if(capacity == map.size()) {
            DLList oldList = freqMap.get(min);
            Node lastNode = oldList.removeLastNode();
            map.remove(lastNode.key);
        }
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        min = 1;
        DLList newList = freqMap.getOrDefault(1, new DLList());
        newList.addToHead(newNode);
        freqMap.put(min, newList);
    }

    private void update(Node node) {
        DLList oldList = freqMap.get(node.freq);
        oldList.removeNode(node);
        if(node.freq == min && oldList.size == 0) {
            min++;
        }
        node.freq++;
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        newList.addToHead(node);
        freqMap.put(node.freq, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
