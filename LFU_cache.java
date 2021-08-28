
//TC - O(1)
//SC - O(N)
import java.util.*;

class LFUCache {
	class Node {
		Node prev;
		Node next;
		int key;
		int val;
		int cnt;

		public Node(int key, int val) {
			this.key = key;
			this.val = val;
			this.cnt = 1;
		}
	}

	class DLList {
		Node head;
		Node tail;
		int size;

		public DLList() {
			head = new Node(-1, -1);
			tail = new Node(-1, -1);
			head.next = tail;
			tail.next = head;
		}

		public void addToHead(Node node) {
			node.next = head.next;
			node.prev = head;
			node.next.prev = node;
			head.next = node;
			size++;
		}

		public void removeNode(Node node) {
			node.prev.next = node.next;
			node.next.prev = node.prev;
			size--;
		}

		public Node removeLast() {
			Node tailPrev = tail.prev;
			removeNode(tailPrev);
			return tailPrev;
		}
	}

	HashMap<Integer, DLList> freq;
	HashMap<Integer, Node> map;
	int capacity;
	int min;

	public LFUCache(int capacity) {
		this.freq = new HashMap<>();
		this.map = new HashMap<>();
		this.capacity = capacity;
	}

	public int get(int key) {
		if (map.containsKey(key)) {
			Node node = map.get(key);
			update(node);
			return node.val;
		}
		return -1;
	}

	public void put(int key, int value) {
		if (map.containsKey(key)) {
			Node node = map.get(key);
			node.val = value;
			update(node);
		} else {
			if (capacity == 0)
				return;
			if (map.size() == capacity) {
				DLList minList = freq.getOrDefault(min, new DLList());
				Node toBeRemoved = minList.removeLast();
				map.remove(toBeRemoved.key);
			}
			Node newNode = new Node(key, value);
			min = 1;
			DLList minList = freq.getOrDefault(min, new DLList());
			minList.addToHead(newNode);
			freq.put(min, minList);
			map.put(key, newNode);
		}
	}

	public void update(Node node) {
		DLList oldList = freq.get(node.cnt);
		oldList.removeNode(node);
		if (node.cnt == min && oldList.size == 0)
			min++;
		node.cnt++;
		DLList newList = freq.getOrDefault(node.cnt, new DLList());
		newList.addToHead(node);
		freq.put(node.cnt, newList);
		map.put(node.key, node);
	}
}

/**
 * Your LFUCache object will be instantiated and called as such: LFUCache obj =
 * new LFUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */