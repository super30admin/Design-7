import java.util.HashMap;

public class LFUCache460 {
    
        HashMap<Integer,Node> map;
        HashMap<Integer,DLList> frequencyMap;
        int capacity;
        int  min;
        int size;
    
        public LFUCache460(int capacity) {
            
            map = new HashMap<>();
            frequencyMap = new HashMap<>();
            size = 0;
            min = Integer.MAX_VALUE;
            this.capacity = capacity;
            
        }
        
        public int get(int key) {
            
            Node node = map.get(key);
            if(node==null) return -1;
            update(node);
            return node.val;
        }
        
        public void put(int key, int value) {
            if(capacity==0) return;
            
            Node node = map.get(key);
            
            if(node!=null){
                node.val = value; 
                update(node);
            } else {
                node = new Node(key,value);
                if(size==capacity){
                    DLList minList = frequencyMap.get(min);
                    Node removed = minList.removeLast();
                    map.remove(removed.key);
                    size--;
                }
                
                DLList newList = frequencyMap.get(1);
                if(newList==null){
                    newList = new DLList();
                    frequencyMap.put(1,newList);
                }
                newList.addFirst(node);
                map.put(key,node);
                min = 1;
                size++;
            }
            
            
        }
        
        
        public void update(Node node){
            DLList priorList = frequencyMap.get(node.frequency);
            priorList.remove(node);
            
            if(priorList.size==0){
                if(node.frequency == min){
                    min++;
                }
            }
            node.frequency++;
            DLList newList = frequencyMap.get(node.frequency);
            if(newList==null){
                newList = new DLList();
                frequencyMap.put(node.frequency, newList);
            }
            newList.addFirst(node);
        }


        public static void main(String[] args) {
            LFUCache460 cache = new LFUCache460(2);
            cache.put(1, 1);
            cache.put(2, 2);
            System.out.println(cache.get(1));       // returns 1
            cache.put(3, 3);    // evicts key 2
            cache.get(2);       // returns -1 (not found)
            cache.get(3);       // returns 3.
            cache.put(4, 4);    // evicts key 1.
            cache.get(1);       // returns -1 (not found)
            cache.get(3);       // returns 3
            cache.get(4);       // returns 4            
        }
    }
    
    class Node {
        
        int key,val,frequency;
        Node next, prev;
        
        Node(int key, int val){
            this.key = key;
            this.val = val;
            this.frequency = 1;
        }
    }
    
    
    class DLList {
        Node head, tail;
        int size;
        
        DLList(){
            head = new Node(0,0);
            tail = new Node(0,0);
            head.next = tail;
            tail.prev = head;
        }
        
        public void addFirst(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        public void remove(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        public Node removeLast(){
            Node cursor = tail.prev;
            remove(cursor);
            return cursor;
        }
    }
    
    
    /**
     * Your LFUCache object will be instantiated and called as such:
     * LFUCache obj = new LFUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */