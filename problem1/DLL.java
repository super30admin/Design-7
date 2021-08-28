package problem1;

public class DLL {
	ListNode head;
	ListNode tail;
	int size;

	public DLL() {
		head = new ListNode(-1, -1);
		tail = new ListNode(-1, -1);

		head.next = tail;
		tail.prev = head;

		size = 0;
	}

	public void addToHead(ListNode node) {
		node.next = head.next;
		node.prev = head;
		head.next = node;
		node.next.prev = node;
		size++;
	}

	public void removeNode(ListNode node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
		size--;
	}

	public ListNode removeFromTail() {
		ListNode node = tail.prev;
		removeNode(node);
		return node;
	}
}
