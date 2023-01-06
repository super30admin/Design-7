//TC:O(1)
//SC:O(n)
class LFUCache {
    
    class Node{
        int key, value, frequency;
        Node next, prev;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }
    
    class DLList{
        int size;
        Node head, tail;
        public DLList(){
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        private void addToHead(Node node){
            node.next = head.next;
            head.next.prev = node;
            node.prev = head;
            head.next = node;
            size++;
        }
        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        private Node removeLastNode(){
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
        
        HashMap<Integer, Node> map;
        HashMap<Integer, DLList> freqmap;
        int capacity;
        int min;
        
    }
    HashMap<Integer, Node> map;
        HashMap<Integer, DLList> freqmap;
        this.capacity = capacity;
        int min;
        

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqmap = new HashMap<>();
        this.capacity = capacity;
        
    }
    
    public int get(int key) {
        if(!map.containskey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containskey(key)){
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }
        if(capacity == map.size()){
            DLList oldlist = freqmap.get(min);
            Node tailprev = oldlist.removeLastNode();
            map.remove(tailprev);
            
        }
        Node node = new Node(ke, value);
        min = 1;
        map.put(key, node);
        DLList newlist = freqmap.getOrDefault(min, new DLList());
        newlist.addToHead(node);
    }
    
    private void update(Node node){
        DLList oldlist = freqmap.get(node.frequency);
        oldlist.removeNode(node);
        if(min == node.frequency && oldlist.size == 0){
            min++;
        }
        node.frequency++;
        DLList newlist = freqmap.getOrDefault(node.frequency, new DLList());
        newlist.addToHead(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
