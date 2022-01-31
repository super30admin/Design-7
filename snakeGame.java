import java.io.*;
import java.util.*;

// Time Complexity :O(width+height) or O(n) where n is the size of the snake
// Space Complexity :O(width+height) or O(n) where n is the size of the snake
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
class SnakeGame {
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    int height, width;

    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
        this.height = height;
        this.width = width;
    }

    public int move(String direction) {
        // Move snakeHead depending on the input direction
        if (direction.equals("U")) {
            snakeHead[0]--;
        }
        else if (direction.equals("D")) {
            snakeHead[0]++;
        }
        else if (direction.equals("L")) {
            snakeHead[1]--;
        }
        else if (direction.equals("R")) {
            snakeHead[1]++;
        }

        // Check if the snake hit the border of the grid
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }

        // Check if the snake touched itself
        for (int i = 1; i < snake.size(); i++) {
            int[] curr = snake.get(i);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) {
                return -1;
            }
        }

        // Check for food items
        if (foodList.size() > 0) {
            int[] food = foodList.peek();
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]) {
                foodList.remove();
                snake.add(new int[]{snakeHead[0], snakeHead[1]});

                // return size of the snake - 1 because we started with size 1 with a value 0
                return snake.size()-1;
            }
        }

        // if no food present
        snake.add(new int[]{snakeHead[0], snakeHead[1]});       // The new snakeHead is added to the snake list
        snake.remove();                         // The first element is removed from the list, since the snake is moving

        // return size of the snake - 1 because we started with size 1 with a value 0
        return snake.size()-1;

    }
}