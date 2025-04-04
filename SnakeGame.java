/*
 * TC: O(n) for move operation where n is the snake's length
 * SC: O(n) for the snake's body
 */
import java.util.*;

record Coordinates(int row, int column) {}

public class SnakeGame {

    /**
     * Direction representing U, D, L, R respectively
     */
    static final List<Coordinates> dirs = Arrays.asList(new Coordinates(-1, 0), new Coordinates(1, 0), new Coordinates(0, -1), new Coordinates(0, 1));

    // Represents the snake's body
    Deque<Coordinates> snake;

    // position of snake's head
    Coordinates head;

    // given information
    int width, height;
    int[][] food;

    // food index/score
    int i;

    public SnakeGame(int width, int height, int[][] food) {
        snake = new ArrayDeque<>();
        head = new Coordinates(0, 0);
        snake.add(head);
        this.width = width;
        this.height = height;
        this.food = food;
        this.i = 0;
    }

    public int move(String direction) {
        Coordinates dir = getDirection(direction);
        // get the new coordinates for head
        int nr = head.row() + dir.row();
        int nc = head.column() + dir.column();

        // check if out of bounds
        if (outOfBounds(nr, nc)) return -1;

        // if a piece of food is consumed, do not remove tail
        if (i < food.length && nr == food[i][0] && nc == food[i][1]) {
            i++;
        }
        // else, remove the tail from the queue
        else {
            snake.removeFirst();
        }
        // check if the snake collided with itself
        if (collision(nr, nc)) return -1;

        // update the head and add it to the queue
        head = new Coordinates(nr, nc);
        snake.addLast(head);

        // return updated food index = score
        return i;
    }

    /**
     * checks if a given row, column are
     * colliding with the snake body
     *
     * @param r row coordinate
     * @param c column coordinate
     * @return if colliding
     */
    boolean collision(int r, int c) {
        for (Coordinates coordinates : snake) {
            if (r == coordinates.row() && c == coordinates.column()) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if a given row, column are
     * out of bounds of the arena
     *
     * @param r row coordinate
     * @param c column coordinate
     * @return whether outOfBounds
     */
    boolean outOfBounds(int r, int c) {
        return r < 0 || c < 0 || r >= height || c >= width;
    }

    /**
     * get direction corresponding to the given string
     *
     * @param direction String dir (U, D, L, R)
     * @return corresponding Coordinate
     */
    Coordinates getDirection(String direction) {
        int dirIndex = switch (direction) {
            case "U" -> 0;
            case "D" -> 1;
            case "L" -> 2;
            case "R" -> 3;
            default -> 0;
        };
        return dirs.get(dirIndex);
    }
}