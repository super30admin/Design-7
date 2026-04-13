// Time Complexity : O(1) for all operations
// Space Complexity : O(capacity) for map and storing nodes
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
/*
We maintain 2 hashmaps, one for regular key and node mapping and the other for frequency and doublylinkedlist
mapping.The first map containing the nodes have a frequency field which helps us idenitfy in case of LFU and
The second map will be helpful to identify the LRU case where we store the least recent used nodes towards
the tail and most frequent nodes at the head of DLL.Whenever, get or update for existing node happens, we
use update method to update the frequency map.
 */
class LFUCache {
    Map<Integer, Node> map;
    Map<Integer, DLL> frqMap;
    int capacity;
    int minFrequency;

    class Node {
        Node next;
        Node prev;
        int frequency;
        int key;
        int value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    class DLL {
        Node head;
        Node tail;
        int size;

        public DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);

            head.next = tail;
            tail.prev = head;
        }

        private void insertAtHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;

            this.size++;
        }

        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }
    }

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.frqMap = new HashMap<>();
        this.capacity = capacity;
    }

    private void update(Node node) {
        int oldFrq = node.frequency;
        DLL oldFrqList = frqMap.get(oldFrq);
        oldFrqList.removeNode(node);

        if(oldFrq == minFrequency && oldFrqList.size == 0) {
            minFrequency++;
        }

        int newFrq = oldFrq + 1;
        node.frequency = newFrq;
        DLL newFrqList = frqMap.getOrDefault(newFrq, new DLL());
        newFrqList.insertAtHead(node);
        frqMap.put(newFrq, newFrqList);
    }

    public int get(int key) {
        if(!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        }
        else {
            if(map.size() == capacity) {
                DLL list = frqMap.get(minFrequency);
                Node nodeToBeRemoved = list.tail.prev;
                list.removeNode(nodeToBeRemoved);
                map.remove(nodeToBeRemoved.key);
            }
            minFrequency = 1;
            Node newNode = new Node(key, value);
            map.put(key, newNode);

            DLL newList = frqMap.getOrDefault(minFrequency, new DLL());
            newList.insertAtHead(newNode);
            frqMap.put(minFrequency, newList);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */