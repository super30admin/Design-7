// Time complexity is O(1)
// Space complexity is O(n)
// This solution is submitted on leetcode

import java.util.HashMap;

public class BigN126LeastFrequentlyUsedCache {
	class LFUCache {
		class Node {
			Node next;
			Node prev;
			int key;
			int val;
			int count;

			public Node(int key, int value) {
				this.key = key;
				this.val = value;
				this.count = 1;
			}
		}

		class DoubleList {
			Node head;
			Node tail;
			int size;

			public DoubleList() {
				head = new Node(-1, -1);
				tail = new Node(-1, -1);
				head.next = tail;
				tail.prev = head;
			}

			public void addToHead(Node node) {
				node.next = head.next;
				node.prev = head;
				head.next = node;
				node.next.prev = node;
				size++;
			}

			public void removeAnyNode(Node node) {
				node.next.prev = node.prev;
				node.prev.next = node.next;
				size--;
			}

			public Node removeLast() {
				if (size > 0) {
					Node tailPrev = tail.prev;
					removeAnyNode(tailPrev);
					return tailPrev;
				}
				return null;
			}
		}

		HashMap<Integer, Node> map;
		HashMap<Integer, DoubleList> freqMap;
		int cacheSize;
		int capacity;
		int min;

		public LFUCache(int capacity) {
			map = new HashMap<>();
			freqMap = new HashMap<>();
			this.capacity = capacity;
		}

		public int get(int key) {
			Node temp = map.get(key);
			if (temp != null) {
				update(temp);
				return temp.val;
			}
			return -1;
		}

		public void put(int key, int value) {
			Node node;
			if (map.containsKey(key)) {
				node = map.get(key);
				node.val = value;
				update(node);
			} else {
				if (capacity == 0)
					return; // edge
				if (cacheSize == capacity) {
					DoubleList fullList = freqMap.get(min);
					Node reference = fullList.removeLast();
					map.remove(reference.key);
					cacheSize--;
				}
				node = new Node(key, value);
				cacheSize++;
				min = 1;
				DoubleList nlist = freqMap.getOrDefault(1, new DoubleList());
				nlist.addToHead(node);
				freqMap.put(1, nlist);
				map.put(key, node);
			}
		}

		private void update(Node node) {
			DoubleList oldList = freqMap.get(node.count);
			oldList.removeAnyNode(node);
			if (oldList.size == 0 && node.count == min)
				min++;
			node.count++;
			DoubleList newList = freqMap.getOrDefault(node.count, new DoubleList());
			newList.addToHead(node);
			freqMap.put(node.count, newList);
		}
	}

	/**
	 * Your LFUCache object will be instantiated and called as such: LFUCache obj =
	 * new LFUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
	 */
}