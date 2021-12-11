class LFUCache {
    class Node{
        int key; 
        int val;
        int cnt;
        Node prev;
        Node next;
        
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
        
    }
    
    class DLList{
        Node head;
        Node tail;
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
            return tailPrev;
        }
    }
    
    HashMap<Integer, DLList> freqMap;
    HashMap<Integer, Node> map;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        freqMap = new HashMap<>();
        map = new HashMap<>();
        this.capacity = capacity;
        
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else{
            if(capacity == 0) return;
            if(capacity == map.size()){
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList newList = freqMap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            freqMap.put(min, newList);
            map.put(key, newNode);
        }
    }
    
    public void update(Node node){
        int count = node.cnt;
        DLList oldList = freqMap.get(count);
        oldList.removeNode(node);
        if(count == min && oldList.size == 0){
            min++;
        }
        count++;
        node.cnt = count;
        DLList newList = freqMap.getOrDefault(count, new DLList());
        newList.addToHead(node);
        freqMap.put(count, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

//Time: 54 ms (78.50%), Space: 125.7 MB (62.12%) 
//TC: functions are o(1)
//SC: O(n) two Hashmaps and one doubly linked list
