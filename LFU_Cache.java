// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class LFUCache {
    class Node {
        int key, value, cnt;
        Node prev, next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.cnt = 1;
        }
    }

    class DLList {
        Node head, tail;
        int size;
        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node) {
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
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    int capacity, min;
    Map<Integer, Node> map;
    Map<Integer, DLList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key); 
        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            updateNode(node);
            node.value = value;
            return;
        }

        if(capacity == 0) return;
        if(map.size() == capacity) {
            DLList oldList = freqMap.get(min);
            Node oldNode = oldList.removeLast();
            map.remove(oldNode.key);
        }

        Node node = new Node(key, value);
        min = 1;
        DLList list = freqMap.getOrDefault(min, new DLList());
        list.addToHead(node);
        freqMap.put(min, list);
        map.put(key, node);
    }

    public void updateNode(Node node) {
        DLList oldList = freqMap.get(node.cnt);
        if(oldList != null) {
            oldList.removeNode(node);
        }

        if(node.cnt == min && oldList.size == 0) {
            min++;
        }
        node.cnt++;
        DLList newList = freqMap.getOrDefault(node.cnt, new DLList());
        newList.addToHead(node);
        freqMap.put(node.cnt, newList);
    }
}
/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */