import java.util.Arrays;
import java.util.LinkedList;

//Time Complexity: O(n), traversing the body of the snake.
//Space Complexity: O(n), LL containing the snake body.

class SnakeGame {
    LinkedList<int[]> snakeBody;
    int[] snakeHead;
    LinkedList<int[]> foodItems;
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[] { 0, 0 };
        snakeBody = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeBody.add(snakeHead);
    }

    public int move(String direction) {
        if (direction.equals("U")) {
            snakeHead[0]--;
        } else if (direction.equals("D")) {
            snakeHead[0]++;
        } else if (direction.equals("L")) {
            snakeHead[1]--;
        } else if (direction.equals("R")) {
            snakeHead[1]++;
        }
        // check if it is touching the boundaries
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }
        // check if the snake touches itself
        for (int i = 1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }
        // if food is present, the snake should eat it and return the score
        if (!foodItems.isEmpty()) {
            int[] appearedFood = foodItems.get(0);
            if (snakeHead[0] == appearedFood[0] && snakeHead[1] == appearedFood[1]) {
                snakeBody.add(new int[] { snakeHead[0], snakeHead[1] });
                foodItems.remove();
                return snakeBody.size() - 1;
            }

        }
        // remove from snakebody as the snake moves forward.
        snakeBody.remove();
        // add the snake head to the snake body
        snakeBody.add(new int[] { snakeHead[0], snakeHead[1] });
        // return score
        return snakeBody.size() - 1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
