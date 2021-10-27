//TC: O(1), adding and removing nodes from the end, and lookup time is constant for hashmap
//SC: bounded by the capacity
class LFUCache {
    class Node {
        int key, value, count;
        Node next, prev;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            count = 1;
        }
    }
    class DLL {
        Node head, tail;
        int size;
        public DLL() {
            //add dummy node to head and tail
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            //DLL is empty
            head.next = tail;
            tail.prev = head;
        }
        //add a new node to the head of doubly linked list
        public void addToHead(Node node){
            node.next = head.next;
            node.next.prev = node;
            node.prev = head;
            head.next = node;
            size++;
        }
        //remove node from DLL
        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        //remove node from end
        public Node removeFromEnd() {
            Node lastNode = tail.prev;
            removeNode(tail.prev);
            return lastNode;
        }
    }
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freqMap;
    int capacity, min;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        //case 1 node exists
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
            return;
        }
        if(capacity == 0) return;
        if(capacity == map.size()) {
            DLL oldlist = freqMap.get(min);
            Node lastNode = oldlist.removeFromEnd();
            map.remove(lastNode.key);
        }
        Node newnode = new Node(key, value);
        map.put(key, newnode);
        min = 1;
        DLL oldlist = freqMap.getOrDefault(min, new DLL());
        oldlist.addToHead(newnode);
        freqMap.put(min, oldlist);
    }
    public void update(Node node){
        int frequency = node.count;
        node.count++;
        DLL oldlist = freqMap.get(frequency);
        oldlist.removeNode(node);
        if(min == frequency && oldlist.size == 0) {
            min++;
        }
        DLL newlist = freqMap.getOrDefault(node.count, new DLL());
        newlist.addToHead(node);
        freqMap.put(node.count, newlist);
    }
}