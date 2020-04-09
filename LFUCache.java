class LFUCache {
    //key and node map
    Map<Integer, Node> cache;
    Map<Integer, DLinkedList> frequencyMap;
    int maxSize;
    int min;
    public LFUCache(int capacity) {
        cache = new HashMap<>();
        frequencyMap = new TreeMap<>();
        maxSize = capacity;
    }
    //min is minimum frequency at any point of time. it would be useful to remove least frequently used item
    class Node{
        int key, value, count;
        Node prev, next;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            count =1;
        }
    }
    class DLinkedList{
        Node head, tail;
        int size;
        public DLinkedList(){
            head = new Node(-1,-1); 
            tail = new Node(-1,-1); 
            head.next = tail;
            tail.prev = head;
        }
        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }
        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        public Node removeLast(){
            if(size > 0){
                Node prev = tail.prev;
                removeNode(prev);
                return prev;
            }
            return null;
        }
    }
    public int get(int key) {
        Node node = cache.get(key);
        if(node != null){
            updateFrequnecy(node);
            return node.value;
        }
        return -1;
    }
    public void put(int key, int value) {
        //edge case
        if(maxSize == 0) return;
        //not present in the cache
        if(!cache.containsKey(key)){
             Node node = new Node(key, value);
            //try if cache has size available
            //cache is full... remove least frequency used item from the cache.
            if(cache.size() == maxSize){
               DLinkedList dll = frequencyMap.get(min);
               Node nodeToBeRemoved = dll.removeLast();
               if(nodeToBeRemoved != null)
                    cache.remove(nodeToBeRemoved.key);
            }
            //cache has free memory, insert into cache
            if(!frequencyMap.containsKey(1))
                frequencyMap.put(1, new DLinkedList());
            frequencyMap.get(1).addToHead(node);
            cache.put(key, node);
            min =1;
        }
        else{
            //already present in cache, update its value and increase count
            Node node = cache.get(key);
            //update count and value of the node
            node.value = value;
            updateFrequnecy(node);
        }
    }

    private void updateFrequnecy(Node node){
        DLinkedList dll = frequencyMap.get(node.count);
        //remove the node from old list
        dll.removeNode(node);
        //check if old list size became zero, if yes remove that entry from the map
        //this node was belonging to minimum frequency and node was the only one in that list we have to update minimum now.
        if(dll.size == 0 && node.count ==  min) //increase min 
            min++;
        int count = node.count;
        count++;
        node.count = count;
        //increase the frequency of the node
        if(!frequencyMap.containsKey(count))
            frequencyMap.put(count, new DLinkedList());
        frequencyMap.get(count).addToHead(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
