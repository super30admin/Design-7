// Time Complexity : O(1) for each user operation
// Space Complexity : O(n) for hashmap and frequency map
// Did this code successfully run on Leetcode : Yes

import java.util.HashMap;

class LFUCache {
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> fmap;
    int minFreq;

    class Node{
        int key;
        int value;
        int freq;
        Node next;
        Node prev;

        public Node(int key, int value){
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
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }

        private void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            this.size++;
        }

        private void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            this.size--;
        }

        private Node removeLastNode()
        {
            Node last = this.tail.prev;
            removeNode(last);
            return last;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fmap = new HashMap<>();
    }


    public void update(Node node){
        DLList oldDllist = fmap.get(node.freq);
        oldDllist.removeNode(node);
        if(node.freq == minFreq && oldDllist.size == 0)
        {
            minFreq++;
        }
        node.freq +=1;
        DLList newDllist = fmap.getOrDefault(node.freq, new DLList());
        newDllist.addToHead(node);
        fmap.put(node.freq, newDllist);

    }
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            update(node);
        }else{
            if(map.size() == this.capacity){
                DLList minFreqList = fmap.get(minFreq);
                Node lastNode = minFreqList.removeLastNode();
                map.remove(lastNode.key);
            }
            minFreq = 1;
            Node newNode = new Node(key,value);
            DLList minFreqList = fmap.getOrDefault(minFreq,new DLList());
            minFreqList.addToHead(newNode);
            fmap.put(minFreq, minFreqList);
            map.put(key,newNode);
        }

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */