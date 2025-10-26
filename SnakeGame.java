import java.util.LinkedList;

public class SnakeGame {

    private final int[][] food;
    private final int w;
    private final int h;

    private final boolean[][] visited;
    private final LinkedList<int[]> snake;
    private final int[] head;

    private int idx;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.food = food;

        this.visited = new boolean[height][width];
        this.snake = new LinkedList<>();
        this.head = new int[] {0, 0};

        this.snake.add(this.head);
    }

    public int move(final String direction) {
        switch (direction) {
            case "U":
                this.head[0]--;
                break;
            case "D":
                this.head[0]++;
                break;
            case "L":
                this.head[1]--;
                break;
            default:
                this.head[1]++;
                break;
        }

        if (this.head[0] < 0 || this.head[0] == this.h || this.head[1] < 0 || this.head[1] == w) return -1;
        if (this.visited[this.head[0]][this.head[1]]) return -1;

        //food move
        if (this.idx < this.food.length) {
            int[] current = this.food[idx];
            if (current[0] == this.head[0] && current[1] == this.head[1]) {
                this.idx++;

                this.snake.addFirst(new int[] {this.head[0], this.head[1]});
                this.visited[this.head[0]][this.head[1]] = true;

                return this.snake.size() - 1;
            }
        }

        //normal move
        this.snake.addFirst(new int[] {this.head[0], this.head[1]});
        this.visited[this.head[0]][this.head[1]] = true;

        this.snake.removeLast();
        int[] tail = this.snake.getLast();
        this.visited[tail[0]][tail[1]] = false;

        return this.snake.size() - 1;
    }
}
