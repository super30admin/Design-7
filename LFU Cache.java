/*
class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.count = 1
        self.next = None
        self.prev = None
        
class DLL:
    def __init__(self):
        self.head = Node(-1,-1)
        self.tail = Node(-1,-1)
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0
        
    def addToHead(self, node):
        node.next = self.head.next
        node.prev = self.head
        node.next.prev = node
        self.head.next = node
        self.size += 1
        
    def removeNode(self, node):
        node.next.prev = node.prev
        node.prev.next = node.next
        self.size -= 1
    
    def removeFromTail(self):
        node = self.tail.prev
        self.removeNode(node)
        return node
    
class LFUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.map = dict()
        self.freqmap = dict()
        self.min = 0

    def get(self, key: int) -> int:
        if key in self.map:
            node = self.map[key]
            self.update(node)       # update its freq and also placed the node at head in freqmap   
            return node.value
        return -1

    def put(self, key: int, value: int) -> None:
        if key in self.map:
            node = self.map[key]
            node.value = value
            self.update(node)
        else:
            if self.capacity == 0:
                return
            
            if self.capacity == len(self.map):
                dll = self.freqmap.get(self.min)
                node = dll.removeFromTail()
                del self.map[node.key]
                
            new_node = Node(key, value)
            self.min = 1
            self.map[key] = new_node
            dll = self.freqmap.get(self.min, DLL())
            dll.addToHead(new_node)
            self.freqmap[self.min] = dll
        
    def update(self, node):
        old_count = node.count
        node.count += 1
        dll = self.freqmap[old_count]
        dll.removeNode(node)
        
        if self.min == old_count and dll.size == 0:
            self.min += 1
        
        dl = self.freqmap.get(node.count, DLL())
        dl.addToHead(node)
        self.freqmap[node.count] = dl
        

*/

// time - O(1)
// space - O(n) where n is capacity of lfu
// logic - maintained two hashmaps one for key and node and another for freq and doubly linkedlist. Node will contain all properties like key,value and count
// doubly linkedlist will maintain at head the most frequently used and at tail least frequently used
class Node{
    int key, val, count;
    Node next, prev;
    public Node(int key, int val){
        this.key = key;
        this.val = val;
        this.count = 1;
    }
}

class DLL{
    Node head, tail;
    int size;
    public DLL(){
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public void addToHead(Node node){
        node.next = head.next;
        node.next.prev = node;
        node.prev = head;
        head.next = node;
        size ++;
    }
    
    public void remove(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }
    public Node removeLast(){
        Node node = tail.prev;
        remove(node);
        return node;
    }
}

class LFUCache {
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLL> freqmap;
    int min;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqmap = new HashMap<>();
    }
    
    public int get(int key) {
        if (map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
            return;
        }
        else{
            if (capacity == 0)
                return;
            if (capacity == map.size()){
                DLL list = freqmap.get(min);
                Node node = list.removeLast();
                map.remove(node.key);
                
            }
            Node new_node = new Node(key, value);
            min = 1;
            map.put(key, new_node);
            DLL list = freqmap.getOrDefault(min, new DLL());
            list.addToHead(new_node);
            freqmap.put(min, list);
        }
    }
    
    public void update(Node node){
        int old_count = node.count;
        node.count ++;
        DLL list = freqmap.get(old_count);
        list.remove(node);
        
        if (min == old_count && list.size == 0)
            min ++;
        
        list = freqmap.getOrDefault(node.count, new DLL());
        list.addToHead(node);
        freqmap.put(node.count, list);
    }
}

