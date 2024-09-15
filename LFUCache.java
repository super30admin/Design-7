/**
 * Time Complexity : O(1) all functions
 * Space Complexity : O(n + m) for maintaing two hashmaps where n is the number of nodes and m is the maximum frequency
 */
import java.util.*;
class LFUCache {
    class Node{
        int count;int key;   
        int val;
        Node next;
        Node prev;
        Node(int k, int v){
            this.val = v;                                                               
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
            this.head = new Node(-1,-1);                                                        
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        public void insert(Node n){
            n.next = head.next;
            n.prev = head;                                                                     
            head.next = n;
            n.next.prev = n;
            size++;
        }
        public void removeNode(Node n){
            n.prev.next = n.next;                                                                               
            n.next.prev = n.prev;
            size--;
        }
        public Node removeLast(){
            Node node = tail.prev;                                                             
            removeNode(node);
            return node;
        }
    }
    HashMap<Integer, Node> store;                                                           
    HashMap<Integer, DLList> frequency;                                                         
    int min=0;
    int capacity;
    public LFUCache(int capacity) {
        store = new HashMap<>();
        this.capacity = capacity;
        frequency = new HashMap<>();
    }
    
    public int get(int key) {
        if(store.containsKey(key)){
            update(store.get(key));                                                              
            return store.get(key).val;
        } else {
            return -1;                                                                          
        }
    }
    
    public void put(int key, int value) {
        if(store.containsKey(key)){
            Node node = store.get(key);
            node.val = value;                                                          
            update(node);
        } else{
        if(capacity == 0){return;}                                                                                      
        if(store.size() == capacity){
            DLList least = frequency.get(min);                                                                  
            Node rem = least.removeLast();
            store.remove(rem.key);                                                                      
        } 
        Node new_node = new Node(key, value);
        store.put(key, new_node);
        min = 1;                                                                                                    
        DLList node = frequency.getOrDefault(min, new DLList());                                    
        node.insert(new_node);                                                                      
        frequency.put(min, node);
        }
    }
    public void update(Node n){
        DLList oldList = frequency.get(n.count);
        oldList.removeNode(n);                                                                                 
        if(oldList.size == 0 && n.count == min) min++;                                             
        n.count++;
        DLList newList = frequency.getOrDefault(n.count, new DLList());                                             
        newList.insert(n);                                                                                  
        frequency.put(n.count, newList);                                                       
    }
}