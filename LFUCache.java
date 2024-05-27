// Time Complexity (TC): O(1) for both get and put operations.
// Space Complexity (SC): O(capacity), where capacity is the maximum number of keys the cache can hold

import java.util.HashMap;

class LFUCache {
    class Node
    {
        int key;
        int val;
        int count;
        Node next;
        Node prev;

        public Node(int key, int val)
        {
            this.key = key;
            this.val = val;
            this.count =1;
        }
    }

    class DLList
    {
        Node head;
        Node tail;

        int size;

        public DLList()
        {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = tail;
            this.tail.prev = head;
        } 

        private void addToHead(Node node)
        {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }

        private void removeNode(Node node)
        {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        private Node removeTail()
        {
            Node toRemove = tail.prev;
            removeNode(toRemove);
            return toRemove;
        }
    
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int min;
    int capacity;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        update(node);
        return node.val;
    }

    private void update(Node node)
    {
        DLList oldList = freqMap.get(node.count);
        oldList.removeNode(node);

        //imp check
        if(node.count == min && oldList.size == 0)
        {
            min++;
        }
        node.count++;
        DLList newList = freqMap.getOrDefault(node.count, new DLList());

        //add that node to new list to head
        newList.addToHead(node);

        freqMap.put(node.count, newList);
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key))
        {
            Node node = map.get(key);
            node.val = value;
            update(node);
        }
        else
        {
            //fresh
            if(capacity == map.size())
            {
                //remove the least frequent used node
                DLList minFreq = freqMap.get(min);
                Node toRemove = minFreq.removeTail();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList minFreq = freqMap.getOrDefault(min, new DLList());
            minFreq.addToHead(newNode);
            freqMap.put(1, minFreq);
            map.put(key, newNode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */