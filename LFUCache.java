package Design-7;

public class LFUCache {

    class Node{
        int key;
        int val;
        int freq;
        Node prev;
        Node next;
        public Node(int key, int val)
        {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }
    
    class DLList
    {
        Node head;
        Node tail;
        int size;
        public DLList()
        {
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }
        
        public void add(Node node)
        {
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;
            size++;
        }
        
        public void remove(Node node)
        {
           node.prev.next = node.next;
           node.next.prev = node.prev; 
           size--; 
        }
        
        public Node removeLast()
        {
            Node tailPrev = tail.prev;
            remove(tailPrev);
            return tailPrev;
        }
    }
    // LFU class
    // Frequency Map of freq : DL of elements with that freq
    Map<Integer,DLList> freqMap;
    
    //Map of key and nodes
    Map<Integer,Node> map;
    
    //capacity
    int capacity;
    
    // minimum frequency
    int minFreq;
    public LFUCache(int capacity) {
      map = new HashMap<>();
      freqMap = new HashMap<>();
      this.capacity = capacity;  
    }


    //O(1)
    public int get(int key) {
       
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        updateNode(node);
        return node.val;
    }
    
    //O(1)
    public void put(int key, int value) {
        
        if(map.containsKey(key))
        {
            Node node = map.get(key);
            node.val = value;
            updateNode(node);
        }else{
            
            if(capacity == 0) return;
            
            if(capacity == map.size())
            {
                DLList minList = freqMap.get(minFreq);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
            }
            // min is 1
            Node newNode = new Node(key,value);
            map.put(key,newNode);
            minFreq = 1;
            DLList newList = freqMap.getOrDefault(minFreq,new DLList());
            newList.add(newNode);
            freqMap.put(minFreq,newList);
            
        }
        
    }
    
    //O(1)
    private void updateNode(Node node)
    {
        
           int freq = node.freq;
           DLList oldList = freqMap.get(freq);
           oldList.remove(node);
         
           if(minFreq == freq && oldList.size == 0 ) minFreq++;
           
           node.freq++;
         
           DLList newList = freqMap.getOrDefault(node.freq,new DLList()); 
           newList.add(node); 
           freqMap.put(node.freq,newList); 
                    
    }

 

    
}
