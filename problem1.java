
// Still Fuzzy on the logic

class LFUCache {
    class Node {
        int key;
        int val;
        int cnt;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size; // we want to check if a list is empty of not

        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = tail;
            this.tail.prev = head;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        private Node removeLast() {
            Node tailPrev = this.tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> fMap;
    int min;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
    }

    private void updateNode(Node node) {
        // frequency increase
        // remove from old freq list and add to new;
        int oldCnt = node.cnt;
        DLList oldList = fMap.get(oldCnt);
        oldList.removeNode(node);
        //
        if (oldCnt == min && oldList.size == 0) {
            min++;
        }
        int newCnt = oldCnt + 1;
        node.cnt = newCnt;
        DLList newList = fMap.getOrDefault(newCnt, new DLList());
        newList.addToHead(node);
        fMap.put(newCnt, newList);
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        updateNode(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            updateNode(node);
        } else {
            // fresh
            if (capacity == 0)
                return;
            if (capacity == map.size()) {
                // remove lFU node
                DLList minList = fMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node node = new Node(key, value);
            min = 1;
            DLList newList = fMap.getOrDefault(min, new DLList());
            newList.addToHead(node);
            fMap.put(min, newList);
            map.put(key, node);
        }
    }
}