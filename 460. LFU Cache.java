// Time Complexity: O(1)
// Space Complexity: O(n)
// Did this code successfully run on Leetcode: YES
// Any problem you faced while coding this: NO

class LFUCache {
    public class Node {
        int key, value;
        int cnt;
        Node next, prev;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.cnt = 1;
        }
    }
    
    public class DLList{
        Node head, tail;
        int size;
        public DLList(){
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        
        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }
        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        public Node removeLast(){
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }
    
    int capacity, min;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }
        if(capacity == 0) return;
        if(capacity == map.size()){
            DLList oldList = freqMap.get(min);
            Node oldNode = oldList.removeLast();
            map.remove(oldNode.key);
        }
        Node node = new Node(key, value);
        min = 1;
        DLList newlist = freqMap.getOrDefault(min, new DLList());
        newlist.addToHead(node);
        freqMap.put(min, newlist);
        map.put(key, node);
    }
    
    private void update(Node node) {
        DLList oldList = freqMap.get(node.cnt);
        if(oldList != null){
            oldList.removeNode(node);
        }
        if(min == node.cnt && oldList.size == 0) {
            min++;
        }
        node.cnt++;
        DLList newlist = freqMap.getOrDefault(node.cnt, new DLList());
        newlist.addToHead(node);
        freqMap.put(node.cnt, newlist);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */