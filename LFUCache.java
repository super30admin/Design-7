import java.util.HashMap;
import java.util.Map;

class LFUCache {
    class Node{
        int key, val, cnt;
        Node next, prev;

        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DLList{
        Node head, tail;
        int size;

        public DLList(){
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addToHead(Node node){
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        public Node removeTailPrev(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    private Map<Integer, Node> map;
    private Map<Integer, DLList> fMap;
    int min, capacity;
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
        this.capacity = capacity;
    }

    private void update(Node node){
        // remove it from old frequency List and add to new frequency
        int oldCnt = node.cnt;
        DLList oldList = fMap.get(oldCnt);
        oldList.removeNode(node);

        if(oldCnt == min && oldList.size == 0){
            min++;
        }
        node.cnt++;
        int newCnt = node.cnt;
        // add to new List
        DLList newList = fMap.getOrDefault(newCnt, new DLList());
        newList.addToHead(node);
        fMap.put(newCnt, newList);
    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node = map.get(key);
        // update this node
        update(node);

        return node.val;
    }

    public void put(int key, int value) {
        if(this.capacity == 0){
            return;
        }
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }else{
            // fresh node
            if(capacity == map.size()){
                DLList minList = fMap.get(min);
                // remove the tail of this list
                Node toRemove = minList.removeTailPrev();
                map.remove(toRemove.key);
            }

            Node newNode = new Node(key, value);
            this.min = 1;
            DLList li = fMap.getOrDefault(1, new DLList());
            li.addToHead(newNode);
            fMap.put(1, li);
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