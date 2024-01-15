class LFUCache {

    class Node {
        int val, key, freq;
        Node next, prev;

        public Node(int key, int val) {
            this.val = val;
            this.key = key;
            this.freq = 1; // first entry will have freq as a 1.
        }
    }

    class DLL {
        Node head, tail;
        int size;

        public DLL() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            size = 0;

            // assign pointers.
            head.next = tail;
            tail.prev = head;
        }

        // Methods of DLL class to perform add and remove operations
        // add Node to Head
        private void addToHead(Node node) {
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            node.prev = head;

            size++;
        }

        // Remove Node from middle
        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;

            size--;
        }

        // Remove Last Node from tail
        private Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    // two HashMaps : 1st is key, val; and 2nd is freq, DLL
    Map<Integer, Node> map;
    Map<Integer, DLL> freqmap;
    int capacity, min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqmap = new HashMap<>();
        min = 1;
    }

    // update Node in DLL for it's respective frequency
    private void update(Node node) {
        // get old freq, and old DLL list associated
        int count = node.freq;
        DLL oldList = freqmap.get(count);

        // if the node.freq == min -> remove node from the list,
        oldList.removeNode(node);
        // if oldlist size ==0; and min == count, update min++
        if (oldList.size == 0 && min == count) {
            min++;
            freqmap.remove(count); // removing entry from map when it's zero!
        }
        // increase node's freq
        node.freq++;

        // fetch the list with updated freq; add node to head.
        DLL newList = freqmap.getOrDefault(node.freq, new DLL());
        // add node to new list head
        newList.addToHead(node);

        // update the new list in freqmap
        freqmap.put(node.freq, newList);

    }

    public int get(int key) {
        // 1. if key is not availabl return -1;
        if (!map.containsKey(key))
            return -1;

        // if key is available - do following steps.
        Node node = map.get(key);
        // update- node
        update(node);

        // return node's value
        return node.val;
    }

    public void put(int key, int value) {
        // map contains it
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            node.val = value;
            return;
        }

        // map doesn'tcontain key

        // capacity is given zero!
        if (capacity == 0)
            return;

        // check size
        if (map.size() == capacity) {
            // remove lastNode associated with min freq.
            DLL minList = freqmap.get(min);
            Node lastNode = minList.removeLastNode();
            map.remove(lastNode.key);
        }
        // reset min to 1 for new entry.
        min = 1;

        Node node = new Node(key, value); // node.freq = 1 is set in constructor.

        // get list associated with min to add new node there.
        DLL newList = freqmap.getOrDefault(min, new DLL()); // we coudld be adding this list for the first time

        // added to list head
        newList.addToHead(node);

        // update both maps
        map.put(key, node);
        freqmap.put(min, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */