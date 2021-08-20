// Time Complexity : O(1)
// Space Complexity : O(n)

class LFUCache {
    class Node{
        Node prev;
        Node next;
        int cnt;
        int key; int value;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            cnt = 1; 
        }
    }
    class DLL{
        Node head;
        Node tail;
        int size; 
        public DLL(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1); 
            head.next = tail;
            tail.prev = head;
        }

        public void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        public void addNode(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node; 
            size++;
        }

        public Node removeLast(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev; 
        }
    }

    HashMap<Integer, Node> map; 
    HashMap<Integer, DLL> freqMap;
    int min; 
    int capacity;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>(); 
    }
    
    public int get(int key) {
        if(!map.containsKey(key))
             return -1;
        Node node = map.get(key); 
        update(node); 
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key); 
            node.value = value; 
            update(node); 
        }
        else{
            if(capacity == 0)
                return;
            if(capacity == map.size()){
                DLL minf = freqMap.get(min); 
                Node nodeToRemove = minf.removeLast();
                map.remove(nodeToRemove.key); 
            }
            Node node = new Node(key,value);
            min = 1;
            DLL list = freqMap.getOrDefault(1, new DLL());
            list.addNode(node);
            freqMap.put(min, list);
            map.put(key, node); 
        }
    }
    
    private void update(Node node){
        int count = node.cnt;
        DLL oldList = freqMap.get(count);
        oldList.removeNode(node);
        if(node.cnt == min && oldList.size == 0)
            min++;
        count++;
        node.cnt = count;
        DLL newList = freqMap.getOrDefault(node.cnt, new DLL());
        newList.addNode(node);
        freqMap.put(count, newList);
        map.put(node.key, node); 
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */