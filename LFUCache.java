//Time Complexity O(1)
//Space Complexity O(N)
//Leetcode tested


import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    Map<Integer,LFUListNode> keyMap = new HashMap<>();
    Map<Integer,DoublyList> freqMap = new HashMap<>();

    int curCapacity=0;
    int maxCapacity;

    public LFUCache(int capacity) {
        this.maxCapacity = capacity;
    }

    public LFUListNode getNode(int key){
        if(!keyMap.containsKey(key)) return null;
        LFUListNode curNode = keyMap.get(key);
        DoublyList list = freqMap.get(curNode.freq);
        list.deleteNode(key);
        curNode.freq++;

        if(!freqMap.containsKey(curNode.freq)){
            freqMap.put(curNode.freq,new DoublyList());
        }
        freqMap.get(curNode.freq).addNode(curNode);
        return curNode;
    }

    public int get(int key) {
        if(!keyMap.containsKey(key)) return -1;
        LFUListNode curNode = getNode(key);
        return curNode.val;

    }

    public void put(int key, int value) {
        if(maxCapacity == 0) return;
        if(keyMap.containsKey(key)){
            LFUListNode curNode = getNode(key);
            curNode.val = value;
        }else{
            if(curCapacity == maxCapacity){
                int lowestFreq = Integer.MAX_VALUE;
                for (Integer freq:freqMap.keySet()) {
                    if(freqMap.get(freq).map.isEmpty())
                        continue;
                    lowestFreq = Math.min(lowestFreq, freq);
                }
                DoublyList list = freqMap.get(lowestFreq);
                LFUListNode curNode = list.deleteHead();
                keyMap.remove(curNode);
                curCapacity--;
            }
            int curFreq = 1;
            LFUListNode curNode = new LFUListNode(value,key);
            keyMap.put(key,curNode);
            if(!freqMap.containsKey(curFreq)){
                freqMap.put(curFreq,new DoublyList());
            }
            freqMap.get(curFreq).addNode(curNode);
            curCapacity++;

        }
    }
}

class LFUListNode{
    LFUListNode prev,next;
    int val,key,freq;

    LFUListNode(){

    }
    LFUListNode(int val,int key){
        this.val = val;
        this.key = key;
        this.freq = 1;
    }
}

class DoublyList{
    Map<Integer,LFUListNode> map = new HashMap<>();
    LFUListNode head,tail;

    public DoublyList(){
        head = new LFUListNode();
        tail = new LFUListNode();
        tail.prev = head;
        head.next = tail;
    }

    public void addNode(LFUListNode curNode){
        LFUListNode tailPrev = tail.prev;
        tailPrev.next = curNode;
        curNode.prev = tailPrev;
        tail.prev = curNode;
        curNode.next = tail;
        map.put(curNode.key,curNode);
    }

    public LFUListNode deleteNode(int key){
        if(!map.containsKey(key)) return null;
        LFUListNode curNode = map.get(key);
        LFUListNode prevNode = curNode.prev;
        LFUListNode nextNode = curNode.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        map.remove(key);
        return curNode;
    }

    public LFUListNode deleteHead(){
        if(head.next == tail) return null;
        LFUListNode headNext = head.next;
        return deleteNode(headNext.key);
    }
}
