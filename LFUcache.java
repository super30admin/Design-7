//time complexity O(1) for both get and put
//space complexity O(2n) size of both the hashmaps

class LFUCache {
    class Node {
        int key;
        int value;
        int freq;
        Node next;
        Node prev;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }
    class DLL {
        Node head; Node tail; int size;//declaration
        public DLL(){
            head = new Node(-1,-1);//instantiation/initialization
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        public void addTohead(Node node){//adding the new node to the head
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;
            size++;
        }
        public void removeNode(Node node){//remove any given node
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        public Node removeLast(){//removing last node
            if(size > 0){
                Node tailPrev = tail.prev;
                removeNode(tailPrev);
                return tailPrev;
            }
            return null;
        }
    }
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freqMap;
    int min; int capacity;//capacity declared globally for use in other fns
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            update(node);
        } else{//if map doesnt contain key
	    //edge case
            if(capacity == 0) return;
            //if the capacity if full
            if(map.size() == capacity){
                //remove LRU node from LFU list
                DLL minList = freqMap.get(min);//getting LFU DLL
                Node toBeRemoved = minList.removeLast();
                map.remove(toBeRemoved.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLL minList = freqMap.getOrDefault(min, new DLL());
            minList.addTohead(newNode);
            freqMap.put(min, minList);
            map.put(key, newNode);
        }

    }
    private void update(Node node){
        DLL oldList = freqMap.get(node.freq);
        oldList.removeNode(node);
        if(oldList.size == 0 && node.freq == min) min++;
        node.freq++;//update frequency
        DLL newList = freqMap.getOrDefault(node.freq, new DLL());
        newList.addTohead(node);
        freqMap.put(node.freq, newList);//add node to new DLL in map
        map.put(node.key, node);//because freq has changed
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
