//Time Complexity: Get- O(1), Put - O(1)
//Space Complexity: O(n)
//Code run successfully on LeetCode.

public class Problem1 {

	class LFUCache {
	    
	    class Node
	    {
	        
	        int key, value;
	        int cnt;
	        
	        Node next, prev;
	        
	        public Node(int key, int value)
	        {
	            this.key = key;
	            this.value = value;
	            this.cnt = 1;
	        }
	    }
	    
	    public class DLList
	    {
	        
	        Node head, tail;
	        int size;
	        
	        public DLList()
	        {
	            this.head = new Node(-1,-1);
	            this.tail = new Node(-1,-1);
	            head.next = tail;
	            tail.prev = head;
	        }
	        
	        public void addToHead(Node node)
	        {
	            node.next = head.next;
	            node.prev = head;
	            node.next.prev = node;
	            head.next = node;
	            size++;
	        }
	        
	        public void removeNode(Node node)
	        {
	            node.next.prev = node.prev;
	            node.prev.next = node.next;
	            size--;
	        }
	        
	        public Node removeLast()
	        {
	            Node lastNode = tail.prev;
	            removeNode(lastNode);
	            return lastNode;
	        }
	    }
	    
	    int capacity, min;
	    HashMap<Integer, Node> map;
	    HashMap<Integer, DLList> freqMap;
	    
	    public LFUCache(int capacity) {
	        
	        this.capacity = capacity;
	        map = new HashMap<>();
	        freqMap = new HashMap<>();
	    }
	    
	    private void update(Node node)
	    {
	        
	        DLList oldList = freqMap.get(node.cnt);
	        
	        if(oldList != null)
	        {
	            oldList.removeNode(node);
	        }
	        
	        if(min == node.cnt && oldList.size == 0)
	            min++;
	        
	        node.cnt++;
	        
	        DLList newList = freqMap.getOrDefault(node.cnt, new DLList());
	        newList.addToHead(node);
	        freqMap.put(node.cnt, newList);
	        
	    }
	    public int get(int key) {
	        
	        if(!map.containsKey(key))
	            return -1;
	        
	        Node node = map.get(key);
	        update(node);
	        
	        return node.value;
	    }
	    
	    public void put(int key, int value) {
	        
	        if(map.containsKey(key))
	        {
	            Node node = map.get(key);
	            update(node);
	            node.value = value;
	            return;
	        }
	        
	        if(capacity == 0)
	            return;
	        
	        if(capacity == map.size())
	        {
	            DLList oldList = freqMap.get(min);
	            
	            Node oldNode = oldList.removeLast();
	            map.remove(oldNode.key);
	        }
	        
	        Node node = new Node(key, value);
	        min = 1;
	        DLList newList = freqMap.getOrDefault(min, new DLList());
	        
	        newList.addToHead(node);
	        freqMap.put(min,newList);
	        map.put(key,node);
	    }
	}
}
