/* Time complexity: O(n)
space complexity: O(2n)
*/

class LFUCache{ 
    class Node{
        int key; int val; int cnt;
        Node prev; Node next;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }

    class DLList{
        Node head;
        Node tail; int size;
        public DLList(){
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public void addToHead(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
            size++;
        }

        public Node removeLast(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freeMap;
    int capacity; int min;

    public LFUCache(int capacity){
        this.map = new HashMap<>();
        this.freeMap  = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key){
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            return node.val;
        }

        return -1;
    }

    public void put(int key, int value){
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            update(node);
        }else{
            if(map.size() == capacity){
                DLList minFreqLi = freeMap.get(min);
                Node toBeRemoved = minFreqLi.removeLast();
                map.remove(toBeRemoved);
                freeMap.put(min, minFreqLi);
            }
            Node newNode = new Node(key, value);
            min = 1;
            DLList minList = freeMap.getOrDefault(1, new DLList());
            minList.addToHead(newNode);
            freeMap.put(min, minList);
            map.put(key, newNode);            
        }
    }
}