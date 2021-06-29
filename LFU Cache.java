// Time Complexity : O(N*M + NlogK)
// Space Complexity : O(N*N*M)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach

class DLL{
    int key,value,freq;
    DLL next;
    DLL prev;
    public DLL(int key,int value,int freq){//O(1)
        this.key = key;
        this.value = value;
        this.freq = freq;
        this.next = null;
        this.prev = null;
    }
}
class LFUCache {
    int capacity;
    HashMap<Integer,DLL> cache;
    DLL head;
    DLL tail;

    public LFUCache(int capacity) {//O(1)
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new DLL(-1,-1,-1);
        this.tail = new DLL(-1,-1,-1);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    private void addNode(DLL node,DLL startNode){
        int freq = node.freq;
        while(startNode!= tail && freq >= startNode.freq){
            startNode = startNode.next;
        }
        node.next = startNode;
        node.prev = startNode.prev;
        startNode.prev.next = node;
        startNode.prev = node;
    }
    private void removeNode(DLL node){//O(1)
        node.prev.next = node.next;
        node.next.prev = node.prev;
        
        
    }
    public int get(int key) { // O(N)
        if(this.cache.containsKey(key)){
            DLL node = this.cache.get(key);
            node.freq++;
            this.removeNode(node);
            this.addNode(node,node.prev);
            return node.value;
        }
        return - 1;
    }
    
    public void put(int key, int value) { // O(N)
        if(this.capacity == 0) return;
        if(this.get(key)!= -1){
            this.cache.get(key).value = value;
            
        }else{
            if(this.cache.size() == this.capacity){
                DLL headNext = head.next;
                this.removeNode(headNext);
                this.cache.remove(headNext.key);
            }
            DLL node = new DLL(key,value,1);
            this.cache.put(key,node);
            this.addNode(node,this.head.next);
            
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
