//Problem 127: LFUCache
//TC:O(1)
//SC:O(C), C stands for capacity 

/*
Steps: Main Data Structures : Doubly-Linked List, and HashMaps
       1) Map for key and the node
       2) Frequncy Map for the Frequency as key and Doubly LinkedList as value, because if two nodes have same minimum frequency then we have to remove Least Recently Used.
*/

import java.util.*;
class LFUCache {

    class Node{
        int key, value, freq;
        Node next,prev;
        Node(int key, int value){
            this.key = key;
            this.value = value;
            freq = 1;
        }
    }
    
    class DLList{
        Node head,tail;
        int size;
        DLList(){
           head = new Node(-1,-1);
           tail = new Node(-1,-1);
           head.next = tail;
           tail.prev = head;
        }
        
        private void addToHead(Node node){
            node.next = head.next;
            node.next.prev = node;
            
            node.prev = head;
            head.next = node;
            size++;//increment the DLL size
        }
        
        private void removeNode(Node node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;//decrement the DLL size
        }
        
        private Node removeFromTail(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
        
    }
    int capacity;
    private Map<Integer,Node> map;
    private Map<Integer,DLList> freqMap;
    private int min;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        
        Node node = map.get(key);
        update(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        
        if(map.containsKey(key)){
           Node node = map.get(key);
           node.value = value;
           update(node);
        }else{
            
            if(map.size()==capacity){
                
                if(capacity==0) return;
                
                //Remove LRU whose freq in minimum from the freqMap along with the map
                DLList minList = freqMap.get(min);
                Node nodeToRemove = minList.removeFromTail();
                map.remove(nodeToRemove.key);
            }
            
            Node newNode = new Node(key,value);
            map.put(key,newNode);
            min = 1;
            
            DLList newList = freqMap.getOrDefault(min,new DLList());
            newList.addToHead(newNode);    
            freqMap.put(min,newList);
        }
        
    }
    //will increment the frquency along with that will move node from older list to the newer list
    private void update(Node node){
        //for updating the node from old frequency list to the new frequency list
        int freq = node.freq;
        //1) Get Old List
        DLList oldList = freqMap.get(freq);
        
        //2)Remove the element from the old list and transfer the element to the new list
        
        oldList.removeNode(node);
        
        //a) while removing just check old list count was equal to the min and if it was then jst check size of old list ==0, then increment the min++; 
        if(min==freq && oldList.size==0) min++;//because current list not contains anything and thus min too be incremented
        node.freq++;
        //3) transfer it to new list with currNode freq+1
        DLList newList = freqMap.getOrDefault(node.freq,new DLList());
        newList.addToHead(node);
        
        freqMap.put(node.freq,newList);
    }
    
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */