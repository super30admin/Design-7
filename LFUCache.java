public class LFUCache {
    
    /*Time complexity: O(1)
    Space complexity: O(N) where N is number of number of unique keys
    */

    class Node{
        int key;
        int value;
        int count;
        Node prev;
        Node next;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
    
    class DList{
        Node head;
        Node tail;
        int size;
        public DList(){
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            
        }
        
        public void addNode(Node node) {
            node.next = head.next;
            head.next = node;
            
            node.prev = head;
            node.next.prev = node;
            size++;
        }
        
        public void removeNode(Node node) {
            
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            
        }
        
        public Node removeLastNode() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }
    
    Map<Integer, Node> map;
    Map<Integer, DList> freqMap;
    int min;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            if(node != null)
                update(node);
            
            return node.value;
        }
        
        return -1;
        
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
        }
        else {
            if(capacity == 0)
                return;
            if(map.size() == capacity) {
                DList minList = freqMap.get(min);
                Node removed = minList.removeLastNode();
                map.remove(removed.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DList minList = freqMap.getOrDefault(min, new DList());
            minList.addNode(newNode);
            freqMap.put(min, minList);
            map.put(key, newNode);
        }
    }
    
    private void update(Node node) {
        DList oldList = freqMap.get(node.count);
        oldList.removeNode(node);
        if(oldList.size == 0 && node.count == min) {
            min++;
        }
        
        node.count++;
        DList newList = freqMap.getOrDefault(node.count, new DList());
        newList.addNode(node);
        freqMap.put(node.count, newList);
        
        map.put(node.key, node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */