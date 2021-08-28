package problem1;

public class ListNode {
	int key;
	int value;
	int count;

	ListNode prev;
	ListNode next;

	public ListNode(int key, int value) {
		this.key = key;
		this.value = value;
		this.count = 1;
	}
}
