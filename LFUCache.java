// O(1) time and space complexity

class LFUCache {
    TreeMap<Integer,Deque<Integer>> dq;
    HashMap<Integer,Integer> map;
    HashMap<Integer,Integer> fre;
    int size;int c=0;
    public LFUCache(int capacity) {
        dq=new TreeMap<Integer,Deque<Integer>>();
        map=new HashMap<Integer,Integer>();
        fre=new HashMap<Integer,Integer>();
        size=capacity;
    }
    
    public int get(int key) 
    {
        if(map.containsKey(key))
        {
            int redirect=fre.get(key);
            dq.get(redirect).remove(key);
             if(dq.get(redirect).size()==0)
            {
                dq.remove(redirect);
            }
            if(dq.containsKey(redirect+1)==false)
            {
                dq.put(redirect+1,new ArrayDeque<Integer>());
            }
            dq.get(redirect+1).offerLast(key);
            fre.put(key,redirect+1);
            return map.get(key);
        }
        return -1;
        
    }
    
    public void put(int key, int value)
    {
        if(map.containsKey(key))
        {
             int redirect=fre.get(key);
             dq.get(redirect).remove(key);
            if(dq.get(redirect).size()==0)
            {
                dq.remove(redirect);
            }
            if(dq.containsKey(redirect+1)==false)
            {
                dq.put(redirect+1,new ArrayDeque<Integer>());
            }
            dq.get(redirect+1).offerLast(key);
            fre.put(key,redirect+1);
            map.put(key,value);
        }
        else
        {
            if(size==0)
            {
                return;
            }
            if(c==size)
            {
            c--;
            int redirect=dq.firstKey();
            int kapa=dq.get(redirect).getFirst();
            dq.get(redirect).pollFirst();
            if(dq.get(redirect).size()==0)
            {
                 dq.remove(redirect);
            }
            fre.remove(kapa);
            map.remove(kapa);
           }
            int redirect=1;
             c++;
            if(dq.containsKey(redirect)==false)
            {
                dq.put(redirect,new ArrayDeque<Integer>());
            }
            dq.get(redirect).offerLast(key);
            fre.put(key,redirect);
            map.put(key,value);
        }

    }
}