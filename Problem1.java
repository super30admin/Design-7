//GET O(1)
//PUT average case O(1), when capacity is reached O(n) search from tail

class LFUCache {
    
    Map<Integer, Integer> countFreq;
    Map<Integer, DLL> nodeMap;
    
    class DLL{
        DLL prev;
        DLL next;
        int key;
        int val;
        
        DLL(int k, int v)
        {
            prev = null;
            next = null;
            key = k;
            val = v;
        }
    }
    
    DLL head = new DLL(-1,-1);
    DLL tail = new DLL(-1,-1);
    
    int cap;

    public LFUCache(int capacity) {
        this.cap = capacity;
        nodeMap = new HashMap<Integer, DLL>();
        countFreq = new HashMap<Integer, Integer>();
        
        head.next = tail;
        tail.prev = head;
    }
    
    private void removeNode(DLL node)
    {
        DLL prv = node.prev;
        DLL nxt = node.next;
        
        prv.next = nxt;
        nxt.prev = prv;
    }
    
    private void addToHead(DLL node)
    {
        DLL nxt = head.next;
        
        head.next = node;
        node.prev = head;
        node.next = nxt;
        nxt.prev = node;
    }
    
    public int get(int key) {
        if(this.cap == 0) return -1;
        
        if(nodeMap.containsKey(key))
        {
            DLL node = nodeMap.get(key);
            countFreq.put(key, countFreq.get(key) + 1);
            
            removeNode(node);
            addToHead(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(this.cap == 0) return;
        
        if(nodeMap.containsKey(key))
        {
            countFreq.put(key, countFreq.get(key) + 1);
            DLL node = nodeMap.get(key);
            node.val = value;
            
            removeNode(node);
            addToHead(node);
        }
        else
        {
            if(nodeMap.size() == cap)
            {
                Set<Integer> st = new HashSet<>();

                int minn = Integer.MAX_VALUE;

                for(int k : countFreq.keySet())
                {
                    if(countFreq.get(k) <= minn)
                        minn = countFreq.get(k);
                }

                for(int k : countFreq.keySet())
                {
                    if(countFreq.get(k) == minn)
                        st.add(k);
                }

                DLL node = tail.prev;

                while(!st.contains(node.key) && node != head)
                    node = node.prev;

                nodeMap.remove(node.key);
                countFreq.remove(node.key);
                removeNode(node);
            }
            
            DLL node = new DLL(key, value);
            addToHead(node);
            nodeMap.put(key, node);
            countFreq.put(key, 1);
        }
        
    }
}
