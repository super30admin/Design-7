import java.util.Deque;
import java.util.LinkedList;

public class SnakeGame {
    Deque<int[]> snake;
    boolean[][] snakeBody;
    int width;
    int height;
    int[][] food;
    int foodIndex = 0;
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        snakeBody = new boolean[height][width];
        this.width = width;
        this.height = height;
        this.food = food;
        snake.offerLast(new int[]{0, 0});
        snakeBody[0][0] = true;
    }

    // TC: O(1)
    // SC: O(Width * Height)
    public int move(String direction) {
        int[] currHead = snake.peekFirst();
        int headRow = currHead[0];
        int headCol = currHead[1];
        if (direction.equals("U")) {
            headRow--;
        } else if (direction.equals("D")) {
            headRow++;
        } else if (direction.equals("L")) {
            headCol--;
        } else {
            headCol++;
        }
        int[] currTail = snake.peekLast();
        int[] newHead = new int[]{headRow, headCol};
        boolean isOutsideGrid = headRow < 0 || headRow >= height || headCol < 0 || headCol >= width;
        if (isOutsideGrid) return -1;
        boolean bitesItself = snakeBody[newHead[0]][newHead[1]] && !(newHead[0] == currTail[0] && newHead[1] == currTail[1]);
        if (bitesItself) return -1;
        if (foodIndex < food.length && food[foodIndex][0] == headRow && food[foodIndex][1] == headCol) {
            foodIndex++;
        } else {
            snake.pollLast();
            snakeBody[currTail[0]][currTail[1]] = false;
        }
        snake.addFirst(newHead);
        snakeBody[newHead[0]][newHead[1]] = true;
        return snake.size() - 1;
    }
}
