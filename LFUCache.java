//TC: O(1) for all user actions.

class LFUCache {
    class Node{
        int key;
        int val;
        int count;
        Node next;
        Node prev;

        Node(int key, int val){
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;
        // MRU to LRU -> head to tail.
        DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }

        private void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }

        private Node removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;

            return node;
        }
    }

    int min;
    Map<Integer, Node> map;
    Map<Integer,DLList> freqMap;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.min = 1;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;

        Node curr = map.get(key);
        update(curr);
        return curr.val;
    }

    private void update(Node node){
        DLList oldList = freqMap.get(node.count);
        oldList.removeNode(node);

        if(node.count == min && oldList.size == 0){
            min++; // next min 
        }

        node.count++;
        DLList newList = freqMap.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        freqMap.put(node.count , newList);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node curr = map.get(key);
            update(curr);
            curr.val = value;
        }
        else{ //fresh node

            //capacity check
            if(capacity == map.size()){
                DLList minFreqList = freqMap.get(min);
                Node remove = minFreqList.removeNode(minFreqList.tail.prev);
                map.remove(remove.key);
            }
            // Fresh node is added
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            min = 1;
            DLList newList = freqMap.getOrDefault(1, new DLList());
            newList.addToHead(newNode);
            freqMap.put(1, newList);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */