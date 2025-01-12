import java.util.HashMap;

// Time Complexity: O(1)
// Space Complexity: O(n)

public class problem1 {
    class LFUCache {

        HashMap<Integer, Node> map;
        HashMap<Integer, DLList> freqMap;
        int capacity;
        int minFreq;


        class Node{
            int key;
            int value;
            int freq;
            Node next;
            Node prev;

            public Node(int key, int value)
            {
                this.key= key;
                this.value= value;
                this.freq= 1;
            }
        }
        class DLList{
            Node head;
            Node tail;
            int size;

            public DLList(){
                this.head= new Node(-1,-1);
                this.tail= new Node(-1,-1);
                head.next= tail;
                tail.prev= head;
            }

            public void addNodeToHead(Node node){
                node.next= head.next;
                node.prev= head;
                head.next= node;
                node.next.prev= node;
                this.size++;
            }
            public void removeNode(Node node)
            {
                node.prev.next= node.next;
                node.next.prev= node.prev;
                node.next= null;
                node.prev=null;
                this.size--;
            }
        }
        public LFUCache(int capacity) {
            this.capacity= capacity;
            this.map= new HashMap<>();
            this.freqMap= new HashMap<>();
        }
        private void update(Node node)
        {
            int oldFreq= node.freq;
            DLList oldfreqlist= freqMap.get(oldFreq);
            oldfreqlist.removeNode(node);
            if(oldFreq==minFreq &&oldfreqlist.size==0)
            {
                minFreq++;
            }
            int newFreq= oldFreq+1;
            node.freq=newFreq;

            DLList newFreqList= freqMap.getOrDefault(newFreq, new DLList());
            newFreqList.addNodeToHead(node);
            freqMap.put(newFreq,newFreqList );

        }

        public int get(int key) {
            if(!map.containsKey(key))
            {
                return -1;
            }
            Node node  =map.get(key);
            update(node);
            return node.value;
        }

        public void put(int key, int value) {
            if(map.containsKey(key))
            {
                Node node= map.get(key);
                node.value= value;
                update(node);
            }
            else{
                if(map.size()==capacity)
                {
                    DLList minFreqList= freqMap.get(minFreq);
                    Node toRemove= minFreqList.tail.prev;
                    minFreqList.removeNode(toRemove);
                    map.remove(toRemove.key);
                }
                Node newNode= new Node(key,value);
                minFreq=1;
                DLList minFreqList= freqMap.getOrDefault(minFreq,new DLList());
                minFreqList.addNodeToHead(newNode);
                freqMap.put(minFreq,minFreqList);
                map.put(key, newNode);

            }

        }
    }

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
}
