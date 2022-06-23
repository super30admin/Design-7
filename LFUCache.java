// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

class LFUCache {

    class Node {
        Node next, prev;
        int key;
        int val;
        int freq;
        
        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }
    
    class DLList {
        Node head, tail;
        int size;
        
        DLList() {            
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        
        void addTohead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
            
        }
        
        void remove(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        
        Node removeFromTail() {
            Node lastNode = tail.prev;
            remove(lastNode);
            return lastNode;
        }
    }
    
    int capacity;
    Map<Integer, Node> map;
    Map<Integer, DLList> freqMap;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        
        Node node = map.get(key);
        
        update(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        
        if(capacity == 0) return;
        
        if(!map.containsKey(key)) {
            
            if(capacity == map.size()) {
                DLList listToRemove = freqMap.get(min);
                Node nodeToRemove = listToRemove.removeFromTail();
                
                map.remove(nodeToRemove.key);
            }
            
            Node node = new Node(key, value);
            min = 1;
            map.put(key, node);
            
            DLList list = freqMap.getOrDefault(node.freq, new DLList());
            
            list.addTohead(node);
            freqMap.put(1, list);
            
            
        } else {
            Node node = map.get(key);
            node.val = value;
            update(node);
            return;
           
        }    
    }
    
    private void update(Node node) {
        DLList oldList = freqMap.get(node.freq);
        oldList.remove(node);
        
        if(min == node.freq && oldList.size == 0) {
            min++;
        }
        
        node.freq++;
        
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        
        newList.addTohead(node);
        
        freqMap.put(node.freq, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */