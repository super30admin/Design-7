// Time Complexity : O(1)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : Yes
// Three line explanation of solution in plain english
//Make a class for node which has key, value, prev, next, cnt. Make a class for DLList which has head, tail, size and 3 fucntion addToHead, removeNode, removeFromEnd. In the general class make an update function to remove the node from the list and increse its count and update to the new list based on the cnt and update to the freqMap, don't forget to check if min == cnt and list that we get is not empty() if then increase min.
// Your code here along with comments explaining your approach
class LFUCache {
    class Node{
        int key; int val;
        int cnt;
        Node prev; Node next;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;
        public DLList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }

        private void addToHead(Node node){
            node.prev = head;
            node.next = head.next;
            head.next = node;
            node.next.prev = node;
            this.size++;
        }

        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        private Node removeLast(){
            Node temp = tail.prev;
            removeNode(temp);
            return temp;
        }

    }
    HashMap<Integer, Node> hm;
    HashMap<Integer, DLList> fHm;
    int capacity; int min;
    public LFUCache(int capacity) {
        this.hm = new HashMap<>();
        this.fHm = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if(!hm.containsKey(key)) return -1;
        Node temp = hm.get(key);
        update(temp);
        return temp.val;
    }

    public void put(int key, int value) {
        if(hm.containsKey(key)){
            Node temp = hm.get(key);
            temp.val = value;
            update(temp);
        }else{
            if(capacity == 0) return;
            if(capacity == hm.size()){
                DLList minlst = fHm.get(min);
                Node temp = minlst.removeLast();
                hm.remove(temp.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList newLs = fHm.getOrDefault(1,new DLList());
            newLs.addToHead(newNode);
            fHm.put(1,newLs);
            hm.put(key, newNode);
        }
    }

    private void update(Node node){
        DLList oldList = fHm.get(node.cnt);
        oldList.removeNode(node);
        if(min == node.cnt && oldList.size == 0) min++;
        node.cnt++;
        DLList newList = fHm.getOrDefault(node.cnt, new DLList());
        newList.addToHead(node);
        fHm.put(node.cnt, newList);
    }


}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */