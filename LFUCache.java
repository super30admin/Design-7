// Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

// Time Complexity : O(1)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach in three sentences only
/*
 * Here, create class node and DLList with methods addToHead which adds node to head, removeNode which removes node and removeTailPrev which
 * removes node prev to tail. Take 2 hashmaps, in one store key and node and in another store frequency and double linked list. In get function
 * if key is not present in map return -1. If not get the node use update method where the node is taken from its previous frequency and added to
 * new frequency list in frequency map and if prev freq list is empty increase min. In put function, if its not a fresh node get the node and
 * update key and use update function. If its a fresh node, if capacity == map.size() then get the list of min freq and remove the prev node to
 * tail and remove it from map also. Add the new node to the min list and new node to map.
 */
class LFUCache {
    class Node{
        int key;
        int val;
        int count;
        Node next;
        Node prev;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }
    class DLList{
        Node head, tail;
        int size;
        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
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
    
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> fmap;
    int min;
    int capacity;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fmap = new HashMap<>();
    }
    private void update(Node node){
        //remove node from prev freq and add it to next one
        int oldCount = node.count;
        //remove node
        DLList oldList = fmap.get(oldCount);
        oldList.removeNode(node);
        if(oldCount == min && oldList.size == 0){
            this.min = this.min + 1;
        }
        node.count++;
        int newCount = node.count;
        DLList newList = fmap.getOrDefault(newCount, new DLList());
        newList.addToHead(node);
        fmap.put(newCount, newList);
    }
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(this.capacity == 0) return;
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        else{
            //fresh
            //capacity is full
            if(this.capacity == map.size()){
                DLList minList = fmap.get(min);
                //remove tail of minList
                Node toRemove = minList.removeTailPrev();
                map.remove(toRemove.key); 
            }
            Node newNode = new Node(key, value);
            this.min = 1;
            DLList newList = fmap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            fmap.put(min, newList);
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