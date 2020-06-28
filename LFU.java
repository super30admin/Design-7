// Time Complexity : O(1) all functions
// Space Complexity : O(n + m) for maintaing two hashmaps where n is the number of nodes and m is the maximum frequency
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : Two hashmaps with one having Doubly LL as value to the frequency as key is difficult to think
/* Algorithm: Maintain two hashmaps. In one of the hashmaps, maintain the key and node as the value. Keep the count of nodes into the node structure only
to keep a track of how many times the node has been accessed, update it timely. Another hashmap will store the frequency and the doubly LL carrying
the nodes having the same frequency and it will enable to perform LRU so that the least recently used node is removed from the doubly LL. The nodes
will be readjusted once accessed and the frequency will be updated time to time. In the Frequency hashmap, once the global minimum frequency has 
empty doubly LL and the count of the node is equal to min, increment the min to the new minimum. If capacity is exceeded, find the global min mapping to 
which doubly LL and remove the min frequency node from the list.
*/
class LFUCache {
    class Node{
        int count;int key;   
        int val;
        Node next;
        Node prev;
        Node(int k, int v){
            this.val = v;                                                               // Key Value and Count fields node
            this.key = k;
            this.count = 1;
            this.next = null;
            this.prev = null;
        }
    }
    class DLList{
        Node head;
        Node tail;
        int size;
        DLList(){
            this.head = new Node(-1,-1);                                                        // Initialise the Doubly LL
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        public void insert(Node n){
            n.next = head.next;
            n.prev = head;                                                                      // Insert the nodes in the Doubly LL to the head
            head.next = n;
            n.next.prev = n;
            size++;
        }
        public void removeNode(Node n){
            n.prev.next = n.next;                                                                               // Remove the node from the doubly LL
            n.next.prev = n.prev;
            size--;
        }
        public Node removeLast(){
            Node node = tail.prev;                                                              // Remove the last node as this is the LRU node
            removeNode(node);
            return node;
        }
    }
    HashMap<Integer, Node> store;                                                           // Map the key to its corresponding nodes
    HashMap<Integer, DLList> frequency;                                                         // Map the frequency to the doubly LL
    int min=0;
    int capacity;
    public LFUCache(int capacity) {
        store = new HashMap<>();
        this.capacity = capacity;
        frequency = new HashMap<>();
    }
    
    public int get(int key) {
        if(store.containsKey(key)){
            update(store.get(key));                                                             // Update the node accessed frequency since it is accessed in get() 
            return store.get(key).val;
        } else {
            return -1;                                                                          // Node value does not exist
        }
    }
    
    public void put(int key, int value) {
        if(store.containsKey(key)){
            Node node = store.get(key);
            node.val = value;                                                           // Update the node value
            update(node);
        } else{
        if(capacity == 0){return;}                                                                                      // edge case
        if(store.size() == capacity){
            DLList least = frequency.get(min);                                                                  // Get the min frequency nodes
            Node rem = least.removeLast();
            store.remove(rem.key);                                                                      // Removing the node forever
        } 
        Node new_node = new Node(key, value);
        store.put(key, new_node);
        min = 1;                                                                                                    
        DLList node = frequency.getOrDefault(min, new DLList());                                    // Add the new node to the frequency map
        node.insert(new_node);                                                                      // Insert the node to the doubly LL
        frequency.put(min, node);
        }
    }
    public void update(Node n){
        DLList oldList = frequency.get(n.count);
        oldList.removeNode(n);                                                                                  // Remove the node and update its frequency to map it accordingly to the frequency
        if(oldList.size == 0 && n.count == min) min++;                                              // If the doubly LL is size 0 and count of the node is already min
        n.count++;
        DLList newList = frequency.getOrDefault(n.count, new DLList());                                             
        newList.insert(n);                                                                                  
        frequency.put(n.count, newList);                                                        // Update the frequency map
    }
}
