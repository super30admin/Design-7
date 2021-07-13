//Time Complexity : O(1)
//Space Complexity : O(n)
//Did this code successfully run on Leetcode :yes
//Any problem you faced while coding this : no
class LFUCache {
    
    class Node{
        
        Node prev;
        Node next;
        int key;
        int value;
        int cnt;
        
        public Node(int key, int value){
            
            this.key = key;
            this.value = value;
            this.cnt = 1;
            
        }
        
    }
    class DLList{
        Node head;
        Node tail;
        int size;
        
        public DLList(){
            
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        
    private void addToHead(Node node){
        
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
        size++;
    }
    
   private void removeNode(Node node){
        
        node.prev.next = node.next;
        node.next.prev = node.prev;
       size --;
    }
    
   private Node removeLast(){
        
        Node temp = tail.prev;
         removeNode(temp);
       return temp;
        
    }
    }
    
    HashMap<Integer,DLList> frequency;
    HashMap<Integer,Node> map1;
    int capacity;
    int min;
    public LFUCache(int capacity) {
        
        this.capacity = capacity;
        frequency = new HashMap<>();
        map1 = new HashMap<>();
    }
    
    public int get(int key) {
        
        if(!map1.containsKey(key)){
            
            return -1;
        }
        Node result = map1.get(key);
        updateNode(result);
        return result.value;
        
    }
    
    public void put(int key, int value) {
        if(capacity == 0){
            return;
        }
    
        if(map1.containsKey(key)){
            
            Node temp = map1.get(key);
            temp.value = value;
            updateNode(temp);
            
        }
        else{
            if(map1.size() == capacity){
                
                DLList minList = frequency.get(min);
                Node toRemove = minList.removeLast();
                map1.remove(toRemove.key);
                
                
            }
            
        
        
       
        Node curr = new Node(key,value);
        min = 1;
        DLList toAddList = frequency.getOrDefault(1, new DLList());
        toAddList.addToHead(curr);
        frequency.put(1,toAddList);
        map1.put(key,curr);
        
        }
        
    }
    
    private void updateNode(Node node){
        
        int count = node.cnt;
        DLList oldList = frequency.get(count);
        oldList.removeNode(node);
        if(count == min && oldList.size == 0){
            min = min + 1;
        }
        count ++;
        node.cnt = count;
        DLList newList = frequency.getOrDefault(count,new DLList());
        newList.addToHead(node);
        frequency.put(count, newList);
        
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */