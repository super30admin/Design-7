import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    class Node {
        int key, value, freq;
        Node prev, next;

        public Node(int k, int v) {
            this.key = k;
            this.value = v;
            this.freq = 1;
        }
    }

    class DLL {
        Node head, tail;
        int size;
        public DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            node.prev = head;
            size++;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev=node.prev;
            size--;
        }

        private Node removeLastNode() {
            Node node = tail.prev;
            removeNode(node);
            return node;
        }
    }

    Map<Integer, Node> keyMap;
    Map<Integer, DLL> freqMap;
    int capacity, min;
    public LFUCache(int capacity) {
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!keyMap.containsKey(key)) return -1;
        Node node = keyMap.get(key);
        //remove node from old freq list and add it to new freq list
        update(node);
        return node.value;
    }

    private void update(Node node) {
        int oldFreq = node.freq;
        DLL oldDll = freqMap.get(oldFreq);
        oldDll.removeNode(node);
        if (min==oldFreq && oldDll.size==0) min++;
        oldFreq++;
        node.freq = node.freq+1;

        if (freqMap.containsKey(oldFreq)) {
            DLL newDll = freqMap.get(oldFreq);
            newDll.addToHead(node);
        }
        else {
            DLL newList = new DLL();
            newList.addToHead(node);
            freqMap.put(oldFreq, newList);
        }
    }

    public void put(int key, int value) {
        if (!keyMap.containsKey(key)) {
            if (keyMap.size()==capacity) {
                DLL minList = freqMap.get(min);
                Node toRemove = minList.removeLastNode();
                keyMap.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            keyMap.put(key, newNode);
            DLL newList = freqMap.getOrDefault(1, new DLL());
            newList.addToHead(newNode);
            freqMap.put(1, newList);
            keyMap.put(key, newNode);
        }
        else {
            Node node = keyMap.get(key);
            update(node);
            node.value = value;
        }
    }
}
