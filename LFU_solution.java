package design;

import java.util.HashMap;

public class LFU_solution {
// o(1) tc for get and put
//     o(1) for spc
    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> freqMap;
    
    int min;
    int capacity;
    
    class Node{
        int key;
        int val;
        int cnt;
        Node prev;
        Node next;
        
        public Node(int key, int val){
            this.key=key;
            this.val=val;
            this.cnt=1;
        }
    }

    
    
    class DLList{
        
        Node head;
        Node tail;
        int size;
        
        public DLList(){
            head=new Node(-1,-1);
            tail=new Node(-1,-1);
            head.next=tail;
            tail.prev=head;
        }
        
        
        
    public void addToHead(Node node){
        node.next=head.next;
        node.prev=head;
        head.next=node;
        node.next.prev=node;
        size++;
    }
    
    
    private void removeNode(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
        size--;
    }
    
    private Node removeLast(){
        
        Node tailPrev=tail.prev;
        removeNode(tailPrev);
        // size--;
        return tailPrev;
    }
        
    }
    
    
    

    

    
    public LFUCache(int capacity) {
        map=new HashMap<>();
        freqMap= new HashMap<>();
        this.capacity=capacity;
    }
    
    
    
    public int get(int key) {
        if(map.containsKey(key)){
            Node node =map.get(key);
            update(node);
            return node.val;
        }
        return -1;
    }
    
    
    public void update (Node node){
        
        // remove node from old freq DLL
        
        int cnt=node.cnt;
        DLList oldList= freqMap.get(cnt);
        oldList.removeNode(node);
        
        if(cnt==min && oldList.size==0) min++;
        
        
        node.cnt++;
        
        
        
        DLList newList= freqMap.getOrDefault(node.cnt, new DLList());
        
        newList.addToHead(node);
        
        freqMap.put(node.cnt,newList);
    }
    
    public void put(int key, int value) {
            if(map.containsKey(key)){
                Node node =map.get(key);
                node.val=value;
                update(node);
            }else{
                //capacity full
                if(capacity==0) return ;
                if(capacity==map.size()) {
                    
                
                DLList minLi= freqMap.get(min);
                Node toRemove=minLi.removeLast();
                map.remove(toRemove.key);
            }
        
        Node newNode = new Node(key, value);
        
        min=1;
        DLList minLi=freqMap.getOrDefault(min, new DLList());
        minLi.addToHead(newNode);
        freqMap.put(1,minLi);
        map.put(key,newNode);
    }
}
}
