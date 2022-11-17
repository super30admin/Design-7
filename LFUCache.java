// Time complexity : O(1)
// Space complexity : O(capacity)
class LFUCache {

    class Node {
        int key, value, frequency;
        Node next, prev;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    class DLList {
        Node head, tail;
        int size;
        public DLList () {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }

        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        private Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }

    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity, min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
        min = 1;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        // check if LFU cache of size 0 has been initialized
        if (capacity == 0) return;

        // If map contains key, then we just need to update the node fre in the freqMap
        // and move it to a new key
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }

        if (capacity == map.size()) {
            DLList temp = freqMap.get(min);
            Node node = temp.removeLastNode();
            map.remove(node.key);
        }

        Node node = new Node(key, value);
        min = 1;

        // add the node new to head of frequency map
        DLList temp = freqMap.getOrDefault(min, new DLList());
        temp.addToHead(node);
        // update frequency map
        freqMap.put(min, temp);

        // populate the key -> node hashmap
        map.put(key, node);
    }

    private void update(Node node) {
        // get the nodes frequency
        int count = node.frequency;
        // get the dll list for the node
        DLList oldlist = freqMap.get(count);
        // remove the node from the dll list
        oldlist.removeNode(node);
        if (count == min && oldlist.size == 0) {
            min++;
        }
        // increase frequency of the node
        node.frequency++;
        // add node to new frequency dll list
        DLList newlist = freqMap.getOrDefault(node.frequency, new DLList());
        // add removed node to head of the new DLL list
        newlist.addToHead(node);
        // add to frequency map again
        freqMap.put(node.frequency, newlist);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
