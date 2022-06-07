class LFUCache {

    //Time Complexity:0(1)
    //Space Complexity: 0(n) where n is the capacity of the cache
    //Did it successfully run on leetcode: Yes
    //Did you face any problem while coding: No

    //In brief explain your approach

    class Node{ //we take a linked list to store the key value pair along with the frequency
        int key;
        int value;
        int cnt;
        Node prev, next;    //prev and next pointer to navigate through linked list
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.cnt = 1;   //initializing count as 1 as we are this is the first time we are encountering
        }
    }

    class DLList{   //we take a doubly linked list to take care if frequencies are same, then to implement LRU
        Node head, tail;    //head and tail to traverse through double linked list
        int size;   //keeps a track of the size of the doubly linked list
        public DLList(){
            head = new Node(-1,-1); //dummy head
            tail = new Node(-1,-1); //dummy tail
            head.next = tail;   //connecting head and tail pointers
            tail.prev = head;
        }
        public void addtoHead(Node node){   //when we receive a new key value, we add it to the head
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++; //and incrase the size of the LL
        }
        public void remove(Node node){  //when a get function is called or put function is called over existing key value, we remove the node and again place it at the top
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        public Node removeLast(){   //when capacity is full, we remove the last node or LRU
            Node lastnode = tail.prev;
            remove(lastnode);
            return lastnode;
        }
    }

    HashMap<Integer, Node> map; //we are declaring our 1st hashmap which store the key and the location of node of the key
    HashMap<Integer, DLList> freqmap;   //2nd hashmap stores the frequency and the nodes having the frequency
    int capacity, min;  //we are making capacity a global variable and also keeping a track of the minimum frequency

    public LFUCache(int capacity) { //initializing our hashmap
        map = new HashMap<>();
        freqmap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if(!map.containsKey(key)){  //when a get method is called, we check if the key exists in our 1st hashmap or not
            return -1;  //if not, then we return -1 indicating it is not LFU
        }
        Node node = map.get(key);   //if it is present in hashmap, means it is LFU
        update(node);   //we call the update function which removes the node from it's current position and increases frequency and again places it at the designated position
        return node.value;  //we return the value of the key
    }

    public void update(Node node){
        DLList oldlist = freqmap.get(node.cnt); //we are getting the current occurence of the linked list
        if(oldlist != null){    //if the linkedlist at that frequency is not null
            oldlist.remove(node);   //we remove the current node from the linked list
        }
        if(min == node.cnt && oldlist.size == 0){   //if the minimum pointing to a certain frequency has no nodes n it or the size of current list is 0
            min++;  //we set minimum to the next frequency
        }
        node.cnt++; //also we increase the frequency in our linked list
        DLList newlist = freqmap.getOrDefault(node.cnt, new DLList());  //we take the new frequency or create a doubly linked list at that frequency if it is not encountered
        newlist.addtoHead(node);    //we then put the new LL at top of the linked list at that frequency
        freqmap.put(node.cnt, newlist); //we also update our frequency hashmap
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){   //when a put function is called, we check our 1st hashmap if the key is present
            Node node = map.get(key);   //if yes, we get the address of the node in our doubly linked list
            node.value = value; //we also update the value as the previous value is changes with the new value
            update(node);   //we call the update function and delete the node from its old position, update frquency, value and add the node to the top of the frequency linked list
            return;
        }
        if(capacity == 0){  //if a node is encountered that is not already created and and if the capacity is set to 0 of LFU, we return
            return;
        }
        if(capacity == map.size()){ //if a node is encountered that is not already created and we have reached the maximum stated capacity,
            DLList oldlist = freqmap.get(min);  //we search for the LRU through our frequency array as minimum will point to the least frequency in our frequency hashmap which will inturn hold the LRU
            Node lastnode = oldlist.removeLast();   //we remove the last node
            map.remove(lastnode.key);//also we remove the key and node address from our 1st hashmap
        }
        Node node = new Node(key, value);   //now we create a new node for the put method for a new key value
        min = 1;    //we reset our minimum to 1 as this is the 1st time we are encountering this key value pair
        DLList newlist = freqmap.getOrDefault(1, new DLList()); //we also update the frequency hashmap or add a doubly linked list at frequency 1 if it is the first time
        newlist.addtoHead(node);    //we add the node to the head of the Doubly Linked List
        map.put(key, node); //we also put the key and the address of the node in our hashmap
        freqmap.put(min, newlist);  //we also put the frequency and doubly LL in our frequency map
    }
}
