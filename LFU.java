class LFUCache {
    class Node{
        int key,val,count;
        Node prev,next;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
            this.count=1;
        }
    }
    class DLList{
        Node head,tail;
        int size;
        public DLList(){
            this.head=new Node(-1,-1);
            this.tail=new Node(-1,-1);
            this.head.next=tail;
            this.tail.prev=head;
            this.size=0;
        }
    }
    int cap,min,tsize;
    HashMap<Integer,Node> mapKey;
    HashMap<Integer,DLList> mapFreq;
    private void removeNode(DLList dll,Node curr){
        curr.prev.next=curr.next;
        curr.next.prev=curr.prev;
        dll.size--;
    }
    private void addNode(DLList dll,Node curr){
        Node head=dll.head;
        head.next.prev=curr;
        curr.next=head.next;
        curr.prev=head;
        head.next=curr;
        dll.size++;
    }
    private void removeLast(DLList dll){
        Node tail=dll.tail;
        Node curr=tail.prev;
        curr.prev.next=tail;
        tail.prev=curr.prev;
        curr.prev=null;
        curr.next=null;
        dll.size--;
    }
    public LFUCache(int capacity) {
        this.cap=capacity;
        this.min=1;
        this.tsize=0;
        mapKey=new HashMap<>();
        mapFreq=new HashMap<>();
    }
    
    public int get(int key) {
        if(!mapKey.containsKey(key)) return -1;
        else{
            Node curr=mapKey.get(key);
            DLList old = mapFreq.get(curr.count);
            removeNode(old,curr);
            if(curr.count==min && old.size==0) min++;
            curr.count++;
            if(!mapFreq.containsKey(curr.count)){
                mapFreq.put(curr.count,new DLList());
            }
            DLList newl = mapFreq.get(curr.count);
            addNode(newl,curr);
            return curr.val;
        }
    }
    
    public void put(int key, int value) {
        Node curr;
        DLList newlist,oldlist;
        if(!mapKey.containsKey(key)){
            curr = new Node(key,value);
            mapKey.put(key,curr);
            if(!mapFreq.containsKey(curr.count)){
                mapFreq.put(curr.count,new DLList());
            }
            newlist=mapFreq.get(curr.count);
            addNode(newlist,curr);
            this.tsize++;
            if(this.tsize>cap){
                oldlist=mapFreq.get(min);
                mapKey.remove(oldlist.tail.prev.key);
                removeLast(oldlist);
                this.tsize--;
            }            
            min=1;
        }
        else{
            curr=mapKey.get(key);
            curr.val=value;
            oldlist=mapFreq.get(curr.count);
            removeNode(oldlist,curr);
            if(curr.count==min && oldlist.size==0){
                min++;
            }
            curr.count++;
            if(!mapFreq.containsKey(curr.count)){
                mapFreq.put(curr.count,new DLList());
            }
            newlist=mapFreq.get(curr.count);
            addNode(newlist,curr);
            
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
