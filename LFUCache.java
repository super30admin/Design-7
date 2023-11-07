//faced issue while running this code

class LFUCache {
    class Node {
        int key, value;
        int freq;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }
    class DLList {
        Node prev, next;
        Node head, tail;
        int size;
        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            size = 0;
        }
        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        public Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }
    int capacity;
    HashMap<Integer, DLList> freqMap;
    HashMap<Integer, Node> map;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        freqMap = new HashMap<>();
        map = new HashMap<>();
        
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
        if(capacity == map.size()) {
            DLList oldlist = freqMap.get(min);
            Node lastNode = oldlist.removeLastNode();
            map.remove(lastNode.key);
        }
        Node newnode = new Node(key,value);
        map.put(key, newnode);
        min = 1;
        DLList newlist = freqMap.getOrDefault(1, new DLList());
        newlist.addToHead(newnode);
        
    }
    private void update(Node node) {
        int freq = node.freq;
        DLList oldlist = freqMap.get(freq);
        oldlist.removeNode(Node);
        if(oldlist.size == 0) {
            min++;

        }
        node.freq++;
        DLList newlist = freqMap.getOrDefault(node.freq, new DLList());
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */