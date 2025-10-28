/*
460. LFU Cache
Ran on leetcode: Yes
TC: O(1) for all operations
SC: O(n) n = capacity 
Create one map to index for faster access and one more map with key as frequency and value as DLL with head being the LRU node for updating the node based on LFU and if freq is same using LRU. We get the node in constant time from first map and update using second map. We put the node in constant time if the node already exists just put the node and update freq and if node does not exists evict if necessary and put the node and update freq.
*/
class LFUCache {

    class Node{ // Create node for DLL, with key, val and freq by default 1
        Node prev;
        Node next;
        int key;
        int val;
        int freq;

        Node(int key, int val){
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }

    class Dll{
        Node head;
        Node tail;
        int size;

        Dll(){
            // dummy nodes
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node){
            // Update new nodes pointer
            node.next = head.next;
            node.prev = head;
            // Update pointers around it
            head.next = node;
            node.next.prev = node;
            size++;
        }

        public void removeNode(Node node){
            //Update the pointers around it
            node.prev.next = node.next;
            node.next.prev = node.prev;
            // Update nodes pointer
            node.next = null;
            node.prev = null;
            size--;
        }
    }

    int capacity;
    Map<Integer, Dll> freqMap;
    Map<Integer, Node> nodeMap;
    int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.freqMap = new HashMap<>();
        this.nodeMap =  new HashMap<>();
        this.minFreq = 0;
    }

    private void update(Node node){
        // Remove the node from freqMap
        int oldFreq = node.freq;
        Dll dll = freqMap.get(oldFreq);
        dll.removeNode(node);

        // check if the old freq was min freq
        if(oldFreq == minFreq){
            // Check if the min freq got empty if yes then increase min freq
            if(dll.size == 0){
                minFreq++;
            }
        }

        //calculate new frequency and update freqMap
        int newFreq = oldFreq + 1;
        node.freq = newFreq;
        Dll newDll = freqMap.getOrDefault(newFreq, new Dll());
        newDll.addToHead(node);
        freqMap.put(newFreq, newDll);
    }
    
    public int get(int key) {
        if(nodeMap.containsKey(key)){ //Check if nodemap contains node else return -1
            Node node = nodeMap.get(key);
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        
        // Check if it is new node or existing node
        if(!nodeMap.containsKey(key)){ // Node doesnt already exists
            Node newNode = new Node(key, value); // Create new node
            // Check the capacity
            if(nodeMap.size() == capacity){
                Dll dll = freqMap.get(minFreq); // get the dll for node with least freq
                // remove the LFU node
                Node node = dll.tail.prev;
                dll.removeNode(node);
                nodeMap.remove(node.key);
            }
            // Since a new node is added
            minFreq = 1;
            // Get the Dll for 1 and add node to the head
            Dll dll = freqMap.getOrDefault(minFreq, new Dll());
            dll.addToHead(newNode);
            freqMap.put(minFreq, dll);
            nodeMap.put(newNode.key, newNode);
        } else { // If Node already exists
            // Update the value and update it in freqMap
            Node newNode = nodeMap.get(key);
            newNode.val = value;
            update(newNode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */