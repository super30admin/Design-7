// TC: O(1) for get and put
// SC: O(N)
class LFUCache {
    class Node {
        int key;
        int value;
        int frequency;
        Node next;
        Node prev;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    class DLL {
        Node head;
        Node tail;
        int size;

        DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.size = 0;

            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        public Node removeTail() {
            Node toRemove = this.tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    }

    HashMap<Integer, Node> keyMap;
    HashMap<Integer, DLL> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        keyMap = new HashMap<Integer, Node>();
        freqMap = new HashMap<Integer, DLL>();
        this.capacity = capacity;
    }

    private void updateNode(Node node) {
        int oldFreq = node.frequency;
        DLL li = freqMap.get(oldFreq);

        // remove from old freq
        li.removeNode(node);
        if (oldFreq == min && li.size == 0) {
            min = min + 1;
        }

        // add to new freq
        int newFreq = oldFreq + 1;
        node.frequency = newFreq;

        if (!freqMap.containsKey(newFreq)) {
            freqMap.put(newFreq, new DLL());
        }
        DLL newList = freqMap.get(newFreq);
        newList.addToHead(node);
    }

    public int get(int key) {
        if (!keyMap.containsKey(key)) {
            return -1;
        }
        Node node = keyMap.get(key);
        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            node.value = value;
            updateNode(node);
        } else {
            if (capacity == 0) {
                return;
            }
            if (capacity == keyMap.size()) {
                // remove LFU
                DLL minList = freqMap.get(min);
                Node removed = minList.removeTail();
                keyMap.remove(removed.key);
            }

            Node newNode = new Node(key, value);
            this.min = 1;
            keyMap.put(key, newNode);
            if (!freqMap.containsKey(min)) {
                freqMap.put(min, new DLL());
            }
            DLL list = freqMap.get(min);
            list.addToHead(newNode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */