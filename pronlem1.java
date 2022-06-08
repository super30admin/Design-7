class LFUCache {
    
    class Node{
        int key, value, cnt;
        Node next, prev;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            cnt = 1;
        }
    }
    
    class DLList{
        Node head, tail;
        int size;
        public DLList(){
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        private void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        
        private void remove(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLast(){
            Node lastNode = tail.prev;
            remove(lastNode);
            return lastNode;
        }
    }
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> frq;
    int capacity, min;

    public LFUCache(int capacity) {
        map = new HashMap();
        frq = new HashMap();
        this.capacity = capacity;
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
            node.value = value;
            update(node);
            return;
        }
        if(capacity == 0) return;
        if(capacity == map.size()){
            DLList oldList = frq.get(min);
            Node lastNode = oldList.removeLast();
            map.remove(lastNode.key);
        }
        Node node = new Node(key, value);
        min = 1;
        DLList newList = frq.getOrDefault(1, new DLList());
        newList.addToHead(node);
        map.put(key, node);
        frq.put(1, newList);
    }
    
    private void update(Node node){
        DLList oldList = frq.get(node.cnt);
        if(oldList != null){
            oldList.remove(node);
        }
        if(min == node.cnt && oldList.size == 0){
            min++;
        }
        node.cnt++;
        DLList newList = frq.getOrDefault(node.cnt, new DLList());
        newList.addToHead(node);
        frq.put(node.cnt, newList);
    }
}

 //time complexity O(1) for all methods
 //space complexity O(capacity or size)