class LFUCache {
    class Node
    {
        int key;
        int val;
        int count;
        Node prev;
        Node next;
        Node(int k, int v)
        {
            this.key = k;
            this.val = v;
            this.count = 1;
        }
    }
    class DLList{
        Node head;
        Node tail;
        int size;
        public DLList()
        {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        // TC: O(1)
        private void addToHead(Node node)
        {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        // TC: O(1)
        private void removeNode(Node node)
        {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        // TC: O(1)
        private Node removeLastNode()
        {
           Node tailPrev = tail.prev;
           removeNode(tailPrev);
           return tailPrev;
        }
    }
    HashMap<Integer, DLList> freqMap;
    HashMap<Integer, Node> map;
    int min;
    int capacity;
    
    public LFUCache(int capacity) {
        freqMap = new HashMap();
        map = new HashMap();
        this.capacity = capacity;
    }
    // TC: O(1)
    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }
    // TC: O(1)
    public void put(int key, int value) {
         if (map.containsKey(key))
         {
             // just update the value and update it in freqMap
             Node node = map.get(key);
             node.val = value; // update the value
             update(node); //update it in freqMap
         }
        else // newNode is coming
        {
            if (capacity == 0) 
                return;
            if ( map.size() == capacity) // capacity is full
            {
                // remove the least frequently used node in cache
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLastNode();
                map.remove(toRemove.key);
            }
            // min is 1
            Node newNode = new Node(key, value);
            min = 1;
            DLList newList = freqMap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            freqMap.put(min, newList);
            map.put(key, newNode);
        }
    }
    // TC: O(1)
    private void update(Node node)
    {
        // for updating the node from oldFreqList to newFreqList
        int count = node.count;
        DLList oldList = freqMap.get(count);
        oldList.removeNode(node);
        if ( min == count && oldList.size == 0)
        {
            min++;
        }
        // transfer it to newList with +1 freq
        node.count++;
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
