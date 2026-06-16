// Time Complexity : O(1) for both get and put
// Space Complexity : O(capacity)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach in three sentences only
// We use one HashMap to map keys to nodes and another HashMap to map frequencies to doubly linked lists.
// Each frequency list keeps nodes with the same count, ordered by recent usage so we can remove the least recently used node among the least frequent ones.
// On every get or update, we increase the node frequency and move it to the correct frequency list.

class LFUCache {
    class Node {
        int key;
        int val;
        int cnt;
        Node prev, next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DLList {
        Node head;
        Node tail;
        int size;

        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        public void addToHead(Node node) {
            node.next = this.head.next;
            node.prev = this.head;
            this.head.next = node;
            node.next.prev = node;
            this.size++;
        }

        public Node removeTail() {
            Node tailPrev = this.tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> map;
    private HashMap<Integer, DLList> fMap;
    private int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
    }

    private void update(Node node) {
        int oldCnt = node.cnt;
        DLList oldList = fMap.get(oldCnt);
        oldList.removeNode(node);

        // update min frequency if old list becomes empty
        if(oldCnt == min && oldList.size == 0) {
            min++;
        }

        int newCnt = oldCnt + 1;
        node.cnt = newCnt;

        // move node to next frequency list
        DLList newList = fMap.getOrDefault(newCnt, new DLList());
        newList.addToHead(node);
        fMap.put(newCnt, newList);
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;

        Node node = map.get(key);
        update(node);
        return node.val;
    }

    public void put(int key, int value) {
        if(capacity == 0) return;

        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        } else {
            if(capacity == map.size()) {
                // remove least recently used node from min frequency list
                DLList minList = fMap.get(min);
                Node toRemove = minList.removeTail();
                map.remove(toRemove.key);
            }

            Node newNode = new Node(key, value);
            this.min = 1;

            DLList li = fMap.getOrDefault(min, new DLList());
            li.addToHead(newNode);
            fMap.put(min, li);
            map.put(key, newNode);
        }
    }
}