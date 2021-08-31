//Time Complexity :o(1)
//Space Complexity : o(N)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No
class LFUCache {
    
    class ListNode
    {
        int key,value,count;
        ListNode prev,next;
        
        public ListNode(int key,int value)
        {
            this.key=key;
            this.value=value;
            this.count=1;
            
        }
    }
    
    class DLList
    {
        ListNode head,tail;
        int size;
        
        public DLList()
        {
            head=new ListNode(-1,-1);
            tail=new ListNode(-1,-1);
            head.next=tail;
            tail.prev=head;
        }
        
        public void addToHead(ListNode node)
        {
            node.next=head.next;
            node.prev=head;
            node.next.prev=node;
            head.next=node;  
            size++;
        }
        
        public void removeNode(ListNode node)
        {
            node.prev.next=node.next;
            node.next.prev=node.prev;
            size--;
        }
        
        public ListNode removeLastNode()
        {
            ListNode tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }
    
    HashMap<Integer,ListNode> keyMap;
    int capacity;
    HashMap<Integer,DLList> freqMap;
    int minFreq;

    public LFUCache(int capacity) 
    {
        this.capacity=capacity;
        this.keyMap=new HashMap<>();
        this.freqMap=new HashMap<>();
    }
    
    public int get(int key)
    {
        if(!keyMap.containsKey(key))
        {
            return -1;
        }
        
        ListNode node=keyMap.get(key);
        updateFreq(node);
        return node.value;
    }
    
    public void put(int key, int value)
    {
         if(capacity == 0)
         {
            return;
         }
        
        if(keyMap.containsKey(key))
        {
          ListNode node = keyMap.get(key);
           updateFreq(node);
            node.value=value;
        }
        else
        {
            if(capacity==keyMap.size())
            {
               DLList minList=freqMap.get(minFreq);
               ListNode toRemove= minList.removeLastNode();
                keyMap.remove(toRemove.key);
            }
            ListNode newNode = new ListNode(key,value);
            minFreq = 1;
            keyMap.put(key,newNode);
            
            DLList minList=freqMap.getOrDefault(minFreq,new DLList());
            minList.addToHead(newNode);
            freqMap.put(minFreq,minList);
        }
    }
    
    private void updateFreq(ListNode node) 
    {
        DLList oldDLList=freqMap.getOrDefault(node.count,new DLList());
        oldDLList.removeNode(node);
        
        if(oldDLList.size==0)
        {
            if(minFreq==node.count)
            {
                minFreq++;
            }
            freqMap.remove(node.count);
        }
        
        node.count++;
        
        DLList newDLList=freqMap.getOrDefault(node.count, new DLList());
        newDLList.addToHead(node);
        freqMap.put(node.count,newDLList);
        
        
    }
}
