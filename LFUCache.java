// Space Complexity: O(n)
class LFUCache {
    
    // This is used to check if value is present or not
    class Node
    {
        int key;
        int val;
        int count;
        Node next, prev;
        
        public Node(int key, int val)
        {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }
    
    // This is used to check vals with same freq (LRU)
    class DLL {
        Node head;
        Node tail;
        int size; // to check count of nodes
        
        public DLL()
        {
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            
            head.next = tail;
            tail.prev = head;
        }
        
        // O(1)
        public void addToHead(Node node)
        {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++; // increase the count of node
        }
        
        // O(1)
        public void removeNode(Node node)
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--; // reduce the count of node
        }
        
         // Least frequently used, in case of capacity is full
         // O(1)
        public Node removeLast()
        {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev; // need to remove this from map also
        }
    }
    
    
    Map<Integer, Node> map;
    Map<Integer, DLL> freqMap;
    int capacity;
    int minFreq = 0;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }
    
    // O(1)
    public int get(int key) {
        if(!map.containsKey(key))
            return -1;
        
        Node node = map.get(key);
        update(node); // need to increase frequency count and update
        
        return node.val;
    }
    
    // O(1)
    public void put(int key, int value) {
        if(capacity == 0)
            return;
        
        if(map.containsKey(key)) //O(1)
        {
            // update val and freq
            Node node = map.get(key); // O(1)
            update(node); // O(1)
            node.val = value;
            return;
        }
        
        
        if(capacity == map.size()) //O(1)
        {
            // remove LFU 
            DLL oldList = freqMap.get(minFreq); //O(1)
            Node node = oldList.removeLast();//O(1)
            // remove from map
            map.remove(node.key); //O(1)
        }
        
        Node node = new Node(key, value); 
        map.put(key, node);
        minFreq = 1;
        DLL oldList = freqMap.getOrDefault(minFreq, new DLL()); //O(1)
        oldList.addToHead(node); // Most recently used //O(1)
        freqMap.put(minFreq, oldList);
    }
    
    // O(1)
    public void update(Node node)
    {
        DLL oldList = freqMap.get(node.count); 
        // node exists in freq map
        if(oldList != null)
        {
            // remove node from freq map, as freq will be increased
            oldList.removeNode(node);
        }
        // check if the after removing the node freq is become zero
        if(oldList.size == 0 && minFreq == node.count)
        {
            minFreq++;
        }
        // update its freq count
        node.count++;
        // place in new location in freqMap
        DLL newList = freqMap.getOrDefault(node.count, new DLL()); 
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