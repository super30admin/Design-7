package Design7;

//public class LFUCache {
//
//}

import java.util.HashMap;

class LFUCache {

    class Node {
        int key;
        int val;
        int count;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size = 0;
        public DLList() {
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node) {
            Node headNode = head.next;
            head.next = node;
            node.prev = head;
            node.next = headNode;
            headNode.prev = node;
            size++;
        }

        public void removeNode(Node node) {
            Node prevNode = node.prev;
            Node nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            size--;
        }

        public Node removeLastNode() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity;
    int min = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key))   return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }

    public void put(int key, int value) {
        if(capacity == 0)   return;
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            if(capacity == map.size()) {
                DLList minFreq = freqMap.get(min);
                Node toRemove = minFreq.removeLastNode();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList  minList = freqMap.getOrDefault(min, new DLList());
            minList.addToHead(newNode);
            freqMap.put(1, minList);
            map.put(key, newNode);
        }
    }

    public void update(Node node) {
        int cnt = node.count;
        DLList oldList = freqMap.get(node.count);
        oldList.removeNode(node);
        if(node.count == min && oldList.size == 0)  min++;
        cnt++;
        node.count = cnt;
        DLList newList = freqMap.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        freqMap.put(cnt, newList);
        map.put(node.key, node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
