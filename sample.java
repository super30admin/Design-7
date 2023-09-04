//Problem 1 LFU cache
// Time Complexity :O(1)
// Space Complexity :O(capacity)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
//O(1) O(capacity)
//Create a node class and doubly linked list(for O(1) removal and deletion.)
//Create a hashmap of frequencies. and store a doubly linked list of all nodes with same freq. 
//when size is full, maintain a minfreq element and remove head element(least frequently used) from doubly linked list of minFreq from hashmap.
class LFUCache {
    class Node{
        int key, value, freq;
        Node next,prev;

        public Node(int k, int v){
            this.key=k;
            this.value=v;
            this.freq=1;
        }
    }

    class ddll{
        Node head, tail;
        int size;

        public ddll(){
            this.head=new Node(-1,-1);
            this.tail=new Node(-1,-1);
            this.head.next=this.tail;
            this.tail.prev=this.head;
        }

        private void addToHead(Node node){
            node.next=head.next;
            node.prev=head;
            head.next=node;
            node.next.prev=node;
            this.size++;
        }

        private void removeNode(Node node){
            node.next.prev=node.prev;
            node.prev.next=node.next;
            this.size--;
        }

        private Node removeLast(){
            Node rem= this.tail.prev;
            removeNode(rem);
            return rem;
        }
    }

    private HashMap<Integer, Node> map;
    private HashMap<Integer, ddll> ddllmap;
    private int capacity;
    private int minfreq;
    public LFUCache(int capacity) {
        this.capacity=capacity;
        this.map=new HashMap<Integer, Node>();
        this.ddllmap=new HashMap<Integer, ddll>();

    }
    
    private void update(Node node){
        int oldcnt= node.freq;
        ddll oldlist= ddllmap.get(oldcnt);
        oldlist.removeNode(node);

        if(oldcnt==minfreq && oldlist.size==0)
            minfreq++;
        node.freq=oldcnt+1;

        ddll newList= ddllmap.getOrDefault(node.freq, new ddll());
        newList.addToHead(node);
        ddllmap.put(node.freq, newList);

    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }

        Node node =map.get(key);
        update(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node= map.get(key);
            node.value=value;
            update(node);
        }else{
            if(this.capacity==map.size()){
                ddll minList= ddllmap.get(minfreq);
                Node rem=minList.removeLast();

                map.remove(rem.key);
            }
            Node newnode= new Node(key, value);
            minfreq=1;
            ddll minList= ddllmap.getOrDefault(minfreq, new ddll());
            minList.addToHead(newnode);
            ddllmap.put(1, minList);
            map.put(key,newnode);
        }

    }
}

//Problem 2 Snake Game
// Time Complexity :O(1)
// Space Complexity :O(height*width)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
//O(1) //O(height*width)
//maintain visited matrix for snake size and a linkedlist to represent a snake. add new cell at end, and remove head cell.
// when snake hits itself or boundary return -1. if it eats food on the directed cell, return size of linkedlist.
class SnakeGame {
    LinkedList<int[]> snake;
    boolean[][] visited;
    int[] head;
    int width;
    int height;
    int[][] foodlist;
    int i;
    public SnakeGame(int width, int height, int[][] food) {
        this.snake= new LinkedList<>();
        this.visited=new boolean[height][width];
        this.width=width;
        this.height=height;
        this.foodlist=food;
        this.head=new int[]{0,0};
        this.snake.addLast(head);
    }
    
    public int move(String direction) {
        if(direction.equals("U")){
            head[0]--;
        }
        else if(direction.equals("D")){
            head[0]++;
        }
        else if(direction.equals("R")){
            head[1]++;
        }
        else if(direction.equals("L")){
            head[1]--;
        }
        //hits boundary
        if(head[0]<0 || head[0]==height || head[1]<0 || head[1]==width) return -1;

        //hits itself
        if(visited[head[0]][head[1]])return -1;

        //eats food
        if(i<foodlist.length){
            int[] cur=foodlist[i];
            if(cur[0]==head[0] && cur[1]==head[1]){
                this.snake.addLast(new int[]{head[0],head[1]});
                this.visited[head[0]][head[1]]=true;
                i++;

                return this.snake.size()-1;
            }
        }

        //normal move
        this.snake.addLast(new int[]{head[0],head[1]});
        this.visited[head[0]][head[1]]=true;
        this.snake.removeFirst();
        int[] newTail= this.snake.getFirst();
        this.visited[newTail[0]][newTail[1]]=false;
        return this.snake.size()-1;
    }
}

//Problem 3 H index 2
// Time Complexity :O(max-min+1)
// Space Complexity :O(max-min+1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
// do a bucket sort on all elements. then check for appropriate h index by checking all conditions.
class Solution {
    //O(2n) //O(n)
    public int hIndex(int[] citations) {
        int n=citations.length;

        int[] bucket= new int[n+1];

        for(int el: citations){
            if(el>=n)
                bucket[n]++;
            else
                bucket[el]++;
        }

        int sum=0;

        for(int i=n;i>=0;i--){
            sum+=bucket[i];

            if(i<=sum)
                return i;
        }

        return 0;
    }
}