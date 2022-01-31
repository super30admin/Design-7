class LFUCache {

    class Node {
        Node prev, next;
        int key, val;
        int count;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLList {
        Node head, tail;
        int size;

        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        // add the new node next to the head
        public void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        public Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;             // map of <key-address of Node>
    HashMap<Integer, DLList> freqMap;       // map of <frequency-Dll>
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();

    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;

        Node node = map.get(key);
        update(node);
        return node.val;
    }

    public void put(int key, int value) {
        // case 1: Key is present and you update its value
        // case 2: Key is not present and you want to create it
        // case 3: If you reach the capacity

        if(capacity == 0) return;

        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        else {
            if(capacity == map.size()) {
                DLList li = freqMap.get(min);
                Node toRemove = li.removeLast();
                map.remove(toRemove.key);
            }

            Node nodeToAdd = new Node(key, value);
            min = 1;
            DLList newList = freqMap.getOrDefault(1, new DLList());
            newList.addToHead(nodeToAdd);
            freqMap.put(1, newList);
            map.put(key, nodeToAdd);
        }
    }

    private void update(Node node) {
        DLList prevList = freqMap.get(node.count);
        prevList.removeNode(node);
        if(prevList.size == 0 && min == node.count) min++;
        node.count++;
        DLList newList = freqMap.getOrDefault(node.count, new DLList());
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