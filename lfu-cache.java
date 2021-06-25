// Time - O(1) for all operations, Space - O(N)
class LFUCache {

    class Node {
        Node next, prev;
        int key, value, freq;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }
    
    class DList {
        Node head, tail;
        int size;
        
        private DList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        
        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }
        
        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
        
    }
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DList> freqMap;
    
    int capacity, min;
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        }
        else {
            if(capacity == 0) {
                return;
            }
            if(capacity == map.size()) {
                DList removeList = freqMap.get(min);
                Node removedNode = removeList.removeLast();
                map.remove(removedNode.key);               
            }
            Node newNode = new Node(key, value);
            
            min = 1;
            map.put(key, newNode);
            DList newList = freqMap.getOrDefault(min, new DList());
            newList.addToHead(newNode);
            freqMap.put(min, newList);
        }
    }
    
    private void update(Node node) {
        int count = node.freq;
        node.freq++;
        DList oldList = freqMap.get(count);
        oldList.removeNode(node);
        if(min == count && oldList.size == 0) min++;
        DList newList = freqMap.getOrDefault(node.freq, new DList());
        newList.addToHead(node);
        freqMap.put(node.freq, newList);
    }
}
