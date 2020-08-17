class LFUCache {
    class Node{
        Node prev;
        Node next;
        int key;
        int val;
        int count;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
            this.count=1;
        }
    }
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
        public void addToHead(Node node){
            node.prev=head;
            node.next=head.next;
            head.next=node;
            node.next.prev=node;
            size++;
        }
        public void remove(Node node){
            node.prev.next=node.next;
            node.next.prev=node.prev;
            size--;
        }
        public Node removeLast(){
            Node tailPrev=tail.prev;
            remove(tailPrev);
            return tailPrev;
        }
    }
    Map<Integer,DLList> freq;
    Map<Integer,Node> map;
    int capacity;
    int min;
    public LFUCache(int capacity) {
        this.freq=new HashMap();
        this.map=new HashMap();
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(map.containsKey(key)){
            Node node=map.get(key);
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.val=value;
            update(node);
        }
        else{
            if(capacity==0){
                return;
            }
            if(map.size()==capacity){
                DLList minList=freq.get(min);
                Node newNode=minList.removeLast();
                map.remove(newNode.key);
            }
            Node nodeNew=new Node(key,value);
            min=1;
            DLList minList=freq.getOrDefault(min,new DLList());
            minList.addToHead(nodeNew);
            freq.put(min,minList);
            map.put(key,nodeNew);
        }
    }
    
    private void update(Node node){
        DLList oldList=freq.get(node.count);
        oldList.remove(node);
        if(node.count==min && oldList.size==0){
            min++;
        }
        node.count++;
        DLList newList=freq.getOrDefault(node.count,new DLList());
        newList.addToHead(node);
        freq.put(node.count,newList);
        map.put(node.key,node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */