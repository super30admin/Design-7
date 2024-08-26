// Time Complexity : put and get - O(1)
// Space Complexity: O(capacity) - to store node and frequency lists.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class LFUCache {
    class Node {
        int key;
        int val;
        int count;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size;

        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            // create initial doubly linked list
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            this.size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        public Node removeTail() { // remove key from the main map
            Node tailPrev = this.tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> fmap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fmap = new HashMap<>();
    }

    public void update(Node node) {
        // this moves the node from its old frequency list to new frequency list in fmap
        int oldCount = node.count;
        DLList oldList = fmap.get(oldCount);
        oldList.removeNode(node);
        if (oldCount == min && oldList.size == 0) {
            min++;
        }
        node.count++;
        DLList newList = fmap.getOrDefault(node.count, new DLList());
        // add node to new list at head position
        newList.addToHead(node);
        fmap.put(node.count, newList);
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // existing node
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            // fresh
            // if capacity of cache is full
            if (map.size() == capacity) {
                // remove the lru node from the cache list
                DLList minList = fmap.get(min);
                Node toRemove = minList.removeTail();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList newList = fmap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            fmap.put(1, newList);
            map.put(key, newNode);
        }

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */