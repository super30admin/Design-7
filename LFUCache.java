//time complexity : O(1) for all functions
//space complexity : 

class LFUCache {

    class Node {
        int key;
        int val;
        int freq;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1; //fresh node will have frequency 1
        }
    }

    class DLL {
        int size;
        Node head;
        Node tail;

        DLL() {
            this.head = new Node(-1,-1); //dummy head
            this.tail = new Node(-1,-1); //dummy tail
            this.head.next = tail;
            this.tail.prev = head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            node.prev = head;
            size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        //when we have to remove LRU because of same frequency and capacity being full
        public Node removeLast() {
            Node node = tail.prev;
            removeNode(node);
            return node;
        }

    }

    int min; //maintain the min frequency so we can give it out in O(1)
    int capacity;

    HashMap<Integer, Node> map; //key vs Node, so we can access the node of a key in O(1)
    HashMap<Integer, DLL> freqMap; //freq vd list of nodes with that freq
    //doubly LL because we want to add and remove optimally

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap();
        this.freqMap = new HashMap();
    }

    public int get(int key) {

        //entry not in cache
        if(!map.containsKey(key))
            return -1;
        //get the node
        Node node = map.get(key);
        update(node);
        //return val
        return node.val;
    }

    public void put(int key, int value) {

        //already in cache
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        //not in cache, fresh node
        else {

            if(capacity == 0)
                return;

            //if capacity is full
            if(capacity == map.size()) {
                //get list of the min frequency
                DLL minList = freqMap.get(min);
                //remove it's lru node
                Node lruNode = minList.removeLast();
                //remove lru node from key-node map also, since it won't be in cache anymore
                map.remove(lruNode.key);
            }

            //create the fresh node
            Node newNode = new Node(key, value);
            //this new node has the least freq now = 1
            min = 1;
            //put it in key-node map
            map.put(key, newNode);
            //get the DLL of frequency 1, or create a new DLL
            DLL minList = freqMap.getOrDefault(1, new DLL());
            //add new node to this DLL
            minList.addToHead(newNode);
            //put DLL in freqmap
            freqMap.put(1, minList);
        }
    }

    public void update(Node node) {

        int oldFreq = node.freq;
        DLL oldList = freqMap.get(oldFreq);

         oldList.removeNode(node);

        //check and update min
        if(min == oldFreq && oldList.size == 0)
            min++;

        //increase freq
        node.freq = oldFreq+1;

        //move to new DLL
        DLL newList = freqMap.getOrDefault(node.freq, new DLL());
        newList.addToHead(node);

        //put newList in freqMap
        freqMap.put(node.freq, newList);
    }

}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
