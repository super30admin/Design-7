import java.util.*;

class SnakeGame {

    int[][] food;
    int foodIdx;
    LinkedList<int[]> snakeBody;
    int w;
    int h;
    boolean[][] visited;

    public SnakeGame(int width, int height, int[][] food) {
        this.food = food;
        this.foodIdx = 0;
        this.snakeBody = new LinkedList<>();
        this.w = width;
        this.h = height;
        this.visited = new boolean[h][w];

        snakeBody.offer(new int[] {0,0});
    }
    
    // O(1) time
    public int move(String direction) {
        int[] head = snakeBody.getFirst();
        int r = head[0];
        int c = head[1];

        if (direction.equals("R")) {
            c++;
        }
        else if (direction.equals("L")) {
            c--;
        }
        else if (direction.equals("U")) {
            r--;
        }
        else if (direction.equals("D")) {
            r++;
        }

        // out of bounds or hitting its own body
        if (r < 0 || c < 0 || r >= h || c >= w || visited[r][c]) {
            return -1;
        }

        if (foodIdx < food.length) {

            int[] currFood = food[foodIdx];
            if (r == currFood[0] && c == currFood[1]) {
                foodIdx++;
                snakeBody.addFirst(new int[] {r, c});
                visited[r][c] = true;
                return snakeBody.size() - 1;
            }
        }

        snakeBody.addFirst(new int[] {r, c});
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
