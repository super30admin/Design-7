import java.util.HashMap;

public class LFUCache {
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freqMap;
    int minFreq;

    class Node {
        int key;
        int val;
        int frq;
        Node prev, next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.frq = 1;
        }
    }

    class DLL { //Doubly Linkedlist
        Node head;
        Node tail;
        int size;

        public DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        private void insertNodeToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            this.size++;
        }

        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
            size--;
        }

    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFreq = 1;
    }

    private void update(Node node) {
        int oldFreq = node.frq;

        DLL dll = freqMap.get(oldFreq);
        dll.removeNode(node);

        if (dll.size == 0 && oldFreq == minFreq) {
            minFreq++;
        }

        int newFrq = oldFreq + 1;
        node.frq = newFrq;

        DLL newFrqList = freqMap.getOrDefault(newFrq, new DLL());
        newFrqList.insertNodeToHead(node);
        freqMap.put(newFrq, newFrqList);

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
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            if (map.size() == capacity) {
                DLL minFrqList = freqMap.get(minFreq);
                Node lastNode = minFrqList.tail.prev;
                minFrqList.removeNode(lastNode);
                map.remove(lastNode.key);
            }
            minFreq = 1;
            Node newNode = new Node(key, value);
            map.put(key, newNode);

            DLL minFrqList = freqMap.getOrDefault(minFreq, new DLL());
            minFrqList.insertNodeToHead(newNode);
            freqMap.put(minFreq, minFrqList);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

//TC: O(1), SC: O(n) - num of nodes
