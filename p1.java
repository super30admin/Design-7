// Time Complexity :O(1) for get and put
// Space Complexity :O(1)
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :no


// Your code here along with comments explaining your approach


class LFUCache {
    int capacity;
    class Node{
        int key;
        int val;
        Node prev;
        Node next;
        int cnt;
        private Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

//Doubly linked list with add and remove node functions
    class Dllist{
        Node head;
        Node tail;
        int cnt;
        private Dllist(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        private void addNode(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            cnt++;
        }

        private void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
            cnt--;
        }

        private Node removeLast(){
            Node node = tail.prev;
            this.removeNode(node);
            return node;
        }

    }

    //map to keep track of keys and it's node
    HashMap<Integer, Node> map;
    //fmap to keep track of frequency and all the nodes for that frequency
    HashMap<Integer, Dllist> fmap;
    int min = 1;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        fmap = new HashMap<>();
        this.capacity = capacity;
        
    }
    
    //if we get the key in map, we would have to update it's frequency too
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        else{
            Node node = map.get(key);
            Dllist d = fmap.getOrDefault(node.cnt, new Dllist());
            d.removeNode(node);
            if(node.cnt==min && d.cnt == 0) min++;
            node.cnt++;
            d = fmap.getOrDefault(node.cnt, new Dllist());
            d.addNode(node);
            fmap.put(node.cnt, d);
            return node.val;
        }
    }
    
    //If we are putting a new key, check the capacity first. If it's full, remove the lfu node and then add the new node.
    public void put(int key, int value) {

        if(!map.containsKey(key)){
            if(map.size() == capacity){
                Node temp = fmap.get(min).removeLast();
                map.remove(temp.key);
            }
            Node node = new Node(key,value);
            min = 1;
            Dllist d = fmap.getOrDefault(node.cnt, new Dllist());
            d.addNode(node);
            fmap.put(1, d);
            map.put(key, node);
        }
        else{
            Node node = map.get(key);
            node.val = value;
            Dllist d = fmap.get(node.cnt);
            d.removeNode(node);
            if(node.cnt==min && d.cnt == 0) min++;
            node.cnt++;
            d = fmap.getOrDefault(node.cnt, new Dllist());
            fmap.put(node.cnt, d);
            d.addNode(node);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */