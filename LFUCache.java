//Time Complexity: O(1)
//Space Complexity: O(n)

/*
 * we maintain a hashmap of key and another one for frequency. 
 * based on the frequency we add the elements to head of linked list of
 * the hashmap and remove the last values. if the value is present 
 * we get it from the hashmap and update its frequency as well.
 */

 class LFUCache {

    class Node{
        int key; int val;
        int count;
        Node next; Node prev;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLList{
        Node head; Node tail;
        int size;
        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }
        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }
        public Node removeLast(){
            Node toRemove = this.tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int min; int capacity;
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
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
        }
        else{
            if(capacity == 0) return;
            if(capacity == map.size()){
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node node = new Node(key, value);
            map.put(key, node);
            min = 1;
            DLList minList = freqMap.getOrDefault(min, new DLList());
            minList.addToHead(node);
            freqMap.put(min, minList);
        }
    }

    public void update(Node node){
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount);
        oldList.removeNode(node);
        if(min == oldCount && oldList.size == 0) min++;
        node.count++;
        int newCount = node.count;
        DLList newList = freqMap.getOrDefault(newCount, new DLList());
        newList.addToHead(node);
        freqMap.put(newCount, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */