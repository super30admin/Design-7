import java.util.LinkedList;

public class SnakeGame {
    LinkedList<int[]> snakeBody;
    boolean[][] visited;
    int w, h;
    int[][] food;
    int idx; // index pointer for the food

    public SnakeGame(int width, int height, int[][] food) {
        this.snakeBody = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.w = width;
        this.h = height;
        this.food = food;
        this.idx = 0;
        snakeBody.addFirst(new int[]{0, 0});
    }

    public int move(String direction) {
        int[] head = snakeBody.getFirst();
        int r = head[0], c = head[1];

        if (direction.equals("L")) {
            c--;
        } else if (direction.equals("R")) {
            c++;
        } else if (direction.equals("U")) {
            r--;
        } else if (direction.equals("D")) {
            r++;
        }

        if (r < 0 || c < 0 || r == h || c == w || visited[r][c])
            return -1;

        if (idx < food.length) {
            if (food[idx][0] == r && food[idx][1] == c) {
                snakeBody.addFirst(new int[]{r, c});
                visited[r][c] = true;
                idx++;
                return snakeBody.size() - 1;
            }
        }

        snakeBody.addFirst(new int[]{r, c});
        visited[r][c] = true;

        snakeBody.removeLast();
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size() - 1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */

//TC: O(1) - all operations, SC: O(h*w)
