// Time Complexity : The time complexity is O(1)
// Space Complexity : The space complexity is O(capacity)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach

class LFUCache {

    // store the values
    Map<Integer,Node> map;
    // store my frequency
    Map<Integer,DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {

        if(!map.containsKey(key)){
            return -1;
        }

        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {

        if(!map.containsKey(key)){
            if(capacity == 0){return;}

            //capacity is full
            if(map.size() == capacity){
                DLList minList = freqMap.get(min);
                //remove the least frequent
                Node n = minList.removeLast();
                map.remove(n.key);
            }

            //add the new key
            Node cur = new Node(key,value);
            min = 1;
            DLList list = freqMap.getOrDefault(min,new DLList());
            list.add(cur);
            freqMap.put(min,list);
            map.put(key,cur);
        }
        //update the value and the frequency
        else{
            Node cur = map.get(key);
            cur.value = value;
            update(cur);
        }
    }

    public void update(Node node){

        //remove from old list
        int c = node.freq;
        DLList li = freqMap.get(node.freq);
        li.remove(node);

        if(c == min && li.size == 0){
            min++;
        }

        node.freq++;

        //add to new list
        DLList l = freqMap.getOrDefault(node.freq,new DLList());
        l.add(node);
        freqMap.put(node.freq,l);
    }
}

class Node{

    int key;
    int value;
    int freq;
    Node prev;
    Node next;

    public Node(int key,int value){
        this.key = key;
        this.value = value;
        this.freq = 1;
    }
}

class DLList{

    Node head;
    Node tail;
    int size;

    public DLList(){
        head = new Node(-1,-1);
        tail = new Node(-1,-1);
        head.next = tail;
        tail.prev = head;
    }

    public void add(Node node){
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
        size++;
    }

    public void remove(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    public Node removeLast(){
        Node n = tail.prev;
        remove(n);
        return n;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */