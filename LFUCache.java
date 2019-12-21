//Time Complexity :O(log(N)) for get operation. O(log(N)) for put operation
//Space Complexity :O(N)
//Did this code successfully run on Leetcode :No for few testcases
//Any problem you faced while coding this :Yes For larger testcases code didn't run. Have to debug more.


//Your code here along with comments explaining your approach

class LFUCache {
    PriorityQueue<Node> pq;
    Map<Integer,Node> map;
    int size, capacity;
    int timestamp;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        timestamp = 0;
        pq = new PriorityQueue<>();
        map = new HashMap<>();
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(null != node){
            node.freq++;
            node.timestamp = this.timestamp++;
            pq.remove(node);
            pq.add(node);
            return node.value;
        }
        // System.out.print("get"+key);
        // printPQ();
      return -1;  
    }
    
    void printPQ(){
        System.out.println();
        for(Node n : pq){
            System.out.print("("+n.key+","+n.value+","+n.freq+","+n.timestamp+") ");
        }
        System.out.println();
    }
    public void put(int key, int value) {
        //System.out.print("put"+key+", "+value);
        Node node = map.get(key);
        if(null != node){
            node.freq = 0;
            node.value = value;
            node.timestamp = this.timestamp;
            this.timestamp++;
            pq.remove(node);
            pq.offer(node);
        }else{
            node = new Node(key,value,this.timestamp++);
            if(size < capacity){
                map.put(key, node);
                pq.add(node);
                size++;
            }else{
                if(size != 0){
                    Node remNode = pq.poll();
                    map.remove(remNode.key);
                    map.put(key,node);
                    pq.add(node);
                    size++;
                }
            }
        }
        //printPQ();
    }
    
    class Node implements Comparable<Node>{
        int key, value, freq, timestamp;
        public Node(int key, int value, int timestamp){
            this.value = value;
            this.key = key;
            this.freq = 0;
            this.timestamp = timestamp;
        }
        public int compareTo(Node other){
            if(this.freq-other.freq == 0){
                return this.timestamp-other.timestamp;
            }
            return this.freq-other.freq;
        }
    }
}