// Time Complexity : O(1)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

public class LFUCache {
    class LFUCache {

        class Node{
            int key, val, freq;
            Node next, prev;

            public Node(int key, int val){
                this.key = key;
                this.val = val;
                this.freq = 1;
                this.next = null;
                this.prev = null;
            }
        }

        class DLList{
            Node head;
            Node tail;
            int size;

            public DLList(){
                this.head = new Node(-1, -1);
                this.tail = new Node(-1, -1);
                this.size = 0;

                head.next = tail;
                tail.prev = head;
            }

            private void addToHead(Node node){
                node.next = head.next;
                node.prev = head;
                head.next = node;
                node.next.prev = node;
                size++;
            }

            private void removeNode(Node node){
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            private Node removeLastNode(){
                Node node = tail.prev;
                removeNode(node);
                return node;
            }
        }

        int minFreq;
        int capacity;
        Map<Integer, Node> map;
        Map<Integer, DLList> freqMap;

        public LFUCache(int capacity) {

            this.capacity = capacity;
            this.minFreq = 1;
            this.map = new HashMap<>();
            this.freqMap = new HashMap<>();
        }

        public int get(int key) {
            if(!map.containsKey(key))
                return -1;

            Node node = map.get(key);
            update(node);

            return node.val;
        }

        private void update(Node node){
            int oldCnt = node.freq;
            DLList oldList = freqMap.get(oldCnt);
            //remove node from old list
            oldList.removeNode(node);
            if(oldCnt == minFreq && oldList.size == 0){
                minFreq++;
            }
            //add to head of new list
            int newCnt = oldCnt + 1;
            DLList newList = freqMap.getOrDefault(newCnt, new DLList());
            node.freq = newCnt;
            newList.addToHead(node);
            freqMap.put(newCnt, newList);
        }

        public void put(int key, int value) {
            if(map.containsKey(key)){
                Node node = map.get(key);
                node.val = value;
                update(node);
            }
            else{
                if(capacity == 0)
                    return;

                if(capacity == map.size()){
                    DLList list = freqMap.get(minFreq);
                    Node oldNode = list.removeLastNode();
                    map.remove(oldNode.key);
                }
                Node newNode = new Node(key, value);
                minFreq = 1;
                DLList minList = freqMap.getOrDefault(minFreq, new DLList());
                minList.addToHead(newNode);
                map.put(key, newNode);
                freqMap.put(minFreq, minList);
            }
        }
    }
}
