import java.util.HashMap;

//Time Complexity : O(1)
//Space Complexity : O(n) where n is capacity of the LFU cache
public class LFUCache {	
	/**Approach: HashMap + Doubly Linked List | Time O(1) | Space O(n)**/	
	class Node{
        int key; int val;
        int count;
        Node next; Node prev;
        public Node(int key, int val){
            this.key= key;
            this.val= val;
            this.count= 1;
        }
    }
    class DLList{
        Node head; Node tail;
        int size;
        public DLList(){
            this.head= new Node(-1,-1);
            this.tail= new Node(-1,-1);
            this.head.next= tail;
            this.tail.prev= head;
        }
        public void addToHead(Node node){
            node.next= head.next;
            head.next= node;
            node.next.prev= node;
            node.prev= head;
            size++;
        }
        public void remove(Node node){
            node.next.prev= node.prev;
            node.prev.next= node.next;            
            size--;
        }
        public Node removeLast(){
            Node node= tail.prev;
            remove(node);
            return node;
        }
    }
    
    int min;
    int capacity;
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;    
    public LFUCache(int capacity) {
        this.capacity= capacity;
        this.map= new HashMap<>();
        this.freqMap= new HashMap<>();
    }
    
    public int get(int key) { //O(1)
        if(!map.containsKey(key)) return -1;
        Node node= map.get(key);      
        update(node);        
        return node.val;
    }
    
    public void put(int key, int value) { //O(1)
        //if already exists in cache
        if(map.containsKey(key)){
            Node node= map.get(key);
            node.val= value;
            update(node);
        }
        else{ //fresh Node            
            if(capacity == 0) return;
            
            //if map is full already, remove the LFU node
            if(map.size() == capacity){
                DLList minFreqList= freqMap.get(min);
                Node nodeRemoved= minFreqList.removeLast();
                map.remove(nodeRemoved.key);
            }
            //add node to map and freqMap
            Node newNode= new Node(key, value);
            map.put(key, newNode);
            min=1;
            DLList freqList= freqMap.getOrDefault(1, new DLList());
            freqList.addToHead(newNode);
            freqMap.put(1, freqList);
        }
    }
    
    //Update the frequency in map and position in freqMap
    private void update(Node node){       
        int cnt= node.count;  
        //remove node from old freq list
        DLList oldFreqList= freqMap.get(cnt);
        oldFreqList.remove(node);  
        
        //update min
        if(min == cnt && oldFreqList.size == 0) min++;   
        
        //Update freq of node
        node.count= cnt + 1; 
        
        //add node to next freq list
        DLList newFreqList= freqMap.getOrDefault(node.count, new DLList());
        newFreqList.addToHead(node);
        freqMap.put(node.count, newFreqList);
    }
	
	
	// Driver code to test above
	public static void main (String[] args) {		
		LFUCache lfu= new LFUCache(2);
		lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
		lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
		
		System.out.println("Value of the key 1 :"+lfu.get(1));      // return 1. cache=[1,2], cnt(2)=1, cnt(1)=2		
		
		lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2. cache=[3,1], cnt(3)=1, cnt(1)=2
		                 
		System.out.println("Value of the key 2 :"+lfu.get(2));      // return -1 (not found)
		System.out.println("Value of the key 3 :"+lfu.get(3));      // return 3. cache=[3,1], cnt(3)=2, cnt(1)=2		  
		
		lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1. cache=[4,3], cnt(4)=1, cnt(3)=2
		                  
		System.out.println("Value of the key 1 :"+lfu.get(1));      // return -1 (not found)
		System.out.println("Value of the key 3 :"+lfu.get(3));      // return 3. cache=[3,4], cnt(4)=1, cnt(3)=3		                 
		System.out.println("Value of the key 4 :"+lfu.get(4));      // return 4. cache=[4,3], cnt(4)=2, cnt(3)=3		
	}
}
