package Design7;

import java.util.Arrays;
import java.util.LinkedList;

public class DesignSnakeGame {

    class SnakeGame {

        LinkedList<int[]> snake;
        LinkedList<int[]> foodList;
        int[] snakeHead;
        int width;
        int height;

        /** Initialize your data structure here.
         @param width - screen width
         @param height - screen height
         @param food - A list of food positions
         E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
        public SnakeGame(int width, int height, int[][] food) {
            this.width = width;
            this.height = height;
            this.foodList = new LinkedList<>(Arrays.asList(food));
            this.snake = new LinkedList<>();
            snakeHead = new int[]{0,0};
            snake.add(snakeHead);
        }

        /** Moves the snake.
         @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
         @return The game's score after the move. Return -1 if game over.
         Game over when snake crosses the screen boundary or bites its body. */
        public int move(String direction) {
            if(direction.equals("U"))   snakeHead[0]--;
            if(direction.equals("D"))   snakeHead[0]++;
            if(direction.equals("L"))   snakeHead[1]--;
            if(direction.equals("R"))   snakeHead[1]++;

            // border hitting (bound check)
            if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width)  return -1;

            // Snake hitting itself
            for(int i = 1 ; i < snake.size() ; i++) {
                int[] curr = snake.get(i);
                if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) {
                    return -1;
                }
            }

            // Snake eats the food
            // First of all the food list should not be empty. It must contain the food.
            if(!foodList.isEmpty()) {
                int[] foodAppear = foodList.get(0);
                if(snakeHead[0] == foodAppear[0] && snakeHead[1] == foodAppear[1]) {
                    snake.add(foodList.remove());
                    return snake.size() - 1;
                }
            }

            // Normal
            int[] newCell = new int[]{snakeHead[0], snakeHead[1]};
            snake.add(newCell);
            snake.remove();
            return snake.size() - 1;

        }
    }

    /**
     * Your SnakeGame object will be instantiated and called as such:
     * SnakeGame obj = new SnakeGame(width, height, food);
     * int param_1 = obj.move(direction);
     */

    public static void main(String[] args) {

    }

}