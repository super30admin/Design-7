// Time complexity: O(1)
// Space complexity: O(1)
// Did it run successfully on leetcode: Yes

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

        //add the node to the beginning of the dll list
        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }

        //remove the given node
        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        //remove the last node and return it
        public Node removeLast() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    int capacity, min;
    Map<Integer, Node> map; // map of key to node
    Map<Integer, DLList> freqMap; // map of frequency to DLL

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1; // if the key is not present
        Node node = map.get(key); //if the key is present, return the value of the key and update the node to update the freq of the key
        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        //if map contains key, update the value of the node and the node. Return
        if(map.containsKey(key)) {
            Node node = map.get(key);
            updateNode(node);
            node.value = value;
            return;
        }

        if(capacity == 0) return;

        //If the max capacity is reached, remove the least frequent, least recently used (tail node of dll list) node
        if(map.size() == capacity) {
            DLList oldList = freqMap.get(min);
            Node oldNode = oldList.removeLast();
            map.remove(oldNode.key);
        }

        //If the key is not present, create a new node with the given key and value, count is initially 1
        Node node = new Node(key, value);
        min = 1;

        //add the node to the dll list of the min freq
        DLList list = freqMap.getOrDefault(min, new DLList());
        list.addToHead(node);

        //update freq map and key value map
        freqMap.put(min, list);
        map.put(key, node);
    }

    //If the node is accessed, update the freq of the node
    public void updateNode(Node node) {
        //get the dll list of curr frequency and remove the node from the list
        DLList oldList = freqMap.get(node.cnt);

        if(oldList != null) {
            oldList.removeNode(node);
        }

        //if curr freq equals min and after removing this node, if the size of the dll list becomes 0, increase the min
        if(node.cnt == min && oldList.size == 0) {
            min++;
        }

        //Increase the freq, get the dll list of the new freq and add the node to the head of the dll list.
        // Update the freq map with the updated dll list
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