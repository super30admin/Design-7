// LFU Cache (https://leetcode.com/problems/lfu-cache/)

// Time Complexity : O(1)
// Space Complexity : O(capacity)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
// we use doubly linked lists for each frquencies to store each node
// if capacity if full, we remove last node from least frquency

class LFUCache {
    class Node{
        int key;
        int value;
        int count;
        Node next;
        Node prev;
        public Node(int key, int value){
            this.key= key;
            this.value=value;
            this.count = 1;
        } 
    }
    class DDL{
        Node head;
        Node tail;
        int size;
        public DDL(){
            this.head= new Node(-1,-1);
            this.tail=new Node(-1,-1);
            this.head.next=tail;
            this.tail.prev=head;
        }
        private void addToHead(Node node){
            node.prev=head;
            node.next= head.next;
            this.head.next= node;
            node.next.prev=node;
            this.size++;
        }
        private void removeNode(Node node){
            node.next.prev= node.prev;
            node.prev.next= node.next;
            this.size--;
        }
        private Node removeTailPrev(){
            Node tailPrev= this.tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }
    HashMap<Integer, Node> map;
    HashMap<Integer,DDL> fmap;
    int capacity;
    int minFreq; 
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.fmap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node current = map.get(key);
        update(current);
        return current.value;
    }
    private void update(Node node){
        DDL currentList = fmap.get(node.count);
        currentList.removeNode(node);
        if(node.count == minFreq && currentList.size==0) minFreq++;
        node.count++;
        DDL newList = fmap.getOrDefault(node.count, new DDL());
        newList.addToHead(node);
        fmap.put(node.count, newList);
    }
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value=value;
            update(node);
        }
        else{
            if(this.capacity==map.size()){
                DDL minFreqList= fmap.get(minFreq);
                Node toRemove = minFreqList.removeTailPrev();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key,value);
            minFreq=1;
            map.put(key,newNode);
            DDL minFreqList = fmap.getOrDefault(1, new DDL());
            minFreqList.addToHead(newNode);
            fmap.put(1, minFreqList);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */