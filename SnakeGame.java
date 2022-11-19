import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {

    int width, height;
    int [] snakeHead;
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItem;

    public SnakeGame (int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[] {0,0};
        snakeBody = new LinkedList<>();
        foodItem = new LinkedList<>(Arrays.asList(food));
        snakeBody.addLast(snakeHead);
    }

    public int move (String direction) {

        if(direction.equals('U')) {
            // snake head moves up
            snakeHead[0]--;

        } else if (direction.equals('D')) {
            snakeHead[0]++;

        } else if (direction.equals('L')) {
            snakeHead[1]--;

        } else if (direction.equals('R')) {
            snakeHead[1]++;

        }

        // check if the snake is out of bounds
        if (snakeHead[0] <0 || snakeHead[0] == width || snakeHead[1] <0 || snakeHead[1] == height) {
            return -1;
        }

        // check if the snake is touching itself
        for (int i=0; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }

        // check if food appears in front of the snake
        if (!foodItem.isEmpty()) {
            int[] foodLocation = foodItem.get(0);
            if (snakeHead[0] == foodLocation[0] && snakeHead[1] == foodLocation[1]) {
                foodItem.remove();
                snakeBody.addLast(new int[] { snakeHead[0], snakeHead[1]});
                return snakeBody.size() - 1;
            }
        }

        //normal case
        snakeBody.removeLast();
        snakeBody.addLast(new int[] { snakeHead[0], snakeHead[1]});
        return snakeBody.size() - 1;
    }
}
