# Design-7

## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

//Time Complexity = O(1) get, put 
class LFUCache {
    class Node{
        Node prev;
        Node next;
        int cnt;
        int key; int value;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
            cnt = 1; 
        }
    }
    class DLL{
        Node head;
        Node tail;
        int size; 
        public DLL(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1); 
            head.next = tail;
            tail.prev = head;
        }
        
        public void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        public void addNode(Node node){
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node; 
            size++;
        }
        
        public Node removeLast(){
            Node tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev; 
        }
    }
    
    HashMap<Integer, Node> countMap; 
    HashMap<Integer, DLL> freqMap;
    int min; 
    int capacity; 
    public LFUCache(int capacity) {
        this.capacity = capacity;
        countMap = new HashMap<>();
        freqMap = new HashMap<>(); 
    }
    
    public int get(int key) {
        if(!countMap.containsKey(key)) return -1;
        Node node = countMap.get(key); 
        update(node); 
        return node.value;
    }
    
    public void put(int key, int value) { 
        if(countMap.containsKey(key)){
            Node node = countMap.get(key); 
            node.value = value; 
            update(node); 
        }else{
            if(capacity == 0) return;
            if(capacity == countMap.size()){
                DLL minf = freqMap.get(min); 
                Node nodeToRemove = minf.removeLast();
                countMap.remove(nodeToRemove.key); 
            }
            Node node = new Node(key,value);
            min = 1;
            DLL list = freqMap.getOrDefault(1, new DLL());
            list.addNode(node);
            freqMap.put(min, list);
            countMap.put(key, node); 
            
        }
    }
    
    private void update(Node node){
        int count = node.cnt;
        DLL oldList = freqMap.get(count);
        oldList.removeNode(node);
        if(node.cnt == min && oldList.size == 0) min++;
        count++;
        node.cnt = count;
        DLL newList = freqMap.getOrDefault(node.cnt, new DLL());
        newList.addNode(node);
        freqMap.put(count, newList);
        countMap.put(node.key, node); 
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

class SnakeGame {
    
    LinkedList<int []> snake;
    LinkedList<int []> foodPath; 
    int size;
    int[] snakeHead;
    int width;
    int height;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodPath = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
        this.width = width; 
        this.height = height; 
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;
        if(direction.equals("L")) snakeHead[1]--;
        if(direction.equals("R")) snakeHead[1]++;
        if(direction.equals("D")) snakeHead[0]++;
        
        //boundary check
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0){
            return -1; 
        }
        
        //snake hitting itself
        for(int i = 1; i < snake.size(); i++){
            int[] curr = snake.get(i); 
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1]){
                return -1;
            }
        }
        
        //snake eating food
        if(!foodPath.isEmpty()){
            int[] food = foodPath.get(0); 
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]){
                snake.add(foodPath.remove());
                return snake.size() - 1;
            }
        }
        
        //normal move
        int[] cell = new int[]{snakeHead[0], snakeHead[1]}; 
        snake.add(cell);
        snake.remove();
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
