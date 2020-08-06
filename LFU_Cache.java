/**
 * intuition: Use 2 hashmaps...one to keep track of key and node and the other map to keep track of frequency and doubleyLL
 * Time: O(1)
 * Space:O(n) n-capacity
 */
class Node{
    int val;
    int key;
    int freq;
    Node next;
    Node prev;

    Node(int k,int v){
        val = v;
        key = k;
        freq = 1;
        next = null;
        prev = null;
    }
}
class Pair{
    Node head;
    Node tail;
    int size = 0;
    Pair(){
        head = new Node(-1,-1);
        tail = new Node(-1,-1);
        head.next = tail;
        tail.next = head;
    }
    public void addToHead(Node n){
        n.next = head.next;
        n.prev = head;
        head.next = n;
        n.next.prev = n;
    }
    public void removeNode(Node n){
        n.next.prev = n.prev;
        n.prev.next = n.next;
    }
    public Node removeLast(){
        Node removed = tail.prev;
        removed.prev.next = tail;
        tail.prev = removed.prev;
        return removed;
    }
}

class LFUCache {
    HashMap<Integer,Node> nmap;  //node map
    HashMap<Integer, Pair> fmap; // Frequency map
    int capacity;
    int min;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.nmap = new HashMap<>();
        this.fmap = new HashMap<>();
    }

    public int get(int key) {
        if(nmap.containsKey(key)){
            Pair p = fmap.get(nmap.get(key).freq);
            p.removeNode(nmap.get(key));
            p.size--;
            if(min == nmap.get(key).freq && p.size == 0) min++;
            nmap.get(key).freq++;
            Pair newP = fmap.getOrDefault(nmap.get(key).freq,new Pair());
            newP.addToHead(nmap.get(key));
            newP.size++;
            fmap.put(nmap.get(key).freq,newP);
            //System.out.println("freq: "+nmap.get(key).freq+" min "+min);
            return nmap.get(key).val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(capacity == 0) return;
        //System.out.println(key);
        if(nmap.containsKey(key)){
            nmap.get(key).val = value;
            Pair p = fmap.get(nmap.get(key).freq);
            p.removeNode(nmap.get(key));
            p.size--;
            if(min == nmap.get(key).freq && p.size == 0) min++;
            nmap.get(key).freq++;
            nmap.get(key).val = value;
            Pair newP = fmap.getOrDefault(nmap.get(key).freq,new Pair());
            newP.addToHead(nmap.get(key));
            newP.size++;
            fmap.put(nmap.get(key).freq,newP);
        }else{
            if(nmap.size() == capacity){
                Pair p = fmap.get(min);
                //System.out.println(nmap.size()+ " "+p.tail.prev.val);
                Node removed = p.removeLast();
                p.size--;
                if(p.size == 0) min++;
                nmap.remove(removed.key);
            }
            Node newnode = new Node(key,value);
            nmap.put(key,newnode);
            min = 1;
            Pair p = fmap.getOrDefault(1,new Pair());
            p.size++;
            p.addToHead(newnode);
            fmap.put(1,p);
        }
    }
}


/**
 ["LFUCache","put","put","get","put","get","get","put","get","get","get"]
 [[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */