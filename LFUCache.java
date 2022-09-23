// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach
//460. LFU Cache (Hard) - https://leetcode.com/problems/lfu-cache/
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
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        private void remove(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLast() {
            Node tailPrev = tail.prev;
            remove(tailPrev);
            return tailPrev;
        }
    }
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    public int get(int key) { // O(1)
        if (!map.containsKey(key)) return -1; // return -1 if main map doesn't contain the given key entry
        
        Node node = map.get(key); // if present, get the node of key and update in frequency map as most frequently used
        update(node);
        return node.val;
    }
    
    // update the frequency map with new count of the node and assigns new minimum if possible
    private void update(Node node) { // O(1)
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount); // retrieve the list from frequency map of existing count
        oldList.remove(node); // remove node from the retrieved list
        
        if (oldCount == min && oldList.size == 0) { // update min if the retrieved list is empty and that list belongs to minimum count
            min++;
        }
        
        node.count++;
        int newCount = node.count;
        DLList newList = freqMap.getOrDefault(newCount, new DLList()); // increment the count of node and update the new list of the incremented count
        newList.addToHead(node); // add the node to head as it is most frequently used
        
        freqMap.put(newCount, newList); // update the freqeuncy map with update node count and corresponding list
    }
    
    public void put(int key, int value) { // O(1)
        if (map.containsKey(key)) { // if map contains, update the map with node key, values
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else { // check if capacity is full
            if (capacity == 0) return;
            if (map.size() == capacity) { // if capacity is full, get the list of min from frequency map and remove least recently used node from least frequently used list
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key); // remove from main map
            }
            Node newNode = new Node(key, value);
            min = 1; // here, we can be sure that the new min is 1 because we don't have that entry previously in map and the capacity was full, so we have to make new entry and mark the count as 1
            DLList newList = freqMap.getOrDefault(1, new DLList());
            newList.addToHead(newNode);
            freqMap.put(1, newList); // update frequency map
            map.put(key, newNode); // add the entry to main map
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */