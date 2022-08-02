//Time -O(1)
//space -O(1)
class LFUCache {
    class Node {
        Node prev;
        Node next;
        int val;
        int key;
        int count;

        public Node(int key, int val){
            this.val = val;
            this.count = 1;
            this.key = key;
        }

    }
    class DLLlist{
        Node tail;
        Node head;
        int size;

        public DLLlist(){
            head = new Node(-1,-1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        private void addtoHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }
        private void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        }

        private Node removeTail(){
            Node tailprev = tail.prev;
            removeNode(tailprev);
            return tailprev;

        }

    }

    HashMap<Integer, DLLlist> freq;
    HashMap<Integer, Node> map;
    int capacity;
    int min;


    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.freq = new HashMap<>();
        this.map = new HashMap<>();

    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        else{
            Node n = map.get(key);
            update(n);
            return n.val; 
        }

    }

    public void put(int key, int value) {
        if(capacity ==0) return;
        if(map.containsKey(key)) {
            Node n = map.get(key);
            n.val =value;
            update(n);
        }else{
            if(capacity == map.size()){
                DLLlist minlist = freq.get(min);
                Node toRemove = minlist.removeTail();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min =1;
            DLLlist dlist = freq.getOrDefault(1,new DLLlist());
            dlist.addtoHead(newNode);
            freq.put(1,dlist);
            map.put(key, newNode);
        }

    }

    private void update(Node node){
        int oldCount = node.count;
        DLLlist oldList = freq.get(oldCount);
        oldList.removeNode(node);
        if(oldCount == min && oldList.size ==0) min++;
        node.count++;
        int newCount = node.count;
        DLLlist newList = freq.getOrDefault(newCount, new DLLlist());
        newList.addtoHead(node);
        freq.put(newCount, newList);

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */ 