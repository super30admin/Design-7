import java.util.*;

public class DesignSnakeGame {
    //  Tail of the LL is head of the snake

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    int width;
    int height;

    public DesignSnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.snake = new LinkedList<>();
        snakeHead = new int[]{0, 0};
        this.snake.addLast(snakeHead);
        this.foodList = new LinkedList<>(Arrays.asList(food));
    }

    public int move(String direction) {
        switch(direction) {
            case "L": snakeHead[1]--;
                break;

            case "R": snakeHead[1]++;
                break;

            case "U": snakeHead[0]--;
                break;

            case "D": snakeHead[0]++;
                break;
        }

        //  death on touching a wall
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }

        //  death on touching itself except for the last node
        //  because it will move out of the way at the same time
        for (int i = 1; i < snake.size(); ++i) {
            int[] cur = snake.get(i);

            if (cur[0] == snakeHead[0] && cur[1] == snakeHead[1]) {
                return -1;
            }
        }

        //  eat food
        if (foodList.size() > 0) {
            int[] curFood = foodList.peek();

            if (snakeHead[0] == curFood[0] && snakeHead[1] == curFood[1]) {
                int[] food = foodList.removeFirst();
                snake.addLast(food);
                return snake.size() - 1;
            }
        }

        //  normal movement
        //  remove tail
        snake.removeFirst();
        //  add head
        snake.addLast(new int[]{snakeHead[0], snakeHead[1]});
        return snake.size() - 1;

    }
}
