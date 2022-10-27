class LFUCache {
    class Node
    {
        int key;
        int value;
        int count;
        Node next;Node prev;
 
        public Node(int key, int value)
        {
            this.key = key;
            this.value = value;
            this.count = 1;
        }  
    }
     class DDList
     {
         Node head;
         Node tail;
         int size;
 
         public DDList(){
           this.head = new Node(-1,-1);
           this.tail = new Node(-1,-1);
           this.head.next = tail;
           this.tail.prev = head;
         }
         public void addToHead(Node node)
         {
         node.next = head.next;
         head.next.prev = node;
         head.next = node;
         node.prev = head;
         size++;
         
         }
         public void removeNode(Node node)
         {
         node.next.prev = node.prev;
         node.prev.next = node.next;
         
         }
         public Node removeLast()
         {
             Node toRemove = tail.prev;
             removeNode(toRemove);
             return toRemove;//returing so that in map also we can remove this
 
         }
     }
     int min;
     int capacity;
     HashMap<Integer,Node> map;
     HashMap<Integer, DDList> freqMap;    
 
     public LFUCache(int capacity) {
         this.capacity = capacity;
         this.map = new HashMap<>();
         this.freqMap = new HashMap<>();
     }
     
     public int get(int key) {//o(1)
         if(!map.containsKey(key)) return -1;
         Node node = map.get(key);
         update(node);
         return node.value;
     }
     
     public void put(int key, int value) {//o(1)
         //if node already exsist
         if(map.containsKey(key))
         {
             Node node = map.get(key);
             node.value = value;
             update(node);
         }
         else{
             //fresh
             if(capacity == 0) return;
             if(capacity == map.size())///capactiy full
             {
                 DDList minFreq = freqMap.get(min);
                 Node toRemove = minFreq.removeLast();
                 map.remove(toRemove.key);
             }
             Node newNode = new Node(key,value);
             min = 1;
             map.put(key,newNode);
             DDList minList = freqMap.getOrDefault(1, new DDList());
             minList.addToHead(newNode);
             freqMap.put(1,minList);
 
 
         }
         
     }
     private void update(Node node)
     {
         //inc feq by 1
         int cnt = node.count;
 
         //change its positio in DLList
         DDList oldList = freqMap.get(cnt);
         oldList.removeNode(node);
         if(cnt == min && oldList.size == 0) min++;
         node.count = cnt+1;
         DDList newList = freqMap.getOrDefault(node.count, new DDList());
         newList.addToHead(node);
         freqMap.put(node.count, newList);    
     }
 }
 
 /**
  * Your LFUCache object will be instantiated and called as such:
  * LFUCache obj = new LFUCache(capacity);
  * int param_1 = obj.get(key);
  * obj.put(key,value);
  */