//TC : get(),put() --> O(1)
//SC : O(1)
import java.util.HashMap;

class LFUCache {

    class Node{
        int key,value,cnt;
        Node next,prev;
        public Node(int key,int value, int cnt){
            this.key = key;
            this.value =value;
            this.cnt =1;
            this.next = null;
            this.prev=null;
        }
    }

    class DLL{
        Node head,tail;
        int size;

        public DLL(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        private void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev =node;
            size++;
        }
        private void remove(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        private Node removeLast(){
            Node last = tail.prev;
            remove(last);
            return last;
        }

    }

   

   

    HashMap<Integer,Node> map;
    HashMap<Integer,DLL> freq;
    int min;int capacity;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;

    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node!=null){
            update(node);
            return node.value;
        }
        else{
            return -1;
        }
        
    }

    private void update(Node node){
        DLL oldList = freq.get(node.cnt);
        oldList.remove(node);
        if(oldList.size == 0 && node.cnt == min)
            min++;
        node.cnt++;
        DLL newList =freq.getOrDefault(node.cnt,new DLL());
        newList.addToHead(node);        
        freq.put(node.cnt,newList);
    }
    
    public void put(int key, int value) {
        //Case 1: Key is already in cache
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            update(node);
        }
        else{
            Node newnode = new Node(key,value);
            if(map.size() >=capacity){


                DLL list = freq.get(min);
                Node toberemoved = list.removeLast();
                map.remove(toberemove.key);


            }
            DLL oldList = freq.getOrDefault(1, new DLL());
            oldList.addToHead(newnode);
            freq.put(1,oldList);
            map.put(key,newnode);
            min = 1;

        }

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */