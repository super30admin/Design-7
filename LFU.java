// Time Complexity : O(1)
// Space Complexity : O(capacity)
// Did this code successfully run on Leetcode : yes 
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
// nodeMap : to get the nodes currently in cache and freqList: to get nodes with same frequency sorted by recency
// get -> if we have a node -> update its cnt, remove from the old freq list and add to the new freq list
// if we don't have a node -> return -1
// put -> if we have a node -> update its value and cnt, remove from the old freq list and add to the new freq list
// if we don't have a node -> remove the last node in min freq list if the capacity is full , else add to the 1 freq list
//  DO NOT FORGET TO UPDATE THE NODEMAP AND FREQLIST AND global MIN FREQ every time to change the cnt

class LFUCache {

    class Node {
        int key;
        int value;
        int cnt;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.cnt = 1;
        }
    }

    class DLL {
        Node head;
        Node tail;
        int size; // 0 def

        public DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
            this.size--;
        }

        public void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            this.size++;
        }

        public Node removeFromTail() {
            Node toRemove = this.tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    }

    HashMap<Integer, Node> nodeMap;
    HashMap<Integer, DLL> freqList;
    int min;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        nodeMap = new HashMap();
        freqList = new HashMap();
    }

    public void update(Node node) {
        DLL oldList = freqList.get(node.cnt);
        oldList.removeNode(node);

        if (node.cnt == min && oldList.size == 0)
            min++;

        node.cnt++;
        DLL newList = freqList.getOrDefault(node.cnt, new DLL());
        newList.addToHead(node);
        freqList.put(node.cnt, newList);
    }

    public int get(int key) {
        if (!nodeMap.containsKey(key))
            return -1;

        Node node = nodeMap.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {

        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.value = value;
            update(node);
        } else {
            Node newNode = new Node(key, value);
            if (nodeMap.size() == capacity) {
                Node remove = freqList.get(min).removeFromTail();
                nodeMap.remove(remove.key);
            }
            min = 1;
            nodeMap.put(key, newNode);
            DLL minList = freqList.getOrDefault(min, new DLL());
            minList.addToHead(newNode);
            freqList.put(min, minList);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */