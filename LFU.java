
// This solution uses hybrid approach of using two hashmaps
// One is to track LRU per frequency and other to directly fetch node specific to key
// We also track a min freq to go to the minimum frequency list and perform operations related to deleting min frequently accessed
// If there are multiple elements we remove the last element as its LRU
class LFUCache {

    Map<Integer, DLList> map = new HashMap();
    Map<Integer, Node> nodeMap = new HashMap();
    int minFreq=1;
    int capacity;

    public LFUCache(int capacity) {
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!nodeMap.containsKey(key)) {
            return -1;
        } else {
            Node node = nodeMap.get(key);
            node.prev.next=node.next;
            node.next.prev=node.prev;
            node.next=null;
            node.prev=null;

            int val = node.val;

            int oldFreq = node.freq;
            int newFreq = oldFreq+1;

            update(node);
            return val;
        }
    }

    private void delete() {
        DLList list = map.get(minFreq);
        Node node = list.tail.prev;
        list.tail.prev = node.prev;
        node.prev.next=list.tail;
        node.next=null;
        node.prev=null;
        nodeMap.remove(node.key);
    }

    private void update(Node node) {
        int oldFreq = node.freq;
        int newFreq = oldFreq+1;
        DLList oldList = map.get(oldFreq);
        if(oldFreq==minFreq && oldList.head.next==oldList.tail) {
            minFreq++;
        }
        DLList newList = map.getOrDefault(newFreq, new DLList());
        node.next=newList.head.next;
        node.prev=newList.head;
        newList.head.next=node;
        node.next.prev=node;
        node.freq=newFreq;
        map.put(newFreq, newList);
    }
    
    public void put(int key, int value) {
        Node node;
        if(!nodeMap.containsKey(key)) {
            node = new Node(key, value);
            if(nodeMap.size()==capacity) {
                delete();
            }
            nodeMap.put(key, node);
            minFreq=1;
            DLList newList = map.getOrDefault(1, new DLList());
            node.next=newList.head.next;
            node.prev=newList.head;
            newList.head.next=node;
            node.next.prev=node;
            map.put(1,newList);
            return;
        } else {
            node = nodeMap.get(key);
            node.prev.next=node.next;
            node.next.prev=node.prev;
            node.next=null;
            node.prev=null;
        }

        if(node.val!=value) node.val=value;

        update(node);
    }
}

class DLList {
    Node head;
    Node tail;

    public DLList() {
        this.head = new Node(-1,-1);
        this.tail = new Node(-1,-1);
        head.next=tail;
        tail.prev=head;
    }
}

class Node {
    int key;
    int val;
    Node next;
    Node prev;
    int freq;

    public Node(int key, int val) {
        this.key=key;
        this.val = val;
        this.freq=1;
        this.next=null;
        this.prev=null;
    }
}


/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
