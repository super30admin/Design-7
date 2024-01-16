//T.C all operations O(1)
//S.C O(2n) for the map, freq map
//Successfull executed in leetcode : yes

//Solution: Always give priority to the T.C of user operations over the S.C.
// get op: Use a map of k,v as key, node for O(1)
// put op: maintain freq map of k,v as freq, DLList - this is to maintain order of LRU
import java.util.HashMap;
import java.util.Map;

class LFUCache {
    //For get(n) in O(1)
    Map<Integer, Node> map;

    //For LFU with LRU ordered list
    Map<Integer, DLList> fmap;

    //Needed for removing LFU when cache is full 
    int minFreq;

    int capacity;

    class Node{
        int key, value, freq;
        Node next, prev;

        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.freq=1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;

        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            this.size++;
        }

        public void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        }

        //The return value is needed to remove the node from prev freq 
        public Node removeFromTail(){
            Node node = tail.prev;
            removeNode(node);
            return node;
        }
    }
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.fmap  = new HashMap<>();

        this.minFreq = 0;
        this.capacity = capacity;
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        //need to update the freq in freq map
        Node node = map.get(key);
        int res = node.value;
        update(node);
        return res;
    }

    //Update fMap - remove from old freq list and add into new freq list
    public void update(Node node){
        int prevFreq = node.freq;
        DLList prevList = fmap.get(prevFreq);
        prevList.removeNode(node);
        //It could be that this node is the only one for that freq, so need to update minFreq
        if(prevList.size==0 && minFreq == prevFreq)
            minFreq++;

        int newFreq = prevFreq+1;
        node.freq = newFreq;
        DLList newList = fmap.getOrDefault(newFreq,new DLList());
        newList.addToHead(node);
        fmap.put(newFreq, newList);
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            update(node);//update freq map 
        } else {
            //before adding new node, check for capacity
            if(map.size() == capacity){
                DLList minFreqList = fmap.get(minFreq);
                Node node = minFreqList.removeFromTail();
                map.remove(node.key);
            }
            Node node = new Node(key, value);
            map.put(key, node);
            minFreq = 1;
            DLList minFreqList = fmap.getOrDefault(minFreq, new DLList());
            minFreqList.addToHead(node);
            fmap.put(minFreq,minFreqList);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */