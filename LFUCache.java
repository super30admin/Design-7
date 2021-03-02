/*
time complexity: O(1)
space complexity: O(1)
*/
class Node{
    int key;int value;
    Node next;Node prev;
    int count;
    Node(int key,int value){
        this.key = key;
        this.value = value;
        this.count =1;
    }
}
class DoublyLinkedList{
    Node head;
    Node tail;
    int size;
    DoublyLinkedList(){
        this.head = new Node(-1,-1);
        this.tail = new Node(-1,-1);
        this.head.next = tail;
        this.tail.next = head;
    }
    
    public void insertFront(Node node){
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
class LFUCache {
    int capacity; //maintains the capacity
    int min; //maintains the min for entire LFU cache
    
    HashMap<Integer, Node> map;  //used to keey track of freq of each node
    HashMap<Integer, DoublyLinkedList> fMap; //used to break tie and make use of LRU policy in case of tie
    
    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
        this.capacity = capacity;
        this.min = 1; //set to 1
    }
    
    public int get(int key) {
        //map does not contain given key return -1
        if(!this.map.containsKey(key)){
            return -1;
        }
        //else, update the node with given key in frequency map
        Node node = this.map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        //if the given key is present in map, update its value and also update the fmap
        if(this.map.containsKey(key)){
            Node node = this.map.get(key);
            node.value = value;
            update(node);
        }else{
            //if no nodes are present in map
            if(capacity == 0)return;
            //if cpacity is reached, evict the LFU
            
            if(this.capacity == this.map.size()){
                //get the least used node list from fmap 
                DoublyLinkedList list = this.fMap.get(min);
                Node remove = list.removeLast(); //remove the last node
                this.map.remove(remove.key); // also remve from map
            }
            Node newnode = new Node(key,value); 
            min = 1;
            DoublyLinkedList newlist = this.fMap.getOrDefault(min,new DoublyLinkedList());
            newlist.insertFront(newnode);
            this.fMap.put(min,newlist);
            this.map.put(key,newnode);
            
        }
    }
    //get the count of node and search that count in map
    //get the oldlist associated with that count
    //check if minimum is equal to count and also if list.size == 0, then update min++
    //do count++ and add node to new list 
    //
    private void update(Node node){
        //update the node in freq map
        int count = node.count;
        DoublyLinkedList list = this.fMap.get(count);
        list.removeNode(node);
        if(count == min && list.size == 0)min++;
        node.count++;
        DoublyLinkedList newlist = this.fMap.getOrDefault(node.count,new DoublyLinkedList());
        newlist.insertFront(node);
        this.fMap.put(node.count,newlist);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */