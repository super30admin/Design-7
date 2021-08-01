
// Time - O(1) for put and get and update
// Space - O(N)

class LFUCache {

    class Node {

        int key; int val;
        int cnt;
        Node nxt; Node prev;

        public Node(int key, int value) {
            this.key = key;
            this.val = value;
            this.cnt = 1;
        }

    }

    class DLList {

        Node head;
        Node tail;
        int size;
        public DLList() {

            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.nxt = tail;
            tail.prev = head;

        }

        public void addToHead(Node node) {

            node.nxt = head.nxt;
            head.nxt = node;
            node.prev = head;
            node.nxt.prev = node;
            size++;
        }

        public void removeNode(Node node) {

            node.nxt.prev = node.prev;
            node.prev.nxt = node.nxt;
            size--;

        }

        public Node removeLast() {
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }


    }

    HashMap<Integer,Node> map; // create node map
    HashMap<Integer,DLList> freqMap; // create frequency map
    int capacity; int min;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;

    }

    public int get(int key) {

        if(!map.containsKey(key)) return -1;
        Node node = map.get(key); // get the reference from the map
        update(node); // update will delete the node from old list and increment the frequency and add it to the new list
        return node.val;

    }

    public void put(int key, int value) {
        if(capacity == 0) return; // checks if the capacity is 0 then return
        if(map.containsKey(key)) {
            Node node = map.get(key); // if the key is found update the val and call update method
            node.val = value;
            update(node);
        }
        else {

            if(capacity == map.size()) { // if the size is full delete the min frequency node

                // delete the min freq node

                DLList minFreq = freqMap.get(min);
                Node toRemove = minFreq.removeLast(); // remove the last node from the old list
                map.remove(toRemove.key); // remove the key from map

            }

            Node newNode = new Node(key,value); // add the new node
            min = 1;
            DLList minList = freqMap.getOrDefault(min, new DLList());
            minList.addToHead(newNode);
            freqMap.put(1,minList);
            map.put(key,newNode);


        }


    }

    private void update(Node node) { // this method will delete the node from old list and increment the frequency and add it to the new list
        int count = node.cnt;
        DLList oldList = freqMap.get(node.cnt);
        oldList.removeNode(node);
        if(node.cnt == min && oldList.size == 0) min++;
        count++;
        node.cnt = count;
        DLList newList = freqMap.getOrDefault(node.cnt, new DLList()); // creates a new list if there is only one node in the old list by assigning the node as min count 1
        newList.addToHead(node); // adds the new node from old list to the head of new list
        freqMap.put(count,newList);
        map.put(node.key,node);


    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */