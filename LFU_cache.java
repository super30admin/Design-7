// Time Complexity : O(1)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class LFUCache {
    class Node{
        int key;
        int value;
        int freq;
        Node prev;
        Node next;
        
        public Node(int key, int value, int freq){
            this.key=key;
            this.value=value;
            this.freq=freq;
        }
    }
    
    class DLL{
        Node head;
        Node tail;
        int size;
        
         public DLL(){
           head=new Node(-1,-1,-1);
           tail=new Node(-1,-1,-1);
           size=0;
             
            head.next=tail;
            tail.prev=head;
        }
        
       
        
        public void addToHead(Node node){
            node.next=head.next;
            node.prev=head;
            head.next=node;
            node.next.prev=node;
            size++;
        }
        
        public void remove(Node node){
            node.prev.next=node.next;
            node.next.prev=node.prev;
            size--;
        }
        
        public Node removeLastNode(){
            Node node=tail.prev;
            remove(node);
            return node;
        }
        
    }
    HashMap<Integer,Node> keys;
    HashMap<Integer,DLL> frequencies;
    int min;
    int capacity;
    
    public LFUCache(int capacity) {
        this.keys=new HashMap<>();
        this.frequencies=new HashMap<>();
        this.min=0;
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!keys.containsKey(key)){
            return -1;
        }
        
        Node node=keys.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        
        if(capacity==0) return;
        if(keys.containsKey(key)){
           Node node= keys.get(key);
           node.value=value;
           update(node);
        }else{
            if(keys.size()==capacity){
                DLL minList=frequencies.get(min);
                Node node=minList.removeLastNode();
                keys.remove(node.key);
            }
            min=1;
            Node newNode=new Node(key,value,1);
            keys.put(key, newNode);
            if(!frequencies.containsKey(1)){
                frequencies.put(1,new DLL());
            }
            
            frequencies.get(1).addToHead(newNode); 
        }
    }
    
    private void update(Node node){
        int oldFreq=node.freq;
        DLL oldList=frequencies.get(oldFreq);
        oldList.remove(node);
        if(oldFreq==min && oldList.size==0){
            min++;
        }
        int newFreq=oldFreq+1;
        node.freq=newFreq;
        if(!frequencies.containsKey(newFreq)){
            frequencies.put(newFreq,new DLL());
        }
        
        frequencies.get(newFreq).addToHead(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */