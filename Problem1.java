//Time Complexity: O(1)
//Space Complexity: O(n)

class Node{
    int key, value;
    int freq = 1;
    Node next, prev;
    Node(int key, int value){
        this.key = key;
        this.value = value;
    }

    int getKey(){return key;}
    int getValue(){return value;}
    int getFreq(){return freq;}
    void setFreq(int freq){this.freq = freq;}
    void setVal(int val){this.value = val;}
}

class DLinkedList{        
    Node head, tail;
    int size;

    DLinkedList(){
        head = new Node(Integer.MIN_VALUE, Integer.MAX_VALUE);
        tail = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    
    // O(1)
    Node delete(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        size--;
        return node;
    }
    
    //O(1)
    void toHead(Node node){
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
        size++;
    }
    
    boolean isEmpty(){ return size==0;}
}


class LFUCache {
    
    HashMap<Integer, Node> map;
    TreeMap<Integer, DLinkedList> fmap;
    int size;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        fmap = new TreeMap<>();
        size = capacity;
    }
    
    //O(1)
    public int get(int key) {
        Node node = map.getOrDefault(key, null);
        if (node == null)
            return -1;

        // delte node from old frequency list
        DLinkedList li = fmap.get(node.freq);
        node = li.delete(node);
        if (li.isEmpty()) {
            fmap.remove(node.freq);
        }

        // increasing freq
        node.setFreq(node.getFreq() + 1);

        // adding node to new frequency list
        fmap.putIfAbsent(node.getFreq(), new DLinkedList());
        li = fmap.get(node.getFreq());
        li.toHead(node);

        return node.value;
    }

    //O(1)
    public void put(int key, int value) {
        if(size == 0)
            return;
        Node node = map.getOrDefault(key, null);
        if (node != null) {
            // changing value
            node.setVal(value);

            get(key);
            return;
        }

        
        if (size == map.size()) {
            DLinkedList li = fmap.get(fmap.firstKey());
            node = li.delete(li.tail.prev);
            map.remove(node.key);
            if (li.isEmpty()) {
                fmap.remove(fmap.firstKey());
            }
        }

        node = new Node(key, value);

        map.put(key, node);
        fmap.putIfAbsent(node.getFreq(), new DLinkedList());
        DLinkedList li = fmap.get(node.getFreq());
        li.toHead(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */