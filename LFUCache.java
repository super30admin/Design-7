import java.util.HashMap;

//Space Complexity=O(n)
public class LFUCache {

    public class Node{
        Node prev,next;
        int key,val;
        int count;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
            this.count=1;
        }
    }

    class DLL{
        Node head,tail;
        int size;
        public DLL(){
            this.head=new Node(-1,-1);
            this.tail=new Node(-1,-1);
            head.next=tail;
            tail.prev=head;
        }

        //Time Complexity=O(1)
        public void remove(Node node){
            node.prev.next=node.next;
            node.next.prev=node.prev;
            this.size--;
        }

        //Time Complexity=O(1)
        public void addToHead(Node node){
            node.next=head.next;
            node.prev=head;
            head.next.prev=node;
            head.next=node;
            this.size++;
        }

        public Node removeLast(){
            Node node=tail.prev;
            remove(node);

            return node;
        }
    }
    HashMap<Integer,Node> map;
    HashMap<Integer,DLL> freqmap;
    int capacity;int min;

    public LFUCache(int capacity) {
        map=new HashMap<>();
        freqmap=new HashMap<>();
        this.capacity=capacity;

    }

    //Time Complexity=O(1)
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node=map.get(key);
        update(node);
        return node.val;
    }

    //Time Complexity=O(1)
    public void put(int key, int value) {
        if(this.capacity==0) return;
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.val=value;
            update(node);
        }else{

            if(map.size()==capacity){
                DLL minFreq=freqmap.get(min);
                Node toRemove=minFreq.removeLast();
                map.remove(toRemove.key);
            }

            Node newNode=new Node(key,value);
            min=1;
            DLL li= freqmap.getOrDefault(1,new DLL());
            li.addToHead(newNode);
            freqmap.put(1,li);
            map.put(key,newNode);
        }
    }

    //Time Complexity=O(1)
    public void update(Node node){
        int oldCount=node.count;
        DLL oldList=freqmap.get(oldCount);
        oldList.remove(node);
        if(oldCount==min && oldList.size==0) min++;
        node.count++;
        DLL newList=freqmap.getOrDefault(node.count,new DLL());
        newList.addToHead(node);
        freqmap.put(node.count,newList);
    }
}
