class LFUCache {
    class Node{
        int key;
        int val;
        int freq;
        Node prev;
        Node next;
        public Node(int key,int val){
            this.freq = 1;
            this.val = val;
            this.key = key;
        }
    }

    class DllList{
        Node head;
        Node tail;
        int size; //to know that there are no more list 
        public DllList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node curr){
            curr.next =  head.next;
            curr.prev = head;
            head.next = curr;
            curr.next.prev = curr;
            this.size++;
        }

        public void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
            this.size--;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DllList> freq;
    int capacity;
    int minFreq; 
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freq = new HashMap<>();
        this.capacity= capacity;
    }
    
    private void update(Node node){
        int old = node.freq;
        DllList oldFrqList = freq.get(old);
        oldFrqList.removeNode(node);

        if(old == minFreq && oldFrqList.size == 0){
            //update minfreq 
            minFreq++;
        }
        int newFrq = old+1;
        node.freq = newFrq;
        DllList newFrqList = freq.getOrDefault(newFrq, new DllList());
        newFrqList.addToHead(node);
        freq.put(newFrq, newFrqList);

    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }else{
            if(map.size() == capacity){
                //full 
                DllList minFrqList = freq.get(minFreq);
                Node toRemove =minFrqList.tail.prev;
                minFrqList.removeNode(toRemove);
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key,value);
            minFreq = 1;
            DllList minFreqList = freq.getOrDefault(minFreq, new DllList());
            minFreqList.addToHead(newNode);
            freq.put(minFreq, minFreqList);
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