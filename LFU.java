// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

/**
 * In this problem, I have maintained a main Hashmap with Key --> Node mapping
 * Also, we have maintained a secondary hashmap with Frequency --> DLL of Nodes
 * 
 * For get operation, We search in the main hashmap with Key. We also update the frequency map with the new frequency of the node.
 * For Put operation, we update the value of the node in the main map and also update the Frequency map.
 */

class Node {
    int key;
    int val;
    
    Node prev;
    Node next;
    
    int freq;
    
    public Node(int key, int val) {
        this.key = key;
        this.val = val;
        this.freq = 1;
    }
}

class DLList {
    Node head;
    Node tail;
    
    int size;
    
    public DLList() {
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        
        head.next = tail;
        tail.prev = head;
    }
    
    public void addToHead(Node node) {
        node.next = head.next;
        head.next = node;
        node.prev = head;
        node.next.prev = node;
        size++;
    }
    
    public void removeNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        
        size--;
    }
    
    public Node removeLast() {
        Node prevTail = tail.prev;
        
        removeNode(prevTail);
        
        return prevTail;
    }
}


class LFUCache {
    int capacity;
    Map<Integer, Node> key_node_map;
    Map<Integer, DLList> freq_dll_map;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.key_node_map = new HashMap<>();
        this.freq_dll_map = new HashMap<>();
    }
    
    public int get(int key) {
        if(!key_node_map.containsKey(key)) {
            return -1;
        } else {
            // get the node
            Node cur = key_node_map.get(key);
            
            updateFrequencyMap(cur);
            
            // return value of the Key from the node
            return cur.val;
        }
    }
    
    public void put(int key, int value) {
        
        if(capacity == 0) return;
        
        if(key_node_map.containsKey(key)) {
            Node cur = key_node_map.get(key);
            
            cur.val = value;
            // key_node_map.get(key).freq += 1;
            
            // update the freq_dll_map
            
            updateFrequencyMap(cur);
        } else {
            // Fresh Node coming
            
            if(capacity == key_node_map.size()) {
                // remove the LFU node
                DLList min_freq_list = freq_dll_map.get(min);
                
                Node toRemoveNode = min_freq_list.removeLast();
                
                key_node_map.remove(toRemoveNode.key);
            }

                Node node = new Node(key, value);
                min = 1;
                
                DLList list = freq_dll_map.getOrDefault(min, new DLList());
                list.addToHead(node);
                
                freq_dll_map.put(min, list);
                key_node_map.put(key, node);

        }
    }
    
    public void updateFrequencyMap(Node cur) {
        
        int freq = cur.freq;
        
        // 1. get the DLL from the freq_map
        DLList oldList = freq_dll_map.get(freq);
                
        // 2. remove from the DLL
        oldList.removeNode(cur);
            
        if(freq == min && oldList.size == 0) {
            min++;
        }
            
        // 3. increment frequency for the Node
        freq++;
        
        cur.freq = freq;
           
        // insert in the freq_dll_map at requiredNode.freq key
        
        DLList newList = freq_dll_map.getOrDefault(freq, new DLList());
        newList.addToHead(cur);
        freq_dll_map.put(cur.freq, newList);
        key_node_map.put(cur.key, cur);
    }
}