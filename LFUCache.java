import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    private static class Node {
        private int key;
        private int val;
        private int freq;

        private Node prev, next;

        public Node(int key, int val) {
            this.freq = 1;
            this.val = val;
            this.key = key;
        }
    }

    private static class DoublyLinkedList {
        private Node head, tail;
        private int size;

        public DoublyLinkedList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);

            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addToHead(final Node node) {
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;
            this.size++;
        }

        public void removeNode(final Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;

            node.next = null;
            node.prev = null;
            this.size--;
        }

        public Node removeTail() {
            final Node node = this.tail.prev;
            removeNode(node);

            return node;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Map<Integer, DoublyLinkedList> frequency;
    private int minCount;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequency = new HashMap<>();
    }

    public int get(int key) {
        if (!this.cache.containsKey(key)) return -1;

        final Node node = this.cache.get(key);
        updateNode(node);

        return node.val;
    }

    public void put(int key, int value) {
        if (this.cache.containsKey(key)) {
            final Node node = this.cache.get(key);
            node.val = value;
            updateNode(node);
        } else {
            if (capacity == 0) return;
            if (capacity == cache.size()) {
                final Node remove = this.frequency.get(minCount).removeTail();
                this.cache.remove(remove.key);
            }

            final Node node = new Node(key, value);
            minCount = 1;
            final DoublyLinkedList dll = this.frequency.getOrDefault(1, new DoublyLinkedList());
            dll.addToHead(node);

            this.cache.put(key, node);
            this.frequency.put(1, dll);
        }
    }

    private void updateNode(final Node node) {
        int freq = node.freq;
        DoublyLinkedList dll = this.frequency.get(freq);

        dll.removeNode(node);
        if (dll.size == 0) this.frequency.remove(freq);
        if (dll.size == 0 && freq == minCount) minCount++;

        node.freq += 1;
        dll = this.frequency.getOrDefault(node.freq, new DoublyLinkedList());

        dll.addToHead(node);
        this.frequency.put(node.freq, dll);
    }
}
