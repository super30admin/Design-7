// Time Complexity : O(1)
// Space Complexity : O(w*h + n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
import java.util.*;
class SnakeGame {
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int width;
    int height;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[] {0, 0};
        snake = new LinkedList<>();
        snake.add(snakeHead);
        foodList = new LinkedList<>(Arrays.asList(food));
    }
    
    public int move(String direction) {
        if(direction.equals("U"))
            snakeHead[0] = snakeHead[0] - 1;
        else if(direction.equals("D"))
            snakeHead[0] = snakeHead[0] + 1;
        else if(direction.equals("L"))
            snakeHead[1] = snakeHead[1] - 1;
        else if(direction.equals("R"))
            snakeHead[1] = snakeHead[1] + 1;
        
        // check if bounds touch
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width)
            return -1;
        // check if the snake touches itself
        for(int i = 1; i < snake.size(); i++) {
            int[] curr = snake.get(i);
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1])
                return -1;    
        }
        
        // eat food
        if(foodList.size() > 0) {
            int[] food = foodList.peek();
            if(food[0] == snakeHead[0] && food[1] == snakeHead[1]) {
                foodList.remove();
                snake.add(new int[] {snakeHead[0], snakeHead[1]});
                return snake.size() - 1;
            }
        }
        
        // normal
        // remove the tail of the snake
        snake.remove();
        // add the new Head of snake
        snake.add(new int[] {snakeHead[0], snakeHead[1]});
        return snake.size() - 1;
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */