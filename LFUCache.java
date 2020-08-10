// Time Complexity : For both get & put operation: O(1)
// Space Complexity : NA
// Did this code successfully run on Leetcode (460): Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class LFUCache {
    
    class Node {
        Node prev; Node next;
        int key; int val; int count;
        
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }
    
    class DLList {
        Node head; Node tail;
        int size;
        
        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        
        private void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
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
    
    HashMap<Integer, DLList> frequency;
    HashMap<Integer, Node> map;
    int capacity; int min;
    
    public LFUCache(int capacity) {
        this.frequency = new HashMap<>();
        this.map = new HashMap<>();
        this.capacity = capacity;
    }
    
    
    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        else {
            if (capacity == 0) return;
            // capacity is full
            if (map.size() == capacity) {
                DLList minList = frequency.get(min);
                Node toBeRemoved = minList.removeLast();
                map.remove(toBeRemoved.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList minList = frequency.getOrDefault(min, new DLList());
            minList.addToHead(newNode);
            frequency.put(min, minList);
            map.put(key, newNode);
        }
    }
    
    private void update(Node node) {
        DLList oldList = frequency.get(node.count);
        oldList.removeNode(node);
        // if oldList freq is equal to global min freq  and it has no size now
        if (node.count == min && oldList.size == 0) min++;
        node.count++;
        DLList newList = frequency.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        frequency.put(node.count, newList);
        map.put(node.key, node);
    }
}