// Time Complexity : Get: O(1), Put: O(1)
// Space Complexity : O(capacity)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
package problem1;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
	Map<Integer, ListNode> nodeMap;
	Map<Integer, DLL> freqMap;
	int minFreq;
	int capacity;

	public LFUCache(int capacity) {
		nodeMap = new HashMap<>();
		freqMap = new HashMap<>();

		this.capacity = capacity;
		this.minFreq = 0;
	}

	public int get(int key) {
		if (!nodeMap.containsKey(key)) {
			return -1;
		}
		ListNode node = nodeMap.get(key);
		updateFreq(node);
		return node.value;
	}

	public void put(int key, int value) {
		if (capacity == 0) {
			return;
		}

		if (nodeMap.containsKey(key)) {
			ListNode node = nodeMap.get(key);
			updateFreq(node);
			node.value = value;
		} else {
			if (capacity == nodeMap.size()) {
				DLL minList = freqMap.get(minFreq);
				ListNode toRemove = minList.removeFromTail();
				nodeMap.remove(toRemove.key);
			}

			ListNode newNode = new ListNode(key, value);
			minFreq = 1;
			nodeMap.put(key, newNode);
			DLL minList = freqMap.getOrDefault(minFreq, new DLL());
			minList.addToHead(newNode);
			freqMap.put(minFreq, minList);
		}
	}

	private void updateFreq(ListNode node) {
		DLL oldList = freqMap.getOrDefault(node.count, new DLL());
		oldList.removeNode(node);
		if (oldList.size == 0) {
			if (minFreq == node.count) {
				minFreq++;
			}
			freqMap.remove(node.count);
		}

		node.count++;

		DLL newList = freqMap.getOrDefault(node.count, new DLL());
		newList.addToHead(node);
		freqMap.put(node.count, newList);
	}

	public static void main(String[] args) {
		LFUCache obj = new LFUCache(2);
		obj.put(1, 1);
		obj.put(2, 2);
		System.out.println(obj.get(1));
		obj.put(3, 3);
		System.out.println(obj.get(2));
		System.out.println(obj.get(3));
		obj.put(4, 4);
		System.out.println(obj.get(1));
		System.out.println(obj.get(3));
		System.out.println(obj.get(4));
	}

}
