// TC => O(1)
// SC => O(1)
public class LFUCache {
    public class Node{
        public int key, value, freq;
        public Node next, prev;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    public class DDList{
        public Node head, tail;
        public int size;
        public DDList(){
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }

        public void Remove(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        public void AddToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }

        public Node RemoveLastNode(){
            Node lastNode = tail.prev;
            Remove(lastNode);
            return lastNode;
        }
    }

    Dictionary<int, DDList> freqMap;
    Dictionary<int, Node> map;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        freqMap = new Dictionary<int, DDList>();
        map = new Dictionary<int, Node>();
        this.capacity = capacity;
        this.min = 0;
    }
    
    public int Get(int key) {
        // Console.WriteLine("Get " + key);
        if(!map.ContainsKey(key)){
            return -1;
        }
        Node node = map[key];
        Update(node);
        return node.value;
    }
    
    public void Put(int key, int value) {
        if(map.ContainsKey(key)){
            Node node = map[key];
            Update(node);
            node.value = value;
            return;
        }
        if(capacity == 0){
            return;
        }
        Node current = null;
        if(capacity == map.Count){
            DDList oldList = freqMap[min];
            Node lastNode = oldList.RemoveLastNode();
            // Console.WriteLine(lastNode.value);
            freqMap[min] = oldList;
            // Console.WriteLine("OldList in PUT");
            // current = oldList.head;
            // while(current != null){
            //     Console.WriteLine(current.value);
            //     current = current.next;
            // }
            map.Remove(lastNode.key);
            // Console.WriteLine(string.Join(",", map.Keys));
        }
        Node newNode = new Node(key, value);
        min = 1;
        freqMap.TryAdd(min, new DDList());
        freqMap[min].AddToHead(newNode);
        map.Add(key, newNode);
        // Console.WriteLine("newList in PUT");
        //     current = freqMap[min].head;
        //     while(current != null){
        //         Console.WriteLine(current.value);
        //         current = current.next;
        //     }
        //     Console.WriteLine(string.Join(",", map.Keys));
    }

    public void Update(Node node){
        // Console.WriteLine("Inside Update "+ node.value + " "+ node.freq);
        // Console.WriteLine("freqMap " + string.Join(",", freqMap.Keys));
        var oldList = freqMap[node.freq];
        oldList.Remove(node);
        if(min == node.freq && oldList.size == 0){
            min++;
        }
        freqMap.TryAdd(min, new DDList());
        node.freq++;
        DDList newList = freqMap.ContainsKey(node.freq) ? freqMap[node.freq] : new DDList();
        newList.AddToHead(node);
        
        freqMap[node.freq] = newList;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.Get(key);
 * obj.Put(key,value);
 */
