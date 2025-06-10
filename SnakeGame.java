// Time complexity: O(1)
// Space Complexity: O(N + F)
import java.awt.Point;
import java.util.*;

class SnakeGame {
    private int width;
    private int height;
    private int[][] food;
    private int foodIndex;
    private Deque<Point> snake;
    private Set<Point> snakeSet;
    private int score;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.foodIndex = 0;
        this.snake = new LinkedList<>();
        this.snakeSet = new HashSet<>();
        Point start = new Point(0, 0);
        snake.addLast(start);
        snakeSet.add(start);
        this.score = 0;
    }

    public int move(String direction) {
        if (score == -1) return -1; // game over

        Point head = snake.peekLast();
        int newRow = head.x;
        int newCol = head.y;

        switch (direction) {
            case "U": newRow--; break;
            case "D": newRow++; break;
            case "L": newCol--; break;
            case "R": newCol++; break;
        }

        Point newHead = new Point(newRow, newCol);

        // Check wall collision
        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
            score = -1;
            return -1;
        }

        // Remove tail (only if not eating food)
        Point tail = snake.peekFirst();

        // Check if eating food
        boolean eating = foodIndex < food.length &&
                         food[foodIndex][0] == newRow &&
                         food[foodIndex][1] == newCol;

        if (!eating) {
            snake.pollFirst();
            snakeSet.remove(tail);
        }

        // Check self collision
        if (snakeSet.contains(newHead)) {
            score = -1;
            return -1;
        }

        // Add new head
        snake.addLast(newHead);
        snakeSet.add(n
