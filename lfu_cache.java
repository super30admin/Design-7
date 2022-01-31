class LFUCache {
    class Node {
        int key, value, cnt;
        Node next, prev;
        public Node (int key, int value) {
            this.key = key;
            this.value = value;
            this.cnt = 1;
        }
    }
    class dll {
        Node head, prev, tail;
        int size;
        public dll() {
            head = new Node(-1, -1); //dummy node
            tail = new Node(-1, -1); //dummy node
            head.next = tail;
            tail.prev = head;
        }
        public void addtohead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }
        public void removenode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        public Node removelast() {
            Node tailprev = tail.prev;
            removenode(tailprev);
            return tailprev;
        }
    }
    HashMap<Integer, dll> freqmap;
    HashMap<Integer, Node> map;
    int capacity, min;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        freqmap = new HashMap<>();
        map = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.value;
    }
    public void put(int key, int value) {
        if(capacity == 0) return;
        if(map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }
        if(capacity == map.size()) {
            dll oldlist = freqmap.get(min);
            Node node = oldlist.removelast();
            map.remove(node.key);
        }
        Node newnode = new Node(key, value);
        min = 1;
        dll list = freqmap.getOrDefault(min, new dll());
        list.addtohead(newnode);
        freqmap.put(min, list);
        map.put(key, newnode);
    }
    public void update(Node node) {
        dll oldlist = freqmap.get(node.cnt);
        if(oldlist != null) {
            oldlist.removenode(node);
        }
        if(oldlist.size == 0 && min == node.cnt) {
            min++;
        }
        node.cnt++;
        dll newlist = freqmap.getOrDefault(node.cnt, new dll());
        newlist.addtohead(node);
        freqmap.put(node.cnt, newlist);
    }
}
//tc and sc - O(1)