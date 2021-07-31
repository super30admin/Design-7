// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

/**
 * In this design problem, we maintain a map with the locations of the snake body.
 * I have maintained a Deque as the representation for the snake.
 * 
 * For each move, we check if the snake either breaches the boundaries or bites itself --> return -1
 * if the new move actually has consumed a food, we don't alter the tail, only add the new location to the head.
 * Else, we remove the tail from the deque (snake).
 * 
 * Finally return the score (snake.length - 1) (deque.length - 1)
 */

class SnakeGame {
    
    int width;
    int height;
    int[][] food;
    Deque<Pair<Integer, Integer>> snake;
    Map<Pair<Integer, Integer>, Boolean> snakeBodyMap;
    int foodIndex;

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        
        foodIndex = 0;
        
        snake = new LinkedList<Pair<Integer, Integer>>();
        snakeBodyMap = new HashMap<Pair<Integer, Integer>, Boolean>();
        
        foodIndex = 0;
        
        snake.offerLast(new Pair<Integer, Integer>(0,0));
        snakeBodyMap.put(new Pair<Integer, Integer>(0,0), true);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        // Get the snake Head
        Pair<Integer, Integer> snakeHead = this.snake.peekFirst();
        
        int headRow = snakeHead.getKey();
        int headCol = snakeHead.getValue();
        
        if (direction.equals("U")) {
            headRow--;
        } else if (direction.equals("D")) {
            headRow++;
        } else if (direction.equals("L")) {
            headCol--;
        } else if (direction.equals("R")) {
            headCol++;
        }
        
        Pair<Integer, Integer> newHead = new Pair<Integer, Integer>(headRow, headCol);
        
        // get Tail location
        Pair<Integer, Integer> tailLocation = this.snake.peekLast();
        
        // check if the snake will be crossing boundaries
        
        // 1. check if row boundary is breached
        boolean crossesRow = headRow < 0 || headRow >= this.height;
        boolean crossesCol = headCol < 0 || headCol >= this.width;
        
        // check if the snake bites itself
        // check in the snakeBody Map
        boolean bitesItself = this.snakeBodyMap.containsKey(newHead) && 
                    !(newHead.getKey() == tailLocation.getKey() && newHead.getValue() == tailLocation.getValue());
        
        if (crossesRow || crossesCol || bitesItself) {
            return -1;
        }
        
        // if there is a food present in the new Head Location, eat it.
        if((this.foodIndex < this.food.length) && (this.food[this.foodIndex][0] == headRow) && (this.food[this.foodIndex][1] == headCol)) {
            this.foodIndex++;
        } else {
            this.snake.pollLast();
            this.snakeBodyMap.remove(tailLocation);
        }
        
        // add head always
        this.snake.addFirst(newHead);
        
        this.snakeBodyMap.put(newHead, true);
        
        // return score
        return this.snake.size() - 1;
    }
}