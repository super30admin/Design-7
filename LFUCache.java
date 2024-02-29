import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    Map<Integer, Node> cache;
    Map<Integer, DLList> freqListMap;
    int capacity;
    int minFreq;
    public LFUCache(int capacity) {
        cache = new HashMap<>();
        freqListMap = new HashMap<>();
        this.capacity = capacity;
    }

    // TC: O(1)
    // SC: O(1)
    public int get(int key) {
        if (!cache.containsKey(key)) return -1;
        Node node = cache.get(key);
        updateNode(node);
        return node.value;
    }

    // TC: O(1)
    // SC: O(1)
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            updateNode(node);
            node.value = value;
        } else {
            if (cache.size() == capacity) {
                DLList minFreqList = freqListMap.get(minFreq);
                Node toBeRemoved = minFreqList.removeTailPrev();
                cache.remove(toBeRemoved.key);
            }
            Node newNode = new Node(key, value);
            DLList newList = freqListMap.getOrDefault(1, new DLList());
            newList.addToHead(newNode);
            cache.put(newNode.key, newNode);
            freqListMap.put(1, newList);
            minFreq = 1;
        }
    }

    private void updateNode(Node node) {
        int freq = node.freq;
        DLList freqList = freqListMap.get(freq);
        freqList.removeNode(node);
        if (node.freq == minFreq && freqList.size == 0) {
            minFreq++;
        }
        node.freq++;
        DLList newList = freqListMap.getOrDefault(node.freq, new DLList());
        newList.addToHead(node);
        freqListMap.put(node.freq, newList);
    }
}

class Node {
    int key;
    int value;
    int freq;
    Node next;
    Node prev;
    public Node(int key, int value) {
        this.key = key;
        this.value = value;
        freq = 1;
    }
}

class DLList {
    Node head;
    Node tail;
    int size;
    public DLList() {
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    // TC: O(1)
    // SC: O(1)
    public void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        size++;
    }

    // TC: O(1)
    // SC: O(1)
    public void removeNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    public Node removeTailPrev() {
        Node toBeRemoved = tail.prev;
        removeNode(toBeRemoved);
        return toBeRemoved;
    }
}
