class LFUCache {
    
    class Node {
        int key, value, count;
        Node prev, next;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
    
    class DList {
        Node head, tail;
        int size = 0;
        
        public DList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            
            head.next = tail;
            tail.prev = head;
        }
        
        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            
            size++;
        }
        
        public void remove(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        public Node removeLast(){
            Node node = tail.prev;
            remove(node);
            return node;
        }
    }
    
    Map<Integer, Node> map;
    Map<Integer, DList> freqMap;
    int cap, min;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        cap = capacity;
    }
    
    public void update(Node node){
        int count = node.count;
        DList oldList = freqMap.get(count);
        oldList.remove(node);
        
        if(count == min && oldList.size == 0)
            min++;
        
        int newCount = count+1;
        node.count = newCount;
        
        freqMap.putIfAbsent(newCount, new DList());
        freqMap.get(newCount).addToHead(node);
        
    }
    
    // Time Complexity: O(1)
    // Space Complexity: O(n)
    public int get(int key) {
        if(!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        update(node);
        
        return node.value;
    }
    
    // Time Complexity: O(1)
    // Space Complexity: O(n)
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            
            update(node);
            return;
        }else{
            if(cap == 0)
                return;
            if(cap == map.size()){
                Node node = freqMap.get(min).removeLast();
                map.remove(node.key);
            }
            
            Node newNode = new Node(key, value);
            min = 1;
            
            freqMap.putIfAbsent(min, new DList());
            freqMap.get(min).addToHead(newNode);
            map.put(key, newNode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */