// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach

class LFUCache {
    class Node {
        int key;
        int value;
        int cnt;
        Node prev;
        Node next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.cnt = 1;
        }
    }
    
    class DLList {
        Node head;
        Node tail;
        int size;
        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
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

    HashMap<Integer, Node> map; // LRU
    HashMap<Integer, DLList> freqMap; //
    int capacity;
    int min;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        // existing node
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        } else {
            // fresh node
            if(capacity == 0)
                return;
            if(capacity == map.size()) { // full
                // remove the least frequently Node
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList newList = freqMap.getOrDefault(1, new DLList());
            newList.addToHead(newNode);
            freqMap.put(1, newList);
            map.put(key, newNode);
        }
    }
    private void update(Node node) {
        // basically move it from prev freq dllist to new freq dllist 
        DLList prevList = freqMap.get(node.cnt);
        prevList.removeNode(node);
        // but I have to check if the prev freqList is min freqList and has become empty
        if(min == node.cnt && prevList.size == 0)
            min++;
        node.cnt++;
        DLList newList = freqMap.getOrDefault(node.cnt, new DLList());
        newList.addToHead(node);
        freqMap.put(node.cnt, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */