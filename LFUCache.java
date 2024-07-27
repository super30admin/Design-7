import java.util.HashMap;

public class LFUCache {
    //https://leetcode.com/problems/lfu-cache/

    /**
     time complexity - get o(1) - manupulation of few pointers, put o(1) - manupulation of few pointers.
     space complexity  - (n)
     hashmap for lookup .address as value bcos linkedlist represents order in which elements are added.
     dll bcos if needs to be deleted, time complexity would be increased so doubly ll.

     */
    /**
     * need node, DLL, 2 hashmap.
     * // Approach.
     * - Hashmap 1 with key as the input key and value as the Node. Which contains
     * ,the key, value and its frequency
     * - Hashmap 2 key as frequency and value as DLL with all the noes with that
     * frequency.
     * - doubly linkedlist to store all the input key value pairs along with their
     * frequencies as
     * there could be multiple with same frequency.
     * - DLL is to find LRU when multiple nodes have same frequency. because in such
     * case we return LRU from the LFU.
     * - DLL is used instead of LL to accopmlish get remove in o(1)
     * Example testcase.
     * 1. put(1,1) - create node out it in hashmap 1. put it in hashmap 2's
     * linkedlist along with the frequency of that node. If the LL doesnt exisit,
     * create one.
     * 2. put (2,1) - repeat above
     * 3. put (1,100) - get from hashmap 1, update value and frequency.
     * goto hashmap 2, remove from associated frequency and add it to the updated
     * frequency of the node.
     * if it doesnt exisit create new DLL.
     *
     * maintain min freq integer variable
     */
    class Node {
        int key, value;
        int freq;
        Node prev, next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            // when we add new node frequency become 1 so already adding that value.
            this.freq = 1;
            // in java prev and next are already 1.
        }
    }

    class DLL {
        // plan to use dummy nodes head and tail to add nodes in between.
        // also always want to know the size of DLL so taking the size variable.
        // if we add node to DLL size ++, if we remove node size--
        Node head, tail;
        int size;
        public DLL() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            size = 0;
        }

        // Adding to the head.
        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }

        // remove node
        private void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        // remove lastNode
        private Node removeLastNode(){
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }


    // declare class variables for LRU Cache
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        // inittialze all the member variable
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * For get check if the key contains, if not return -1. if contains, get the
     * node and return its value;
     * Since we touched the key, its frequency needs to be updated and then needs to
     * be removed from
     * the current DLL in freq map and then should be added to new DLL with updated
     * freq. update function will take care of this.
     * same for put and get, we ned to update certain things.
     */

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        // since we touched this key, its freq needs ot be updated and then should be
        // moved to respective DLL in freqmap.
        update(node);
        return node.value;

    }

    /**
     * update function, since we got the node, its frequency is non zero.
     * get the node, update freq, and since we touched this key,
     * its freq needs ot be updated and then should be moved to respective DLL in
     * freqmap.
     * get old DLL from old freq in the freq map.
     * remove node from DLL.
     * oldList.remove - can use this only if we can make it member function
     * increase freq. move it to DLL 4 in freq map
     *
     */

    private void update(Node node) {
        // get the DLL from freq map based on existing freq of the node.
        DLL oldList = freqMap.get(node.freq);
        // remove from oldList
        oldList.removeNode(node);
        if (node.freq==min && oldList.size == 0) {
            // since old min freq is zero, min freq has been increamented by 1.
            min++;
        }

        // update the nodes frequency.
        node.freq++;
        // add it to new freq DLL.
        DLL newList = freqMap.getOrDefault(node.freq, new DLL());
        newList.addToHead(node);
        // add list back to the freqmap.
        freqMap.put(node.freq, newList);

    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }
        if (capacity == 0) {
            return;
        }
        /**
         * if capacity reaches max, get min freq in freqmap. min freq is given by min
         * variable.
         * in freqmap if multiple nodes get last node(LRU); then put the new input.
         */

        if (capacity == map.size()) {
            DLL minFreqList = freqMap.get(min);
            Node lastNode = minFreqList.removeLastNode();
            // also remove from map list
            map.remove(lastNode.key);
        }
        // create new node. and add to map and freq map.
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        // since its new node initialize min to 1;
        min = 1;

        // since this is new node and min is 1, create new DLL. and add this node to its
        // head.
        DLL newList = freqMap.getOrDefault(1, new DLL());
        newList.addToHead(newNode);
        freqMap.put(min, newList);
    }
}
