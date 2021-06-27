//Time Complexity: O(1) all operations
//Space Complexity: O(n) asymptotic

import java.util.HashMap;

class LFUCache {
    class Node{
        int key; int val;
        int cnt;
        Node next; Node prev;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }
    
    class DLLlist{
        Node head; Node tail; int size;
        public DLLlist(){
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        public Node removeLast(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLLlist> freq;
    int cacheSize; int min; int capacity;
    
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node != null){
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void update(Node node){
        DLLlist oldList = freq.get(node.cnt);
        oldList.removeNode(node);
        if(oldList.size == 0 && node.cnt == min) min++;
        node.cnt++;
        DLLlist newList = freq.getOrDefault(node.cnt, new DLLlist());
        newList.addToHead(node);
        freq.put(node.cnt, newList);
    }
    
    public void put(int key, int value) {
        Node node;
        if(map.containsKey(key)){
            node = map.get(key);
            node.val = value;
            update(node);
        } else {
            node = new Node(key, value);
            if(capacity == 0) return;
            if(cacheSize == capacity){
                DLLlist minList = freq.get(min);
                Node toBeRemoved = minList.removeLast();
                map.remove(toBeRemoved.key);
                cacheSize--;
            }
            DLLlist li = freq.getOrDefault(1, new DLLlist());
            li.addToHead(node);
            freq.put(1, li);
            map.put(key, node);
            min = 1;
            cacheSize++;
        }
    }
}