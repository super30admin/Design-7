// 460.
class Node {
    int key; //a node of doubly linked list with key, val and count
    int value; 
    int count;
    Node prev;
    Node next;
    
    public Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.count = 1; //count is set to 1 when node is created intially
        this.prev = null;
        this.next = null;
    }
}

class DLL {
    Node head; //dummy head and tail
    Node tail;
    int size; //# of nodes in dll excluding dummy head and dummy tail
    
    public DLL() {
        this.head = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.tail = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.tail.prev = this.head;
        this.head.next = this.tail;
        this.size = 0;
    }
    
    //time - O(1)
    public void removeNode(Node current) {
        //remove node from dll 
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--; //reduce size by 1
        return;
    }
    
    //time - O(1)
    public void addToHead(Node current) {
        //add current to head
        current.next = head.next;
        current.prev = head;
        head.next = current;
        current.next.prev = current;
        size++; //increase size of dll
        return;
    }
    
    //time - O(1)
    public Node removeLastNode() {
        //remove last node
        Node last = tail.prev; //last(least recently used) node
        removeNode(last);
        //size--;
        return last;
    }
}

class LFUCache {

    HashMap<Integer, Node> nodeReference; //map of key to node
    HashMap<Integer, DLL> freq; //map of freq to dll of nodes with that freq
    int capacity;
    int minFreq; //min freq in freq map
    
    public LFUCache(int capacity) {
        this.nodeReference = new HashMap<>();
        this.freq = new HashMap<>();
        this.capacity = capacity;
    }
    
    //time - O(1)
    public int get(int key) {
        //check if key is in the nodeReference map, if so return value else return -1
        if(nodeReference.containsKey(key))
        {
            Node current = nodeReference.get(key); //get the node
            adjustFreq(current); //maintain lfu order in freq map
            return current.value; //return value
        }
        return -1; //key absent
    }
    
    //time - O(1)
    public void put(int key, int value) {
        //edge
        if(capacity == 0)
        {
            return;
        }
        //key already exists
        if(nodeReference.containsKey(key))
        {
            Node current = nodeReference.get(key); //get the node
            current.value = value; //update value
            adjustFreq(current); //maintain lfu order in freq map
        }
        else //new key to be added
        {
            Node current = new Node(key, value); //node to be added to cache with count = 1
            //capacity is reached
            if(nodeReference.size() == capacity)
            {
                DLL minFreqList = freq.get(minFreq); //get the list of nodes with min freq
                Node lru = minFreqList.removeLastNode(); //remove last node(least recently used node) in that list
                nodeReference.remove(lru.key); //remove least recently used node from main map
            }
            minFreq = 1; //reset min freq to 1 as this is a new key
            DLL list = freq.getOrDefault(minFreq, new DLL()); //get list of nodes with count 1
            list.addToHead(current); //add current to head of that list
            freq.put(minFreq, list); //add in freq map
            nodeReference.put(key, current); //add to key-node pair map 
        }
        return;
    }
    
    //time - O(1)
    private void adjustFreq(Node current) {
        DLL list = freq.get(current.count); //list of nodes with count same as current
        list.removeNode(current); //remove current from its pos 
        if(list.size == 0 && current.count == minFreq) //update min freq if applicable
        {
            minFreq++;
        }
        current.count++; //increase count of this node by 1 as it is accessed once
        list = freq.getOrDefault(current.count, new DLL()); //new list - current has to be inserted into this list as its count is increaased by 1
        list.addToHead(current); //add current to this list as its count is increased by 1
        freq.put(current.count, list); //add this to freq map
        return;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
