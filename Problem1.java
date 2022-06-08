import java.util.HashMap;
import java.util.Map;

class LFUCache {
    // Two hash Map with doubly Linked List solution
    // TC : O(1)
    // SC : O(1)
    class Node {
        int key, value, cnt;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            cnt = 1;
        }
    }

    class DList {
        Node head, tail;
        int size;

        public DList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }

        public void remove(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        public Node removeLast() {
            Node lastNode = tail.prev;
            remove(lastNode);
            return lastNode;
        }

    }

    int capacity, min;
    Map<Integer, Node> map;
    Map<Integer, DList> fMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        fMap = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            update(node);
            return;
        }
        if (capacity == map.size()) {
            DList dList = fMap.get(min);
            Node lastNode = dList.removeLast();
            map.remove(lastNode.key);
        }
        min = 1;
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        DList dList = fMap.getOrDefault(1, new DList());
        dList.addToHead(newNode);
        fMap.put(1, dList);
    }

    private void update(Node node) {
        DList oldList = fMap.get(node.cnt);
        if (oldList != null) {
            oldList.remove(node);
        }
        if (node.cnt == min && oldList.size == 0) {
            min++;
        }
        node.cnt++;
        DList newList = fMap.getOrDefault(node.cnt, new DList());
        newList.addToHead(node);
        fMap.put(node.cnt, newList);
    }

    //Driver
    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(2);
        lfuCache.put(1, 1);
        lfuCache.put(2, 2);
        System.out.println("lfuCache get 1 value " + lfuCache.get(1));
        lfuCache.put(3, 3);
        System.out.println("lfuCache get 2 value " + lfuCache.get(2));
        System.out.println("lfuCache get 3 value " + lfuCache.get(3));
        lfuCache.put(4, 4);
        System.out.println("lfuCache get 1 value " + lfuCache.get(1));
        System.out.println("lfuCache get 3 value " + lfuCache.get(3));
        System.out.println("lfuCache get 4 value " + lfuCache.get(4));
    }
}
