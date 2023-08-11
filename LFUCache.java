import java.util.HashMap;
public class LFUCache {

        // Hash maps of key-node and key-frequency DL list - Time O(1) and Space O(C)

        // Node class with a node of key, value, use counter and prev, next pointers compatible to doubly linked list
        public static class Node {

            int key; int value; int useCounter;

            Node prev; Node next;

            // constructor
            public Node(int key, int value) {

                this.key = key;
                this.value = value;
                this.useCounter = 1;
            }
        }

        // frequency (use counter) wise doubly linked lists with head, tail and list size and add to head, remove, remove LRU methods
        public static class frequencyDLL{

            Node head; Node tail; int listSize;

            // constructor
            public frequencyDLL() {

                this.head = new Node(-1, -1);
                this.tail = new Node(-1, -1);

                this.head.next = this.tail;
                this.tail.prev = this.head;
            }

            //  MRU (Most Recently Used) node will be at head-end (head.next)
            public void addToHead(Node node) {

                // add node to head of appropriate frequency DLL, and increase list size
                node.next = head.next;
                node.prev = head;
                head.next = node;
                node.next.prev = node;

                listSize++;
            }


            public void remove(Node node) {

                // remove node from appropriate frequency DLL, and decrease list size
                node.next.prev = node.prev;
                node.prev.next = node.next;

                listSize--;

            }

            // LRU is at the tail end (tail.prev)
            public Node removeLRU() {

                // remove Least Recently Used node (just before the tail) from appropriate (lowest)frequency DLL, and as LRU node itself is removed and returned, list size is adjusted during remove method call
                Node LRU = this.tail.prev;
                remove(LRU);

                return LRU;
            }

        }

        // Maintain Hash maps of (key and node) and (key and frequency DL list), capacity of LFU cache constant and lowest frequency (use counter) variable globally
        public static HashMap<Integer, Node> keyNodeMap;
        public static HashMap<Integer, frequencyDLL> freqListMap;
        int capacity;
        int lowestFreq;

        // LFU cache constructor
        public LFUCache(int capacity) {

            keyNodeMap = new HashMap<>();
            freqListMap = new HashMap<>();
            this.capacity = capacity;
        }


        public int get(int key) {

            // if key-node hashmap does not have given key, output -1
            if(!keyNodeMap.containsKey(key)) {

                return -1;
            }
            // get the node if map has the key, and update frequency DLL with new use counter of node in a new frequency DLL before returning value of node
            Node node = keyNodeMap.get(key);
            updateFrequencyDLL(node);
            return node.value;
        }


        public void put(int key, int value) {

            // if map has key, update value of node and update frequency DLL list
            if(keyNodeMap.containsKey(key)) {

                Node node = keyNodeMap.get(key);
                node.value = value;

                updateFrequencyDLL(node);
            }

            // if key is new to map
            else {

                // check if capacity is at full
                if(capacity == keyNodeMap.size()) {

                    // if full capacity, remove key of LRU node, from LFU DLL, from key-node map
                    frequencyDLL lowFreqList = freqListMap.get(lowestFreq);
                    Node LRU = lowFreqList.removeLRU();
                    keyNodeMap.remove(LRU.key);
                }

                // add fresh node to key-node map
                Node fresh = new Node(key, value);
                keyNodeMap.put(key, fresh);

                // lowest frequency is 1 now due to fresh key and create frequency DLL with 1 frequency if not there already
                lowestFreq = 1;
                frequencyDLL lowestFreqList = freqListMap.getOrDefault(1, new frequencyDLL());
                // add fresh node to head of this lowest(1) frequency DLL and insert in map
                lowestFreqList.addToHead(fresh);
                freqListMap.put(1, lowestFreqList);

            }

        }

        public void updateFrequencyDLL(Node node) {

            // remove node from old list got from old counter, increment the lowest frequency if old list is empty now and old counter was the lowest frequency
            int oldUseCounter = node.useCounter;
            frequencyDLL oldList = freqListMap.get(oldUseCounter);

            oldList.remove(node);
            if(oldUseCounter == lowestFreq && oldList.listSize == 0) {
                lowestFreq++;
            }

            //create new list with new counter if is not there, add node to head of new list and insert new counter (frequency)-new list (frequency DLL) in frequency map
            int newUseCounter = oldUseCounter + 1;
            node.useCounter = newUseCounter;
            frequencyDLL newList = freqListMap.getOrDefault(newUseCounter, new frequencyDLL());

            newList.addToHead(node);
            freqListMap.put(newUseCounter, newList);
        }

        public static void main(String[] args) {


            LFUCache lfu = new LFUCache(2);

            lfu.put(1,1);
            System.out.println("LFU cache now after put: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            lfu.put(2,2);
            System.out.println("LFU cache now after put: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            System.out.println("Get value: ");
            System.out.println(lfu.get(1));
            System.out.println("LFU cache now after get: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            lfu.put(3,3);
            System.out.println("LFU cache now after put: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            System.out.println("Get value: ");
            System.out.println(lfu.get(2));
            System.out.println("LFU cache now after get: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            System.out.println("Get value: ");
            System.out.println(lfu.get(3));
            System.out.println("LFU cache now after get: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            lfu.put(4,4);
            System.out.println("LFU cache now after put: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            System.out.println("Get value: ");
            System.out.println(lfu.get(1));
            System.out.println("LFU cache now after get: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            System.out.println("Get value: ");
            System.out.println(lfu.get(3));
            System.out.println("LFU cache now after get: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

            System.out.println("Get value: ");
            System.out.println(lfu.get(4));
            System.out.println("LFU cache now after get: ");
            for (int key : keyNodeMap.keySet()) {
                System.out.println(key + " ");
            }

        }

}

/*
TIME COMPLEXITY = O(1)

SPACE COMPLEXITY = O(C)
C = capacity of LFU cache
*/

/*
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[4,3], cnt(4)=2, cnt(3)=3
 */
/*
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */