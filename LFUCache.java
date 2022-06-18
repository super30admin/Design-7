//All operations are constant time and space lookup
class LFUCache {

    class Node{
        int key;
        int value;
        Node next;
        Node prev;
        int freq;
        public Node(int key, int value)
        {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DLL{
        Node head,tail;
        int size;

        public DLL(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1);

            head.next = tail;
            tail.prev = head;
            this.size=0;
        }

        public void addToHead(Node n)
        {
            n.next = head.next;
            head.next = n;
            n.next.prev = n;
            n.prev = head;
            this.size++;
        }
        public void removeNode(Node n){
            n.next.prev = n.prev;
            n.prev.next = n.next;
            n.next = null;
            n.prev = null;
            this.size--;
        }
        public Node removeFromTail()
        {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }
    int capacity,min;
    HashMap<Integer,Node> nodeMap;
    HashMap<Integer,DLL> freqMap;
    public LFUCache(int capacity) {

        this.capacity = capacity;
        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!nodeMap.containsKey(key)) return -1;

        Node n = nodeMap.get(key);
        update(n);
        return n.value;
    }

    public void update(Node n){
        int freq = n.freq;
        DLL list = freqMap.get(freq);
        list.removeNode(n);
        if(min == freq && list.size == 0){
            min++;
        }
        n.freq++;
        freq = n.freq;
        if(!freqMap.containsKey(freq))
        {
            freqMap.put(freq,new DLL());
        }
        DLL newList = freqMap.get(freq);
        newList.addToHead(n);
        freqMap.put(freq,newList);
    }

    public void put(int key, int value) {
        if(nodeMap.containsKey(key))
        {
            Node n = nodeMap.get(key);
            n.value = value;
            update(n);
            return;
        }
        else
        {
            if(capacity==0) return;
            if(capacity==nodeMap.size())
            {
                DLL list = freqMap.get(min);
                Node removeNode = list.removeFromTail();
                nodeMap.remove(removeNode.key);
            }
            Node n = new Node(key,value);
            min = 1;
            DLL list = freqMap.getOrDefault(min, new DLL());
            list.addToHead(n);
            freqMap.put(min,list);
            nodeMap.put(key,n);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */