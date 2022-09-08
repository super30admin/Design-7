// TC: O(1)
// SC: O(n)


class LFUCache {
    class Node{
        Node next;
        Node prev;
        int key;
        int value;
        int cnt;
     
        public Node(int key,int value){
            this.key=key;
            this.value=value;
            cnt=1;
        }
    }
    class DLList{
        Node head,tail;
        int size;
        
        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
            
        }
        
        public void addToHead(Node node){
            node.next=head.next;
            node.next.prev=node;
            head.next=node;
            node.prev=head;
            size++;
        }
        public void removeNode(Node node){    // particular removal
            node.prev.next=node.next;
            node.next.prev=node.prev;
            size--;
        }
        
        public Node removelast(){               //least used means last one removed
            Node lastnode = tail.prev;
            removeNode(lastnode);
            return lastnode;
        }
    }
    
    int capacity,min;
    
    HashMap<Integer,Node> map;          // key and node pair for get method
    HashMap<Integer,DLList> freqmap;      // frequency and dllist pair

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqmap = new HashMap<>();
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            node.value=value;
            return;
        }
        if(capacity == 0) return;
        if(capacity == map.size()){
            DLList oldList = freqmap.get(min);
            Node oldnode = oldList.removelast(); 
            map.remove(oldnode.key);
        }
        Node node = new Node(key,value);
        min=1;
        DLList newList = freqmap.getOrDefault(min,new DLList());
        newList.addToHead(node);
        map.put(key,node);
        freqmap.put(min,newList);
    }
    
    private void update(Node node){
        DLList oldList = freqmap.get(node.cnt);
        if(oldList!=null){
            oldList.removeNode(node);
        }
        if(min==node.cnt && oldList.size == 0){
            min++;
            //freqmap.remove(node.cnt);
        }
        node.cnt++;
        DLList newList = freqmap.get(node.cnt);
        if(newList==null){
            freqmap.put(node.cnt,new DLList());
        }
        freqmap.get(node.cnt).addToHead(node);
    }
}
