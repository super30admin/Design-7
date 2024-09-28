// Problem 460. LFU Cache
// Time Complexity : O(1)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
class LFUCache {
    class Node {
        int key, val, cnt;
        Node prev, next;
        
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DLLList {
        Node head, tail;
        int size;
        
        public DLLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }

        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        public Node removeLast() {
            if (size > 0) {
                Node toRemove = this.tail.prev;
                removeNode(toRemove);
                return toRemove;
            }
            return null;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLLList> fmap;
    int min, capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fmap = new HashMap<>();
        this.min = 0;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.val;
    }

    private void update(Node node) {
        DLLList oldList = fmap.get(node.cnt);
        oldList.removeNode(node);
        
        if (node.cnt == min && oldList.size == 0) {
            min++;
        }
        
        node.cnt++;
        DLLList newList = fmap.getOrDefault(node.cnt, new DLLList());
        newList.addToHead(node);
        fmap.put(node.cnt, newList);
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            if (capacity == map.size()) {
                DLLList minList = fmap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLLList minList = fmap.getOrDefault(min, new DLLList());
            minList.addToHead(newNode);
            fmap.put(1, minList);
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