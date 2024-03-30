class LFUCache {
    class Node {
        int key;
        int value;
        int count;
        Node next;
        Node prev;
        Node (int key, int value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
    class DoublyLinkedList {
        Node head;
        Node tail;
        int size;
        DoublyLinkedList() {
        this.head = new Node(-1,-1);
        this.tail = new Node(-1,-1);
        this.head.next = tail;
        this.tail.prev = head;
        }
        void addToHead(Node node) {
        node.next= head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
        size++;
        
    }

    void removeNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    Node removeTail() {
       Node toRemove = tail.prev;
       removeNode(toRemove);
       return toRemove;
    }
    }

    
    int min;
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer , DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.min = Integer.MAX_VALUE;
        
    }

    
    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);

update(node);
return node.value;
        
    }

    void update(Node node) {
        DoublyLinkedList oldlist = freqMap.get(node.count);
        oldlist.removeNode(node);
        if(node.count == min && oldlist.size == 0) {
            min++;
        }
        node.count++;
        DoublyLinkedList newList = freqMap.getOrDefault(node.count, new DoublyLinkedList());
        newList.addToHead(node);
        freqMap.put(node.count, newList);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        } else {
            if(capacity == map.size()) {
              DoublyLinkedList minFreq = freqMap.get(min);
              Node toRemove = minFreq.removeTail();
              map.remove(toRemove.key);
            }
            Node newNode = new Node(key,value);
            min = 1;
            DoublyLinkedList minFreq = freqMap.getOrDefault(min, new DoublyLinkedList());
            minFreq.addToHead(newNode);
            freqMap.put(1, minFreq);
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