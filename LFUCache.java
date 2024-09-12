/*
 * 
 * Time complexity : O(1)
 * Space compleixty : O(n)
 */

class LFUCache {

    class Node{
        int key;
        int val;
        int count;
        Node next;
        Node prev;
        
        Node(int key, int value){
            this.key = key;
            this.val = value;
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
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        
        public void addNode(Node node){
            
            node.next = head.next;
            node.next.prev = node;
            
            head.next = node;
            node.prev = head;
            
            size++;
        }
        
        public void removeNode(Node node){
            
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        public Node removeLast(){
            if(size > 0){
                Node last = tail.prev;
                removeNode(last);
                return last;
            }else{
                return null;
            }
        }
    }
    
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freq;
    int capacity;
    int min;
    
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;
    }
    
    
    
    public int get(int key) {
        Node node = map.get(key);
        
        if(node == null){
            return  -1;
        }
        
        updateNode(node);
        return node.val;
    }
    
    public void updateNode(Node node){
        
        DLList curr = freq.get(node.count);
        curr.removeNode(node);
        
        if(curr.size == 0 && node.count == min){
            min++;
        }
        
        node.count++;
        //map.put(key, node);
        
        curr = freq.getOrDefault(node.count, new DLList());
        curr.addNode(node);
        freq.put(node.count, curr);
    }
    
    public void put(int key, int value) {
        Node node;
        
        if(map.containsKey(key)){
            node = map.get(key);
            node.val = value;
            
            updateNode(node);
        }else{
            //Case 1 :  when capacity is full
            if(capacity == 0){
                return;
            }
            if(map.size() == capacity){
                
                DLList d = freq.get(min);
                Node leastUsed = d.removeLast();
                map.remove(leastUsed.key);
            }
            
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            DLList curr = freq.getOrDefault(newNode.count, new DLList());
            curr.addNode(newNode);
            min = 1;
            freq.put(newNode.count, curr);
            
            
        }
        
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
