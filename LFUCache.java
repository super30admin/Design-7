//https://leetcode.com/problems/lfu-cache/
/*
Time: O(1) for get, put, add, remove
Space: O(n) for HashMap + DLL
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : None
*/
class LFUCache {

    ListNode head;
    ListNode tail;
    Map<Integer, ListNode> map = null;
    int capacity = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    public int get(int key) {

        if (map.get(key) == null || capacity == 0)
            return -1;

        ListNode item = map.get(key);
        removeNode(item); // move to correct position according to it's frequency
        item.frequency = item.frequency + 1; // increment frequency
        addNodeWithUpdatedFrequency(item);

        return item.value;

    }

    public void put(int key, int value) {
        // Key already exists
        if (map.containsKey(key)) {
            ListNode item = map.get(key);
            item.value = value;
            item.frequency = item.frequency + 1;
            // move to right position according to frequency
            removeNode(item);
            addNodeWithUpdatedFrequency(item);
        }

        else // Key does not exists
        {
            if (map.size() >= capacity) {
                // delete head with least frequency and least recently used
                if (head != null) {
                    map.remove(head.key);
                    removeNode(head);
                }
            }

            // move to right position according to frequency
            ListNode node = new ListNode(key, value, 1);
            addNodeWithUpdatedFrequency(node);
            map.put(key, node);
        }

    }

    private void removeNode(ListNode nodeToRemove) {

        if (nodeToRemove.prev != null)
            nodeToRemove.prev.next = nodeToRemove.next;
        else
            head = nodeToRemove.next;

        if (nodeToRemove.next != null)
            nodeToRemove.next.prev = nodeToRemove.prev;
        else
            tail = nodeToRemove.prev;

    }

    private void addNodeWithUpdatedFrequency(ListNode nodeToAdd) {
        if (tail != null && head != null) {
            ListNode curr = head;
            while (curr != null) {
                if (curr.frequency > nodeToAdd.frequency) {
                    if (curr == head) {
                        nodeToAdd.next = curr;
                        curr.prev = nodeToAdd;
                        head = nodeToAdd;
                        break;
                    } else {
                        nodeToAdd.next = curr;
                        nodeToAdd.prev = curr.prev;
                        curr.prev.next = nodeToAdd;
                        nodeToAdd.prev = curr.prev;
                        break;
                    }
                } else // curr.frequency <= nodeToAdd.frequency
                {
                    curr = curr.next;
                    if (curr == null) {
                        tail.next = nodeToAdd;
                        nodeToAdd.prev = tail;
                        nodeToAdd.next = null;
                        tail = nodeToAdd;
                        break;
                    }
                }
            }
        } else // head and tail are null. nodeToAdd will be head & tail
        {
            tail = nodeToAdd;
            head = tail;
        }
    }
}

public class ListNode {
    int key;
    int value;
    int frequency;
    ListNode prev;
    ListNode next;

    public ListNode(int key, int value, int frequency) {
        this.key = key;
        this.value = value;
        this.frequency = frequency;
    }
}
