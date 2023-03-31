class LFUCache {
    
    class Node {
        int key;
        int value;
        int count;
        Node prev; Node next;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            int count = 1;
        }  
    }
    
    class DLList {
        Node head;
        Node tail;
        int size;
        public DLList() {
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = tail;
            this.tail.prev = head;     
        }
        
        private void addtoHead(Node node){
            Node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLast(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            size--;
        }
    }
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node); //remove it from previous old prev list and add it to new freq list
        return node.value;
    }
    
    private void update(Node node){
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount);
        oldList.removeNode(node);
        if(min == oldCount && oldList.size == 0) min++;
        node.count = node.count+1;
        int newCount = node.count;
        DLList newList = freqMap.getOrDefault(newCount, new DLList());
        newList.addtoHead(node);
        freqMap.put(newCount, newList);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(node);
            update(node);
            node.value = value;
        }else{
            if(map.size() == capacity){
                //remove node
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key,value);
            min = 1;
            DLList newList = freqMap.getOrDefault(1, new DLLIst());
            newList.addtoHead(newNode);
            freqMap.put(1, newList);
            map.put(Key, newNode);
        }
        
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
