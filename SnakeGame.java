import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class SnakeGame {

    private int height;
    private int width;
    private int[][] food;
    private int score;
    private int foodIndex;
    private Deque<Integer> snakeBody = new ArrayDeque<>();
    private Set<Integer> visited = new HashSet<>();

    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        this.food = food;
        snakeBody.offer(0);
        visited.add(0);
    }

    public int move(String direction) {
        int head = snakeBody.peekFirst();
        int row = head / width, col = head % width;
        int newRow = row, newCol = col;

        switch (direction) {
            case "U":
                newRow--;
                break;
            case "D":
                newRow++;
                break;
            case "L":
                newCol--;
                break;
            case "R":
                newCol++;
                break;
        }

        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
            return -1;
        }

        if (foodIndex < food.length && newRow == food[foodIndex][0] && newCol == food[foodIndex][1]) {
            score++;
            foodIndex++;
        } else {
            int tail = snakeBody.pollLast();
            visited.remove(tail);
        }

        int newHead = flattenPosition(newRow, newCol);

        if (visited.contains(newHead)) {
            return -1;
        }

        snakeBody.offerFirst(newHead);
        visited.add(newHead);

        return score;
    }

    private int flattenPosition(int i, int j) {
        return i * width + j;
    }
}