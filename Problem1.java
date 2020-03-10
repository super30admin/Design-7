// Time Complexity - Both get and put is O(1)
// Space Complexity - O(2n) where n is the number is elements 
// Total space will be used by the 2 HashMap data structure. One will be be to keep track of key and values and the other to keep track of the frequency and the order of recently used.

class LFUCache {
    class Node{
        int key;
        int val;
        int cnt;
        Node next;
        Node prev;
        public Node(int key,int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }
    
    class DLList{
        Node head;
        Node tail;
        int size;
        public DLList(){
            tail = new Node(-1,-1);
            head = new Node(-1,-1);
            tail.prev = head;
            head.next = tail;
        }
        
        public void addtoHead(Node node){
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;
            size++;
        }
        
        public void remove(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        public Node removeLast(){
            if(size > 0){
                Node node = tail.prev;
                remove(node);
                return node;
            }
            return null;
        }
    }
    HashMap<Integer,Node> map;
    HashMap<Integer,DLList> freq;
    int min;int capacity;
    
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;
        
    }
    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void update(Node node){
        DLList OldList = freq.get(node.cnt);
        OldList.remove(node);
        if(OldList.size == 0 && node.cnt == min)
            min++;
        node.cnt++;
        
        DLList newList = freq.getOrDefault(node.cnt,new DLList());
        newList.addtoHead(node);
        freq.put(node.cnt,newList);
    }
    
    public void put(int key, int value) {
        //if key already exists
        
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        // if key does not exist
        else{
            // If Cachesize is not full
            if(capacity == 0)   return;
            if( map.size() == capacity){
                DLList minList = freq.get(min);
                Node toberemoved = minList.removeLast();
                map.remove(toberemoved.key);
            }
            Node node = new Node(key,value);
            map.put(key,node);
            DLList addtoList = freq.getOrDefault(1,new DLList());
            addtoList.addtoHead(node);
            freq.put(1,addtoList);
            min =1;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
