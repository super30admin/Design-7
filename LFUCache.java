import java.util.HashMap;

// Time complexity: O(1) for get and put operations
// Space complexity: O(N) where N is the capacity of the cache

// Intuition: Use a HashMap to store the key-value pairs and another HashMap to maintain the frequency of each key.
// Use a doubly linked list to maintain the order of keys based on their frequency.
// When a key is accessed or updated, increase its frequency and move it to the appropriate position in the linked list.
// When the cache reaches its capacity, remove the least frequently used key.


public class LFUCache {
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> fMap;
    int minFrq;

    class Node {
        int key;
        int value;
        int frq;
        Node next, prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frq = 1;
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

        private void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
            this.size--;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
    }

    private void update(Node node) {
        int oldFrq = node.frq;
        DLList oldFrqList = fMap.get(oldFrq);
        oldFrqList.removeNode(node);

        if (oldFrq == minFrq && oldFrqList.size == 0) {
            minFrq++;
        }

        int newFrq = ++oldFrq;
        node.frq = newFrq;
        DLList newFrqList = fMap.getOrDefault(newFrq, new DLList());
        newFrqList.addToHead(node);
        fMap.put(newFrq, newFrqList);
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        } else {
            if (map.size() == capacity) {
                DLList minList = fMap.get(minFrq);
                Node freq_tail = minList.tail.prev;
                minList.removeNode(freq_tail);
                map.remove(freq_tail.key);
            }
            minFrq = 1;
            Node newNode = new Node(key, value);
            map.put(key, newNode);

            DLList minList = fMap.getOrDefault(minFrq, new DLList());
            minList.addToHead(newNode);
            fMap.put(minFrq, minList);
        }
    }
}
