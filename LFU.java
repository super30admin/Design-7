class LFUCache {
    // Node for Doubly linked list
    class Node{
        int key;
        int value;
        int count;
        Node next;
        Node prev;
        public Node(int key, int value){
            this.value = value;
            this.key = key;
            this.count = 1;
        }
    }
    // doubly liked list class
    class DLL{
        Node head;
        Node tail;
        int size;
        public DLL(){
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }

        public void remove(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        }

        public void add(Node node){
            node.prev = this.head;
            node.next = this.head.next;
            this.head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public Node removeTail(){
            if(size == 0) return null;
            Node toRemove = tail.prev;
            remove(toRemove);
            return toRemove;
        }
    }

    // global objects
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freqMap;
    int capacity;
    int minFreq;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
        this.minFreq = 0;
    }
    
    public int get(int key) {
        if(map.containsKey(key)){
            // get the node and DLL from maps
            Node curr = map.get(key);
            DLL currList = freqMap.get(curr.count);
            curr.count += 1;

            // remove from old list 
            currList.remove(curr);
            if(minFreq == curr.count - 1 && currList.size == 0){
                minFreq++;
            }
            // add to head of new freq DLL
            if(!freqMap.containsKey(curr.count)){
                freqMap.put(curr.count, new DLL());
            }
            DLL newList = freqMap.get(curr.count);
            newList.add(curr);

            return curr.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(capacity == 0)return;

        if(map.containsKey(key)){
            get(key);
            map.get(key).value = value;
            return;
        }

        if(map.size() >= capacity){
            DLL leastFreq = freqMap.get(minFreq);
            Node toRemove = leastFreq.removeTail();
            map.remove(toRemove.key);
        }

        Node node = new Node(key, value);
        map.put(key, node);
        DLL newList;
        if(!freqMap.containsKey(1)){
            freqMap.put(1, new DLL());
        }
        newList = freqMap.get(1);
        newList.add(node);
        minFreq = 1;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
