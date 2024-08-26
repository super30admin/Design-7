# Design-7

## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

Explain approach in 3 lines: In this approach we are using the combination of a hashmap and doubly linked lists to store and manage cache entries where each liked list will correspond to a frequency count. When inserting a key the cache will update the frequency of the key and it will rearrange it in the corresponding frequency list. If the cache reaches its capacity, the lest frequently used entry will be evicted.

Time Complexity: Get operation will have O(1) time complexity and put operation will have O(1) time complexity.

Space Complexity: O(n) where n is the capacity of cache to store the nodes in the hashmap and doubly linked list.

Approached Code: 

import org.w3c.dom.Node;

class LFUCache {

    class Node {
        int key;
        int val;
        int cnt;

        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.cnt = 1;
        } 
    }

    class DLList {
        Node head;
        Node tail;
        int size;

        public DLList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            this.size++;
        }

        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }

        public Node removeTail() {
            Node tailPrev = this.tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer, Node> map;
    HashMap<Integer, DLList> fMap;
    int capacity;
    int min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.fMap = new HashMap<>();
    }

    public void updateNode(Node node) {
        int oldCnt = node.cnt;

        DLList oldList = fMap.get(oldCnt);
        oldList.removeNode(node);

        if(oldCnt == min && oldList.size == 0) {
            min++;
        }

        node.cnt++;
        DLList newList = fMap.getOrDefault(node.cnt, new DLList());
        newList.addToHead(node);
        fMap.put(node.cnt, newList);
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

            if(map.size() == capacity) {
                DLList minList = fMap.get(min);
                Node toRemove = minList.removeTail();
                map.remove(toRemove.key);
            }

            Node newNode = new Node(key, value);
            min = 1;
            DLList newList = fMap.getOrDefault(min, new DLList());
            newList.addToHead(newNode);
            fMap.put(1, newList);
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



## Problem2 H-Index (https://leetcode.com/problems/h-index/)

Explain approach in 3 lines: First we will count the number of papers with each citation count using auxillary array. Then we will traverse through the auxillary array in the reverse to find the highest citation count i where there are at least i papers with i or more citations. Then we will return this i as H-index. We can sort the array and then perform the similary operations but sorting takes nlong time.

Time Complexity: O(n) as we are iterating over rhe citations array and then auxillary array where n is length of the citations array.

Space Complexity: O(n) as we are using the auxillary array to store number of citations greater than the index number.

Approached Code: 

class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;

        // Arrays.sort(citations);

        // for(int i = 0; i < n; i++) {
        //     int temp = n - i;

        //     if(temp <= citations[i]) {
        //         return temp;
        //     }
        // }


        int[] arr = new int[n+1];

        for(int i = 0; i < n; i++) {
            if(citations[i] >= n) {
                arr[n]++;
            } else {
                arr[citations[i]]++;
            }
        }

        int temp = 0;

        for(int i = n; i >= 0; i--) {
            temp = temp + arr[i];

            if(temp >= i) {
                return i;
            }
        }

        return 0;
    }
}



## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

Explain approach in 3 lines: In this approach we are using linked list to track the snake's position and a boolean matrix to track the whole length of snake to check if snake is hitting itself or not. On each move, we are updating snake's head based on the direction first and if the new position collides with boundries or the snake's body we are returning -1 which means game ends. If the snake reaches the food it will grow otherwise it will move by removing it's tail and side by side we are updating the visited matrix as well.

Time Complexity: Move operation has a time complexity of O(1).

Space Complexity: O(W*H) where H is height of matrix and W is width of a matrix as we are storing visited boolean matrix and also snake's position.

Approached Code: 

class SnakeGame {

    LinkedList<int []> snake;
    int[] snakeHead;
    boolean[][] visited;
    int i;
    int H;
    int W;
    int[][] foodList;

    public SnakeGame(int width, int height, int[][] food) {
        this.H = height;
        this.W = width;
        this.foodList = food;
        this.snake = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.snakeHead = new int[] {0, 0};
        this.snake.addLast(snakeHead);
    }
    
    public int move(String direction) {
        if(direction.equals("R")) {
            snakeHead[1]++;
        } else if(direction.equals("U")) {
            snakeHead[0]--;
        } else if(direction.equals("L")) {
            snakeHead[1]--;
        } else if(direction.equals("D")) {
            snakeHead[0]++;
        }

        if(snakeHead[0] < 0 || snakeHead[1] < 0 || snakeHead[0] == H || snakeHead[1] == W) {
            return -1;
        }

        if(visited[snakeHead[0]][snakeHead[1]]) {
            return -1;
        }

        if(i < foodList.length) {
            int[] foodLoc = foodList[i];

            if(snakeHead[0] == foodLoc[0] && snakeHead[1] == foodLoc[1]) {
                i++;

                int[] head = new int[] {snakeHead[0], snakeHead[1]};
                snake.addLast(head);
                visited[head[0]][head[1]] = true;

                return snake.size() - 1;
            }
        }

        int[] head = new int[] {snakeHead[0], snakeHead[1]};
        snake.addLast(head);
        visited[head[0]][head[1]] = true;
                
        snake.removeFirst();
        int[] tail = snake.peek();
        visited[tail[0]][tail[1]] = false;

        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */


