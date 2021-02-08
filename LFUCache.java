/**
Leet Code :YES
Time Complexity : O(1)
Space Complexity : 2*O(Capacity)

The idea is to use two maps (1 for key--> Node(key,val,freq))) and 2 for Freq ---> List(Node(key,val,freq)). The main issue is handling add and remove scenarios which takes lot of time and patience.
**/
class LFUCache {

    int capacity;
    int currSize;
    int currMin;
    Map<Integer,Node> cacheMap;
    Map<Integer,DLL> frequencyMap;
    
    public class Node{
        int key;
        int val;
        int freq;
        Node next;
        Node prev;
        public Node(int key, int val, int freq){
            this.key = key;
            this.val = val;
            this.freq = freq;
        }
    };
    
    public class DLL{
        Node  start;
        Node  end;
        int size;
        public  DLL(){
            this.start      = new Node(-1,0,0);
            this.end        = new Node(-1,0,0);
            this.start.next = end;
            this.end.prev   = start;
            this.size = 0;
        }
        
        private void addNode(Node node){
            node.next = start.next;
            node.prev = start;
            start.next.prev = node;
            start.next = node;
            size++;
        }
        
        private void removeNode(Node node){
            
            Node t = end;
            while(t.prev != start){
                if(t.key == node.key){
                    t.prev.next = t.next;
                    t.next.prev = t.prev;
                    break;
                }else{
                    t=t.prev;
                }
            }
            size--;
        }
        
        private Node removeLast(){
            if(end.prev != start){
                    Node node = end.prev;
                    
                    node.prev.next = end;
                    node.next.prev = node.prev;
                    size --;
                    return node;
            }
            return null;
        }
    }
    
    
   
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.currSize = 0;
        this.currMin  = Integer.MAX_VALUE;
        this.cacheMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(cacheMap.containsKey(key) && frequencyMap.containsKey(cacheMap.get(key).freq)){
                Node node = cacheMap.get(key);
                
                //Remove node from List
                DLL tmpList = frequencyMap.get(node.freq);
                tmpList.removeNode(node);
                
                
                if(currMin == node.freq)
                {
                    if (tmpList.size == 0)
                    {
                        currMin += 1;
                    }
                }else{
                    frequencyMap.put(node.freq,tmpList);    
                }
                
                
                
                //Add node in front of List with frequency + 1
                int updateFrequency = node.freq + 1;  
                if(frequencyMap.containsKey(updateFrequency)){
                    DLL tmpList1 = frequencyMap.get(updateFrequency);
                    tmpList1.addNode(new Node(node.key,node.val,node.freq + 1));
                    frequencyMap.put(updateFrequency,tmpList1);
                }else{
                    DLL tmpList1 = new DLL();
                    tmpList1.addNode(new Node(node.key,node.val,node.freq + 1));
                    frequencyMap.put(updateFrequency,tmpList1);
                }
            
                //Update Original Cache Map
                cacheMap.put(key,new Node(node.key,node.val,node.freq + 1));
            
            //Return the Cache Map Key
            return cacheMap.get(key).val;
        }else{
            //No cache Map
            return -1;
        }
    }
    
    public void put(int key, int value) {
        if(cacheMap.containsKey(key))
            return;
        
        if(currSize == capacity){
            
            //Remove old frequently used node and update cache
            DLL tmpList = frequencyMap.get(currMin);
            Node last = tmpList.removeLast();
            
            if(tmpList.size != 0){
                frequencyMap.put(currMin,tmpList);    
            }    
            
            cacheMap.remove(last.key);
            cacheMap.put(key,new Node(key,value,1));
            
            if(frequencyMap.containsKey(1)){
                DLL tmpList1 = frequencyMap.get(1);
                tmpList1.addNode(new Node(key,value,1));
                frequencyMap.put(1,tmpList1);
            }else{
                DLL tmpList1 = new DLL();
                tmpList1.addNode(new Node(key,value,1));
                frequencyMap.put(1,tmpList1);
            }
        }else{
            //No need to remove anything simply add new node in cache
            Node node = new Node(key,value,1);
            cacheMap.put(key,node);
            if(frequencyMap.containsKey(1)){
                DLL tmpList = frequencyMap.get(1);
                tmpList.addNode(node);
                frequencyMap.put(1,tmpList);
            }else{
                DLL tmpList = new DLL();
                tmpList.addNode(node);
                frequencyMap.put(1,tmpList);
            }
            currSize++;
        }
        currMin = 1;
    }
}

/*
["LFUCache","put","put","get","put","get","get","put","get"]
[[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1]]


*/

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
