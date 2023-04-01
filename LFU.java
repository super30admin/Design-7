//Time Complexity : O(1)
//Space Complexity : O(capacity)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

/**
 * Maintain two maps. One for the key to (freq, value) and one for frequencies
 * (freq -> set of keys). So when get is called check if there is a key in the
 * cache. If so, get its frequency and fetch the set of keys having that
 * frequency. Remove this key from that list and if that list becomes empty
 * remove that frequency from the frequencies map. Then update the key in the
 * cache with frequency+1 and value and also put the frequency with the key in
 * its set.
 * 
 * When put is called, check if there exists a key already in cache. If so,
 * update the key with the new value and old frequency and then call get of this
 * key. It will update the frequency accordingly. If the capacity is already
 * full, then get the min frequency from the frequencies map and remove it from
 * cache as well. Then insert the new key with frequency as 1. Update min to 1.
 *
 */

class LFUCache {

   class Node {
        int key;
        int value;
        int count;
        Node prev; Node next;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.count = 1;
        }  
    }
    
    class DLList {
        Node head;
        Node tail;
        int size;
        public DLList() {
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = tail;
            this.tail.prev = head;     
        }
        
        private void addtoHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        
        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        private Node removeLast(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node); //remove it from previous old prev list and add it to new freq list
        return node.value;
    }
    
    private void update(Node node){
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount);
        oldList.removeNode(node);
        if(min == oldCount && oldList.size == 0) min++;
        node.count = node.count+1;
        int newCount = node.count;
        DLList newList = freqMap.getOrDefault(newCount, new DLList());
        newList.addtoHead(node);
        freqMap.put(newCount, newList);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            node.value = value;
        }else{
            if(capacity == 0) return;
            if(map.size() == capacity){
                //remove node
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key,value);
            min = 1;
            DLList newList = freqMap.getOrDefault(1, new DLList());
            newList.addtoHead(newNode);
            freqMap.put(1, newList);
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
     
