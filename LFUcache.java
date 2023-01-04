// Time Complexity : O(1)
// Space Complexity :O(n)
class LFUCache {
    Map<Integer, DLList> freq;
    Map<Integer,Node> map;
    int capacity, min;
    
    class DLList{
        Node head;
        Node tail;
        int size;
        public DLList(){
            head=new Node(-1,-1);
            tail=new Node(-1,-1);
            head.next=tail;
            tail.prev=head;
        }
        
        private Node removeLast(){
            Node lastNode=tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
        
        private void removeNode(Node node){
            node.prev.next=node.next;
            node.next.prev=node.prev;
            size--;
        }
        
        private void addFirst(Node node){
            node.prev=head;
            node.next=head.next;
            node.next.prev=node;
            head.next=node; 
            size++;
        }
        
    }
    
    
    
    class Node{
        int key;
        int value, frequency;
        Node next;
        Node prev;
        
        public Node(int key, int value){
            this.key=key;
            this.value=value;
            this.frequency=1;
        }
        
    }
    
    
    
    

    public LFUCache(int capacity) {
        this.capacity=capacity;
        freq=new HashMap<>();
        map=new HashMap<>();
      
    }
    
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        
        Node node=map.get(key);
        update(node);
        return node.value;
    }
    
    private void update(Node node){
        DLList oldList=freq.get(node.frequency);
        oldList.removeNode(node);
        if(min==node.frequency && oldList.size==0){
            min++;
        }
        
        node.frequency++;
        DLList newList=freq.getOrDefault(node.frequency,new DLList());
        newList.addFirst(node);
        freq.put(node.frequency,newList);
    }
    
    public void put(int key, int value) {
        if(capacity==0) return;
        if(map.containsKey(key)){
            Node node=map.get(key);
            update(node);
            node.value=value;
            return;
        }
        
        if(capacity==map.size()){
            DLList oldList=freq.get(min);
            Node tailprev=oldList.removeLast();
            map.remove(tailprev.key);
        }
        
        Node node=new Node(key,value);
        map.put(key,node);
        min=1;
        DLList list=freq.getOrDefault(min,new DLList());
        list.addFirst(node);
        freq.put(min, list);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */