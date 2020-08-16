
/**
 * https://leetcode.com/problems/lfu-cache
 *
 */
class LFUCache {

    class Node{
        Node prev, next;
        int key, val, cnt;
        
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }
    
    class DLList{
        Node head, tail;
        int size;
        
        public DLList() {
            //create dummy nodes for head and tail
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        
        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        public Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }
    
    //map of frequency and Doubly linked list;
    HashMap<Integer, DLList> freq;
    HashMap<Integer, Node> map;
    int capacity;
    
    //min frequency from freq map
    int min;
    
    public LFUCache(int capacity) {
        freq = new HashMap<>();
        map = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            return node.val;
        }else {
            return -1;
        }
    }
    
    //update current nodes position - called from the get
    private void update(Node node) {
       DLList oldDlist = freq.get(node.cnt);
       oldDlist.removeNode(node);
       
       //if old list frequency is min and old list size becomes zero, we need to increase global min
       if(node.cnt == min && oldDlist.size == 0){
            min++;   
       } 
       
       //increase node cnt by 1
       node.cnt++;
       DLList newList = freq.getOrDefault(node.cnt, new DLList()); 
       newList.addToHead(node);
       freq.put(node.cnt, newList);
       map.put(node.key, node);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            //edge case of capacity = 0
            if(capacity == 0) return;
            
            //if capacity is full
            if(map.size() == capacity) {
              DLList list = freq.get(min);
              Node nodeToBeRemoved = list.removeLast();
              map.remove(nodeToBeRemoved.key);
            }
            
            Node newNode = new Node(key, value);
            min = 1;
            
            DLList minList = freq.getOrDefault(min, new DLList());
            minList.addToHead(newNode);
            freq.put(min, minList);
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