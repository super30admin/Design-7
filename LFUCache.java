//time complexity - put is O(1), get is O(1)
//space complexity - O(n)
import java.util.*;
public class LFUCache {
    private class Node {
        int key;
        int val;
        int freq;
        Node prev;
        Node next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }
    private class Dll {
        Node head;
        Node tail;
        int size;
        public Dll() {
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        public void addFirst(Node node) {
            node.next = head.next;
            head.next.prev = node;
            node.prev = head;
            head.next = node;
            this.size++;
        }
        public void removeNode(Node node) {
            if (node == null || node.prev == null || node.next == null) return;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
            this.size--;
        }
        public Node removeTail(){
            Node prev = tail.prev;
            removeNode(prev);
            return prev;
        }
    }
    HashMap<Integer, Node> map;
    HashMap<Integer, Dll> fmap;
    int cap;
    int min;
    public LFUCache(int capacity) {
        map = new HashMap<>();
        fmap = new HashMap<>();
        this.cap = capacity;
        this.min = Integer.MAX_VALUE;

    }
    private void updateNode(Node node) {
        int count = node.freq;
        Dll oldList = fmap.get(count);
        oldList.removeNode(node);
        if(count == min && oldList.size == 0) {
            this.min++;
        }
        node.freq++;
        count++;
        if(!fmap.containsKey(count)) {
            fmap.put(count, new Dll());
        }

        Dll newList = fmap.get(count);
        newList.addFirst(node);

    }
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        updateNode(node);
        return node.val;
    }
    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            updateNode(node);
        } else {

            if(this.cap== map.size())  {
                Dll minList = fmap.get(min);
                Node rem = minList.removeTail();
                map.remove(rem.key);
            }
            Node temp = new Node(key,value);
            this.min = 1;
            map.put(key, temp);
            if(!fmap.containsKey(1)) {
                fmap.put(1, new Dll());

            }
            Dll li = fmap.get(1);
            li.addFirst(temp);
        }
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        System.out.println(cache.get(1));
        cache.put(3,3);
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        cache.put(4,4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));



    }
}
