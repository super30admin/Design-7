//TC : For both put and get operations - O(1)

//SC : O(n) - Size of cache

class LFUCache {

    class Node{
        int key;
        int value;
        int freq;  
        Node prev, next;
        
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            freq = 1;
        }
    }
    
    class DLL{
        Node head, tail;
        int length; 
        public DLL(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);            
            this.head.next = tail;
            this.tail.prev = head;
        }
        public void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            length--;
        }
        public Node removeTailNode(){
            //Remove least frequently used node
            Node temp = tail.prev;
            removeNode(temp);
            return temp;
        }
        public void addTOHead(Node node){
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            node.prev = head;
            length++;
        }
    }
    
    int capacity; 
    int minFreq;
    Map<Integer, Node> map;
    Map<Integer, DLL> freqMap;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key))   return -1;
        
        Node node = map.get(key);
        
        update(node);
        return node.value;
    }
    
    public void update(Node node){
        //Get the frequency of node
        int OldFr = node.freq;
        
        //Update the freqMap by putting the node in its updated frequency
        DLL list = freqMap.get(OldFr);
        list.removeNode(node); // Remove node from old list
        
        if(OldFr == minFreq && list.length == 0)    minFreq++;
        
        
        node.freq = OldFr + 1;
        DLL newList = freqMap.getOrDefault(node.freq, new DLL());
        newList.addTOHead(node);
        freqMap.put(node.freq, newList);
            
            
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            update(node);// Updating node from old freqList to new freqList
        }else{if(this.capacity == 0)    return;
            
            if(this.capacity == map.size()){
                //Get minFreq List 
                DLL minList = freqMap.get(minFreq); // List with minimum freq
                Node temp = minList.removeTailNode();
                map.remove(temp);//REMOVING FROM REGULAR MAP TO ADDD NEW NODE IN CACHE
            }
            
            Node newNode = new Node(key, value);
            minFreq = 1;
            map.put(key, newNode);
            DLL newMinList = freqMap.getOrDefault(1, new DLL()); // List with minimum freq
            newMinList.addTOHead(newNode);
            freqMap.put(1, newMinList);
            
        }
    }
}

