class LFUCache {
    class Node{
        int key, val, count;
        Node next, prev;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;

        public DLList(){
            head = new Node(-1,-1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        private void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
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
    }

    int capacity, min;
    Map<Integer, Node> map;
    Map<Integer, DLList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node newNode = map.get(key);
            newNode.val = value;
            update(newNode);
            return;
        }
        else{
            if(capacity==0) return;
            if(capacity==map.size()){
                DLList ddlist = freqMap.get(min);
                Node last = ddlist.removeLastNode();
                map.remove(last.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList newList = freqMap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            freqMap.put(min, newList);
            map.put(key, newNode);
        }
    }

    private void update(Node node){
        int freq = node.count;
        DLList oldList = freqMap.get(freq);
        oldList.removeNode(node);
        if(min==freq && oldList.size==0){
            min++;
        }
        node.count++;
        freq = node.count;
        if(!freqMap.containsKey(freq)){
            freqMap.put(freq, new DLList());
        }
        DLList newList = freqMap.get(freq);
        newList.addToHead(node);
        freqMap.put(freq, newList);
    }
}

