# Design-7

## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

//Time Complexity = O(1)
//Space Complexity = O(N)

class LFUCache {
class ListNode {
int value;
int key;
int count;
ListNode prev;
ListNode next;

        public ListNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
    class DLList {
        ListNode head;
        ListNode tail;
        int size;

        public DLList() {
            head = new ListNode(-1,-1);
            tail = new ListNode(-1,-1);
            head.next = tail;
            tail.prev = head;
        }
        public void addToHead(ListNode node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
            size++;
        }
        public void removeNode(ListNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        public ListNode removeLastNode() {
            ListNode tailPrev = tail.prev;
            removeNode(tailPrev);
            return tailPrev;
        }
    }

    HashMap<Integer,ListNode> keyMap;
    int capacity;
    HashMap<Integer,DLList> freqMap;
    int minFreq;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        keyMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!keyMap.containsKey(key)) {
            return -1;
        }
        ListNode node = keyMap.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(capacity == 0) {
            return;
        }
        if(keyMap.containsKey(key)) {
            ListNode node = keyMap.get(key);
            updateFreq(node);
            node.value = value;
        } else {
            if(capacity == keyMap.size()) {
                DLList minList = freqMap.get(minFreq);
                ListNode toRemove = minList.removeLastNode();
                keyMap.remove(toRemove.key);
            }
            ListNode newNode = new ListNode(key,value);
            minFreq = 1;
            keyMap.put(key,newNode);
            DLList minList = freqMap.getOrDefault(minFreq, new DLList());
            minList.addToHead(newNode);
            freqMap.put(minFreq,minList);
        }
    }

    private void updateFreq(ListNode node) {
        DLList oldList = freqMap.getOrDefault(node.count, new DLList());
        oldList.removeNode(node);

       if(oldList.size == 0) {
           if(minFreq == node.count) {
               minFreq++;
           }
           freqMap.remove(node.count);
       }
        node.count++;

        DLList newList = freqMap.getOrDefault(node.count, new DLList());
        newList.addToHead(node);
        freqMap.put(node.count,newList);
        //keyMap.put(node.key,node);
    }

}

/\*\*

- Your LFUCache object will be instantiated and called as such:
- LFUCache obj = new LFUCache(capacity);
- int param_1 = obj.get(key);
- obj.put(key,value);
  \*/

## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

//Time Complexity = O(N)
//Space Complexity = O(N)

class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int width;
    int height;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
    }

    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        }
        if(direction.equals("D")) {
            snakeHead[0]++;
        }
        if(direction.equals("L")) {
            snakeHead[1]--;
        }
        if(direction.equals("R")) {
            snakeHead[1]++;
        }

        //Hits wall
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) {
            return -1;
        }

        //Hits itself
        for(int i = 1; i < snake.size(); i++) {
            int[] current = snake.get(i);
            if(current[0] == snakeHead[0] && current[1] == snakeHead[1]) {
                return -1;
            }
        }

        //Eats Food
        if(!foodList.isEmpty()) {
            int[] appearedFood = foodList.get(0);
            if(snakeHead[0] == appearedFood[0] && snakeHead[1] == appearedFood[1]) {
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }

        //Normal Move
        int[] newCell = new int[]{snakeHead[0],snakeHead[1]};
        snake.add(newCell);
        snake.remove();
        return snake.size() - 1;
    }

}

/\*\*

- Your SnakeGame object will be instantiated and called as such:
- SnakeGame obj = new SnakeGame(width, height, food);
- int param_1 = obj.move(direction);
  \*/
