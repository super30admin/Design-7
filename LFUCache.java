import java.util.HashMap;

class LFUCache {

    // Define the properties of Node
    class Node{
        int key;
        int val;
        int count;  // To keep track of the frequency
        Node next;
        Node prev;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }

    //Define the properties of DLList
    class DLList{
        int size;
        Node head;
        Node tail;
        public DLList(){
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = tail;
            this.tail.prev = head;
        }

        public void addToHead(Node node){  //Always Add the new node to the head
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;  // and increment the size
        }

        public void removeNode(Node node){ // remove node
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--; //and decrement the size
        }

        public Node removeLast(){    //Always remove the least used node from the last
            Node lastNode =  tail.prev;
            removeNode(lastNode);
            return lastNode;  // return is used to remove node from map as well
        }
    }

    int min;
    int capacity;
    HashMap<Integer, DLList> freqMap;   // frequency map : Count -> DLList
    HashMap<Integer, Node> map;  // Normal map : key -> Node

    public LFUCache(int capacity) {
        this.freqMap = new HashMap<>();
        this.map = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;

        Node temp = map.get(key);
        update(temp);   // update the count after accessing

        return temp.val;
    }

    public void put(int key, int value) {
        if(capacity == 0)
            return;

        if(map.containsKey(key)){  // If map already contians key, update value
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        else{
            //capacity is full
            if(map.size() == capacity){  // If the map size equals capacity, remove the least used node using min
                DLList minList = freqMap.get(min);
                Node toRemove = minList.removeLast();
                map.remove(toRemove.key);
                // freqMap.put(min, minList);
            }

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            min = 1;
            DLList minList = freqMap.getOrDefault(min, new DLList());
            minList.addToHead(newNode);

            freqMap.put(min, minList);

        }
    }

    public void update(Node node){
        int oldCount = node.count;
        DLList oldList = freqMap.get(oldCount);
        oldList.removeNode(node);   // Delete from old list and put it in new list
        // freqMap.put(oldCount, oldList);
        if(oldCount == min && oldList.size == 0)
            min++;

        node.count++;
        DLList newList = freqMap.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        freqMap.put(node.count, newList);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

