//Leetcode :460. LFU Cache 
//Time Complexity: get O(1), put O(1)
//Space Complexity: O(n) // n is the capacity of the cache, LinkedList and hashmap are used.
class LFUCache {    
    class Node{
        int key;
        int val;
        Node next;
        Node prev;
        int fr;
        Node(int key, int val){
            this.key=key;
            this.val= val;
            this.next=null;
            this.prev=null;
            fr=1; 
        }        
    }
    class DLL{
        Node head;
        Node tail;
        int size;
        DLL(){
            this.head= new Node(-1,-1);
            this.tail= new Node(-1,-1);
            head.next=tail;
            tail.prev= head;
        }
    }    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freq;
    int capacity;
    int min;
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity= capacity;
        this.min =0;
    }
    
    public int get(int key) {
        if(map.containsKey(key)){
            update(map.get(key));
            return map.get(key).val;
        }
        return -1;
    }
    public void update(Node n){
        int f= n.fr;
        removeNode(n); // remove node from old DLL 
        DLL d = freq.get(f);
        if(d.size==0 && f==min) min++;
        n.fr=f+1;
        addNode(n); //Add updated node to DLL
    }
    public void removeNode(Node n){
        DLL d= freq.get(n.fr);    
        if(d==null) return;
        d.size--;
        n.prev.next= n.next;
        n.next.prev=n.prev;

    }
    public void addNode( Node n){      
        if(!freq.containsKey(n.fr)){
            freq.put(n.fr, new DLL());  
        }
         DLL d= freq.get(n.fr);    
        d.size++;
        n.prev= d.head;
        n.next= d.head.next;         
        d.head.next.prev = n;
        d.head.next= n;       
    }
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node n= map.get(key);
            n.val= value;
            map.put(key,n);
            update(n);
        }
        else{
            Node n = new Node(key,value);
            if(map.size()>=capacity){ 
                DLL d= freq.get(min);
               if(d==null) return;
                Node r= d.tail.prev;
                removeNode(r);
                map.remove(r.key);     
            }
            min=1; //remember
            n.fr=1; //take care of this
            map.put(key,n);
            addNode(n);
            
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */