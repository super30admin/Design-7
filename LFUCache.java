import java.util.HashMap;
import java.util.Map;

//TC: O(1) all
//SC: O(n)
public class LFUCache {

    class Node{
        int key;
        int val;
        int freq;
        Node next;
        Node prev;
        public Node(int key, int val){
            this.key=key;
            this.val=val;
            this.freq=1;
        }
    }

    class DLList{
        Node head;
        Node tail;
        int size;
        public DLList(){
            this.head=new Node(-1,-1);
            this.tail=new Node(-1,-1);
            this.head.next=this.tail;
            this.tail.prev=this.head;
        }

        public void addTohead(Node node){
            node.prev=this.head;
            node.next=this.head.next;
            this.head.next=node;
            node.next.prev=node;
            this.size++;
        }
        public void removeNode(Node node){
            node.next.prev=node.prev;
            node.prev.next=node.next;
            this.size--;
        }

        //to remove least recent from min frequency list
        //we need this node 
        public Node removeTail(){
            Node tailprev=this.tail.prev;
            removeNode(tailprev);
            return tailprev;
        }
    }

    private Map<Integer,Node> map;
    private Map<Integer,DLList> fmap;
    int cap;
    int min;


    public LFUCache(int capacity) {
        this.cap=capacity;
        this.map=new HashMap<>();
        this.fmap=new HashMap<>();
    }

    private void update(Node node){
        //remove from old freq list and add to new freq list
        int oldcnt=node.freq;
        DLList oldlist=fmap.get(oldcnt);
        oldlist.removeNode(node);
        if(oldcnt==min && oldlist.size==0){
            min++;
        }
        oldcnt++;
        int newcnt=oldcnt;
        node.freq=newcnt;
        //add to new list
        DLList newlist=fmap.getOrDefault(newcnt,new DLList());
        newlist.addTohead(node);
        fmap.put(newcnt,newlist);
    }
    
    public int get(int key) {
        if(map.containsKey(key)){
            Node node=map.get(key);
            //update this node
            update(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(cap==0){
            return;
        }
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.val=value;
            update(node);
        }else{
            //fresh
            if(this.cap==map.size()){
                //get min freq list
                DLList minlist=fmap.get(min);
                //remove tail of this minlist
                Node toremove=minlist.removeTail();
                int k=toremove.key;
                map.remove(k);
            }
            Node newnode=new Node(key,value);
            this.min=1;
            DLList li=fmap.getOrDefault(1,new DLList());
            li.addTohead(newnode);
            fmap.put(1,li);
            map.put(key,newnode);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */