package design7;

import java.util.*;

public class DesignSnakeGame {
	//Time Complexity : O(n), if food, in worst case
  	//Space Complexity : O(n)
  	//Did this code successfully run on Leetcode : Yes
  	//Any problem you faced while coding this : No
	LinkedList<int[]> snake;
    LinkedList<int[]> snakeFood;
    int width;
    int height;
    public DesignSnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        snakeFood = new LinkedList<>(Arrays.asList(food));
        snake.addLast(new int[] {0, 0});
        this.width = width;
        this.height = height;
    }
    
    public int move(String direction) {
        int[] snakeHead = new int[] {snake.getLast()[0], snake.getLast()[1]};
        if(direction.equals("R"))
            snakeHead[1]++;
        else if(direction.equals("D"))
            snakeHead[0]++;
        else if(direction.equals("L"))
            snakeHead[1]--;
        else
            snakeHead[0]--;
        
        // boundary check
        if(snakeHead[0] < 0 || snakeHead[1] < 0 || snakeHead[0] >= height || snakeHead[1] >= width)
            return -1;
        
        // check if snake eats itself
        for(int i=1; i<snake.size(); i++) {
            int[] curr = snake.get(i);
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1])
                return -1;
        }
            
        // if food
        if(snakeFood.size() > 0) {
            int[] currFood = snakeFood.getFirst();
            if(snakeHead[0] == currFood[0] && snakeHead[1] == currFood[1]) {
                snake.addLast(snakeHead);
                snakeFood.removeFirst();
                return snake.size() - 1;
            }
        }
        
        // normal movement
        snake.addLast(snakeHead);
        snake.removeFirst();
        return snake.size() - 1;
    }
}
