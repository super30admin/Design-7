class LFUCache {
    class Node{
        int key; int val;
        int cnt; Node prev; Node next;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        } 
    }

    class DLList{
        Node head; Node tail; int size;
        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        public void addToHead(Node node){
            node.prev = head; //connecting the orphan first
            node.next = head.next; //connecting the orphan first
            head.next = node;
            node.next.prev = node;
            this.size++;
        }
        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }
        public Node removeTailPrev(){
            Node toRemove = this.tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    }
    HashMap<Integer,Node> map;
    HashMap<Integer,DLList> fMap;
    int capacity;
    int min;
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
        this.capacity = capacity;

    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }
    
    private void update(Node node){
        int oldFreq = node.cnt;
        DLList oldList = fMap.get(oldFreq);
        oldList.removeNode(node);
        if(oldFreq == min && oldList.size == 0){
            min++;
        }
        node.cnt++;
        int newCnt = node.cnt;
        DLList updatedList = fMap.getOrDefault(newCnt,new DLList());
        updatedList.addToHead(node);
        fMap.put(node.cnt,updatedList);
        map.put(node.key,node);
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }else {
            if(this.capacity == map.size()){
                DLList minFreq = fMap.get(min);
                Node node = minFreq.removeTailPrev();
                map.remove(node.key);
            }
            Node newNode = new Node(key,value);
            min = 1;
            DLList newList = fMap.getOrDefault(min,new DLList());
            newList.addToHead(newNode);
            fMap.put(newNode.cnt,newList);
            map.put(key,newNode);
        
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */