// tc = O(1)
//sc = O(1)

class LFUCache {

    class Node {
        int key;
        int val;
        int count;
        Node prev;
        Node next;

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
            this.head.next = tail;
            this.tail.prev = head;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }

        private void removeHead(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        private Node removeTail() {
            Node tailPrev = tail.prev;
            removeHead(tailPrev);
            return tailPrev;

        }

    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        update(node);
        return node.val;

    }

    private void update(Node node) {
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount);
        oldList.removeHead(node);
        if (oldCount == min && oldList.size == 0)
            min++;
        node.count = node.count + 1;
        int newCount = node.count;
        DLList newList = freqMap.getOrDefault(newCount, new DLList());
        newList.addToHead(node);
        freqMap.put(newCount, newList);
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        // not contais key
        else {
            if (map.size() == capacity) {
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeTail();
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
}
