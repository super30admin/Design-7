// #LC 460. LFU Cache   #S30 128

class LFUCache {
    
    class Node {
        int key, value, count;
        Node next, prev;
        private Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
    
    class DLList {
        Node head, tail;
        int size;
        private DLList() {
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        
        private void addToHead( Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }
        
        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity, min;
    
    
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
        
        
    }
    
    // TC = O(1)
    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            return node.value;
        }
        return -1;
    }
    
    
    // T.c = O(1)
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        }
        else {
            
            if(capacity == 0) {
                return;
            }
            if(capacity == map.size()) {
                DLList removeList = freqMap.get(min);
                Node removedNode = removeList.removeLast();
                map.remove(removedNode.key);
            }
            
            Node newNode = new Node(key, value);
            min = 1;
            map.put(key, newNode);
            DLList newList = freqMap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            freqMap.put(min, newList);
        }
        
    }
    
    // T.C => O(1)
    private void update(Node node) {
        
        int count = node.count;
        node.count++;
        DLList oldList = freqMap.get(count);
        oldList.removeNode(node);
        
        if(min == count && oldList.size == 0) min++;
        DLList newList = freqMap.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        freqMap.put(node.count, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */



 