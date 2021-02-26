import java.util.*;

public class LFUCache {

	// Since we are using Doubly LinkedList, 2 HashMapto store key and its reference to Node in LL and frequency and DLL
	// We can store key value pairs in DLL such that we can do get operations of a key, and Key and reference to Node we can get from hashMap
	// Create a class node to store the key and value pairs keeping reference to next and previous values, Count to keep the number of nodes 
	class Node{
		
		int key, val, cnt;
		Node next, prev;
		public Node(int key, int val) {
			this.key = key;
			this.val = val;
			cnt = 1;
			next = null;
			prev = null;
		}
	}
	
	class DLLList{
		
		Node head, tail;
		int size = 0;
		// Head and tail so that we can modify the first node value.
		public DLLList() {
		
			head = new Node(0,0);
			tail = new Node(0,0);
			head.next = tail;
			tail.prev = head;
			
		}
		
		// add the nodes after the head node value, increment the size of the LinkedList such that we can keep track of capacity
		private void add(Node node) {
			
			Node temp = head.next;
			temp.prev = node;
			node.next = temp;
			node.prev = head;
			head.next = node;
			size++;
			
		}
		
		// remove the node from the linkedlist, decrement the size of the linkedlist
		private void remove(Node node) {
			Node before = node.prev;
			Node after = node.next;
			before.next = after;
			after.prev = before;
			size--;
		}
		
		// remove the last element from the LinkedList if the size is equal to capacity, we will remove the last element from LL
		private Node removeLast() {
			
			if(size > 0) {
				Node node = tail.prev;
				remove(node);
				return node;
			}
			// return null is size is 0;
			return null;
				
		}
	}
	
	HashMap<Integer, Node> nodeMap;  // nodeMap to keep track of key and Node values.
	HashMap<Integer, DLLList> cntMap;  // cntMap to keep track of count and reference of DLL
	int capacity, size, min;
	
	public LFUCache(int capacity) {
		
		this.capacity = capacity;  
		nodeMap = new HashMap<>();
		cntMap = new HashMap<>();
	}
	
	public int get(int key) {
		// retrieve the node value for the given key from the HashMap
		Node node = nodeMap.get(key);
		// if the node does not exist in Hashmap, return -1;
		if(node == null)
			return -1;
		
		// if the node is present, update the frequency and the LinkedList Node
		update(node);
		return node.val; // return the node value from the hashMap
	}
	
	public void put(int key, int val) {
		
		// if capacity is 0, return 
		if(capacity == 0)
			return;
		
		Node node;
		// if the key is present in nodeMap, retrieve the node from the HashMap, update the frequency of the node with the cntMap
		if(nodeMap.containsKey(key)) {
			node = nodeMap.get(key);
			node.val = val;
			update(node);
		}else {
			// if the key is not present in nodeMap, store the key and values pairs in node and update the nodeMap
			node = new Node(key, val);
			nodeMap.put(key, node);
			
			// if the size is same as capacity, retrieve the List from cntMap, and remove the lastNode from the nodeMap because we need to remove
			// the key and Node pair if the Cache is full
			if(size == capacity) {
				DLLList lru = cntMap.get(key);
				nodeMap.remove(lru.removeLast().key);
				size--;
			}
			size++;
			min = 1;  // initialize the min value so that we can update it later if the cnt is equal is min(1)
			// update the frequency HashMap with new node values and and count which is initialised to 1 in Constructor Node
			DLLList newList = cntMap.getOrDefault(node.cnt, new DLLList());
			newList.add(node);
			// add the count and new Node List into cntMap and update the frequency later in the update function
			cntMap.put(node.cnt, newList);
			
		}
	}
	
	public void update(Node node) {
	// we need remove the old values from the HashMap and update it with new frequency
		// keep track of old list node
		DLLList oldList = cntMap.get(node.cnt);
		// remove the node from the list
		oldList.remove(node);
		// if the cnt is same as min(1) and list size is 0, update the min
		if(node.cnt == min && oldList.size == 0) {
			min++;
		}
		node.cnt++; // update the cnt of the node		
		DLLList newList = cntMap.getOrDefault(node.cnt, new DLLList()); // get the newList for the frequency
		// add the new node into newList node
		newList.add(node);
		// put the newList with newNode and cnt back to HashMap(update frequency)
		cntMap.put(node.cnt, newList);
	}
	
}
