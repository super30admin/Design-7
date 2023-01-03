//Time complexity: O(1)
//Space Complexity: O(n)
//Did the code run successfully in LeetCode = yes

package com.madhurima;

import java.util.HashMap;

class LFUCache {
    class Node{
        int key, value, freq;
        Node next, prev;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    private class DLList{
        int size;
        Node head, tail;

        public DLList(){
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        private void addToHead(Node node){
            node.next = head.next;
            head.next.prev = node;
            node.prev = head;
            head.next = node;
            size++;
        }

        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }

        private Node removeLastNode(){
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }

    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    int capacity, min;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node = map.get(key);
        update(node);
        return node.value;
    }

    private void update(Node node){
        DLList oldList = freqMap.get(node.freq);
        oldList.removeNode(node);
        if(min == node.freq && oldList.size == 0){
            min++;
        }
        node.freq++;
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        newList.addToHead(node);
        freqMap.put(node.freq, newList);
    }

    public void put(int key, int value) {
        if(capacity == 0) return;
        if(map.containsKey(key)){
            Node node = map.get(key);
            update(node);
            node.value = value;
            return;
        }

        if(capacity == map.size()){
            DLList oldList= freqMap.get(min);
            Node tailPrev = oldList.removeLastNode();
            map.remove(tailPrev.key);
        }

        Node node = new Node(key, value);
        min = 1;
        map.put(key, node);
        DLList newList = freqMap.getOrDefault(min, new DLList());
        newList.addToHead(node);
        freqMap.put(min, newList);
    }
}
