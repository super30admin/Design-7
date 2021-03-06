//Time Complexity: o(n)
//Space Complexity: o(n)
//Maintain a HashMap with value as doubly linked list and maintain another hashmap for key value and o(1)
// computaion to modify the other frequency map so to update based on frequency and to remove the nodes etc
class LFUCache {
    //Node node with freq 1
    class Node{
        Node prev;
        Node next;
        int val;
        int freq;
        int key;
        public Node(int key, int val)
        {
            this.val = val;
            this.key = key;
            this.freq = 1;
        }
    }
    //Doubly linked list class with remove last node, remove any node by node and adding to head in new freq map are the methods here
    class DLL{
        Node head;
        Node tail;
        int size;
        public DLL()
        {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }
        //Add to head of the linkedlist in the freqmap
        private void addtoHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }
        //remove any node given the node from the help of the index map
        private void removeNode(Node node)
        {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
        //remove the last node
        private Node removelastNode()
        {
            Node tailnode = tail.prev;
            removeNode(tailnode);
            return tailnode;
        }
    }
    Map<Integer, Node> indexmap;
    Map<Integer, DLL> freqmap;
    int lfreq;
    int capacity;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        lfreq = 1;
        indexmap = new HashMap<>();
        freqmap = new HashMap<>();
    }
    //get the value from the index map and update the node by changing the freq level 
    public int get(int key) {
        if(!indexmap.containsKey(key)) return -1;
        Node node = indexmap.get(key);
        updateNode(node);
        return node.val;
    }
    //update the node by getting the freq and removing from that list and adding to head of the new freq level in freqmap
    private void updateNode(Node node)
    {
        DLL oldlist = freqmap.get(node.freq);
        oldlist.removeNode(node);
        if(node.freq == lfreq && oldlist.size == 0)
        {
            lfreq++;
        }
        node.freq++;
        DLL newlist = freqmap.getOrDefault(node.freq, new DLL());
        newlist.addtoHead(node);
        freqmap.put(node.freq, newlist);
    }
    // if the index map contains already update the value and update the freq levels
    // else check for the capacity and remove the last node from least freq and add this node to freq 1. 
    // and add it to index map to.
    public void put(int key, int value) {
        if(indexmap.containsKey(key))
        {
            Node node = indexmap.get(key);
            node.val = value;
            updateNode(node);
        }
        else
        {
            if(capacity == 0 ) return;
            //capacity is full
            if(capacity == indexmap.size())
            {
                DLL minlist = freqmap.get(lfreq);
                Node remove = minlist.removelastNode();
                indexmap.remove(remove.key);
            }
            Node newnode = new Node(key, value);
            lfreq = 1;
            DLL list = freqmap.getOrDefault(lfreq, new DLL());
            list.addtoHead(newnode);
            freqmap.put(lfreq, list);
            indexmap.put(key, newnode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */