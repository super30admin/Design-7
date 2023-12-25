// Time Complexity:  O(1)
// Space Complexity: O(n)

class LFUCache {

    // Class Node
    class Node {
        int key;
        int val;
        int count;
        Node next;
        Node prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.count = 1;
            this.next = null;
            this.prev = null;
        }
    }

    // Class DLList
    class DLList {
        Node dummyHead;
        Node dummyTail;
        int listSize;
        public DLList() {
            dummyHead = new Node(-1, -1);
            dummyTail = new Node(-1, -1);
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
            listSize = 0;
        }
        private void addToHead(Node node) {
            node.next = dummyHead.next;
            node.prev = dummyHead.next.prev;
            dummyHead.next.prev = node;
            dummyHead.next = node;
            listSize++;
        }
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            listSize--;
        }
        private Node removeLast() {                   // need to return node as we can use it when LRU node with same frequency comes, and need to update in freqMap
            Node lastNode = dummyTail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    Map<Integer, Node> map;
    Map<Integer, DLList> freqMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
        this.min = 0;
    }
    
    public int get(int key) {
        if(!map.containsKey(key))                      // if key not present
            return -1;
        Node node = map.get(key);                      // if present
        update(node);                                  // update freqMap
        return node.val;                               // return val
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)) {                     // if key existing
            Node node = map.get(key);                  
            node.val = value;                          // update val
            update(node);                              // update freqMap
        }
        else {                                         // if new key
            if(capacity == 0)                          // capacity 0, then do nothing
                return; 
            if(map.size() == capacity) {               // if used all space
                DLList list = freqMap.get(min);     
                Node lfuNode = list.removeLast();      // remove LFU node
                map.remove(lfuNode.key);          
            }
            Node newNode = new Node(key, value);       
            min = 1;
            DLList newList = freqMap.getOrDefault(1, new DLList());
            newList.addToHead(newNode);                
            freqMap.put(1, newList);                   // add new node at freq 1
            map.put(key, newNode);                     // add in map
        }
    }

    private void update(Node node) {
        
        int oldCount = node.count;       
        DLList list = freqMap.get(oldCount);
        list.removeNode(node);                        // remove node from old freq

        if(oldCount == min && list.listSize == 0) {   // if no node left in that freq list
            min++;                                    // then update min
        }

        node.count++;                                 // update count of that node

        DLList newList = freqMap.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        freqMap.put(node.count, newList);             // add node to updated count freq

    }

}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */









// // ******************** Another method : simple way same as LRU, but we need to iterate through list ********************

// class LFUCache {

//     class Node {
//         int num;
//         int val;
//         int counter;
//         Node next;
//         Node prev;
//         public Node(int num, int val) {
//             this.num = num;
//             this.val = val;
//             this.counter = 1;
//             this.next = null;
//             this.prev = null;
//         }
//     }

//     Map<Integer, Node> map;
//     int size;
//     Node dummyHead;
//     Node dummyTail;

//     public LFUCache(int capacity) {
//         this.map = new HashMap<>();
//         this.size = capacity;
//         dummyHead = new Node(-1, -1);
//         dummyTail = new Node(-1, -1);
//         dummyHead.next = dummyTail;
//         dummyTail.prev = dummyHead;
//     }
    
//     public int get(int key) {
        
//         if(!map.containsKey(key)) return -1;
        
//         Node node = map.get(key);
//         node.counter = node.counter+1;
        
//         Node curr = node;
//         while(curr.next.val >= 0 && curr.next.counter <= node.counter) {
//             curr = curr.next;
//         }

//         removeNode(node);
//         addNode(node);

//         return node.val;
//     }
    
//     public void put(int key, int value) {

//         if(map.containsKey(key)) {
//             Node node = map.get(key);
//             node.val = value;
//             node.counter++;
//             removeNode(node);
//             addNode(node);
//         }

//         else {
            
//             Node newNode = new Node(key, value);
            
//             if(map.size() < size) {
//                 addNode(newNode);
//                 map.put(key, newNode);
//                 return;
//             }

//             map.remove(dummyHead.next.num);
//             removeNode(dummyHead.next);
            
//             addNode(newNode);
//             map.put(key, newNode);

//         }

//     }

//     public void removeNode(Node node) {
//         node.next.prev = node.prev;
//         node.prev.next = node.next;
//         node.next = null;
//         node.prev = null;
//     }

//     public void addNode(Node newNode) {
//         Node curr = dummyHead;
//         while(curr.next.val >= 0 && curr.next.counter <= newNode.counter) {
//             curr = curr.next;
//         }
        
//         newNode.next = curr.next;
//         curr.next.prev = newNode;
//         curr.next = newNode;
//         newNode.prev = curr;
//     }

// }

// /**
//  * Your LFUCache object will be instantiated and called as such:
//  * LFUCache obj = new LFUCache(capacity);
//  * int param_1 = obj.get(key);
//  * obj.put(key,value);
//  */

