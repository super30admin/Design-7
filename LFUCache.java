// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Approach

// We design this using DLL 
// We make use of Hashmap for frequency and dll and to store the nodes

class LFUCache {
    class Node {
        int key, value, cnt;
        Node next, prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.cnt = 1;
        }
    }

    class DLL {
        Node head, tail;
        int size;

        public DLL() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addtoHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        public Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, DLL> freqmap;
    HashMap<Integer, Node> map;
    int capacity, min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        freqmap = new HashMap<>();
        map = new HashMap<>();

    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0)
            return;
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }
        if (capacity == map.size()) {
            DLL oldList = freqmap.get(min);
            Node node = oldList.removeLast();
            map.remove(node.key);
        }
        Node newNode = new Node(key, value);
        min = 1;
        DLL newList = freqmap.getOrDefault(min, new DLL());
        newList.addtoHead(newNode);
        freqmap.put(min, newList);
        map.put(key, newNode);
    }

    public void update(Node node) {
        DLL oldList = freqmap.get(node.cnt);
        if (oldList != null) {
            oldList.removeNode(node);
        }
        if (oldList.size == 0 && min == node.cnt) {
            min++;
        }
        node.cnt++;
        DLL newList = freqmap.getOrDefault(node.cnt, new DLL());
        newList.addtoHead(node);
        freqmap.put(node.cnt, newList);
    }
}
