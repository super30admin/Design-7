import java.util.HashMap;

// Time Complexity : O(1)
// Space Complexity :O(n)

class LFUCache {

    class Node{
        int key;
        int val;
        int freq;
        Node next;
        Node prev;

        public Node(int key,int val){
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;

        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = tail;
            this.tail.prev = head;
        }

        public void  addToHead(Node node){
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            node.prev = head;
            size++;
        }

        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        public Node removeLast(){
            Node toRemove = tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    }

    int min, capacity;
    HashMap<Integer,DLList> freqMap;
    HashMap<Integer,Node> map;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }

    public void put(int key, int value) {

        // if node already exists
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        else {
            // its fresh node
            if (capacity == 0) return;
            if(capacity == map.size()) {
                DLList minFreq = freqMap.get(min);
                Node toRemove = minFreq.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key,value);
            min = 1;
            map.put(key,newNode);
            DLList minFreqList = freqMap.getOrDefault(1, new DLList());
            minFreqList.addToHead(newNode);
            freqMap.put(1,minFreqList);
        }

        // check the capactiy
    }

    private void update(Node node){
        // increase the freq by 1
        int cnt = node.freq;

        // change position of node in DLList in the freqMap
        DLList oldList = freqMap.get(cnt);
        oldList.removeNode(node);
        if(cnt == min && oldList.size == 0) min++;
        node.freq = cnt+1;
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        newList.addToHead(node);
        freqMap.put(node.freq, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */