// Time Complexity - O(1)
// Space Complexity - O(m+n) for 2 hashmaps 
// Approach:
// Maintain 2 hashmaps one for maintaining key and node as value that keeps the count of
// nodes to keep track of how many times the node has been accessed.Another hashmap
// to store the freq and dobly LL having same freq and it will be able to perform LRU so that least recently used node
// is removed from DLL. The nodes will be adjusted once accessed and the freq will be updated timely. In freq hashmap, once global
// min freq has empty DLL and count of node is equal to min, increment the min to new min. If capacity is exceeded, find global min 
// mapping to which DLL and remove the min freq node from list.

class LFUCache {
    class Node {
        int count;
        int key;
        int val;
        Node next, prev;
        Node(int k, int v) {
            this.key = k;
            this.val = v;
            this.count = 1;
            this.next = null;
            this.prev = null;
        }
    }
    
    class DLList {
        Node head;
        Node tail;
        int size;
        DLList() {
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }
        
        public void insert(Node n) {
            n.next = head.next;
            n.prev = head;
            head.next = n;
            n.next.prev = n;
            size++;
        }
        
        public void removeNode(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }
        
        public Node removeLast() {
            Node node = tail.prev;
            removeNode(node);
            return node;
        }
    }
    
    HashMap<Integer,Node> map;
    HashMap<Integer,DLList> freq;
    int min = 0;
    int capacity;
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(map.containsKey(key)) {
            update(map.get(key));
            return map.get(key).val;
        } else {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            if(capacity == 0) return;
            if(map.size() == capacity) {
                DLList least = freq.get(min);
                Node rem = least.removeLast();
                map.remove(rem.key);
            }
            Node new_node = new Node(key,value);
            map.put(key,new_node);
            min = 1;
            DLList node = freq.getOrDefault(min,new DLList());
            node.insert(new_node);
            freq.put(min,node);
        }
    }
    
    public void update(Node n) {
        DLList oldList = freq.get(n.count);
        oldList.removeNode(n);
        if(oldList.size==0 && n.count == min) min++;
        n.count++;
        DLList newList = freq.getOrDefault(n.count,new DLList());
        newList.insert(n);
        freq.put(n.count,newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */