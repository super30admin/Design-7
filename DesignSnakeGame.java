package Dec17;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class DesignSnakeGame {

    /*
    Time complexity: O(n)
    Because iterating over whole snake if it doesn't hit itself.
    
    */
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    
    List<int []> snake;
    List<int []> foodList;
    int width; int height;
    int[] snakeHead;
    
    public DesignSnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        this.width = width;
        this.height = height;
        snakeHead = new int[]{0, 0};
        snake.add(snakeHead);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if (direction.equals("U")) {
            snakeHead[0]--;
        }
         if (direction.equals("D")) {
            snakeHead[0]++;
        }
         if (direction.equals("L")) {
            snakeHead[1]--;
        }
         if (direction.equals("R")) {
            snakeHead[1]++;
        }
        // check if snake hits boundary
        if (snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) {
            return -1;
        }
        // check if snake hits itself
        for (int i = 1; i < snake.size(); i++) {
            int [] curr = snake.get(i);
            if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }
        // food
        if (!foodList.isEmpty()) {
            int[] appeared = foodList.get(0);
            if (appeared[0] == snakeHead[0] && appeared[1] == snakeHead[1]) {
                snake.add(foodList.remove(0));
                return snake.size() - 1;
            }
        }
        snake.remove(0); // prev tail removed
        snake.add(new int[] {snakeHead[0], snakeHead[1]}); // new head is added to snake list
        return snake.size()-1;
    }
    
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
