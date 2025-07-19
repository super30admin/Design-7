public class SnakeGame {
    private List<List<Integer>> foodList;
    int foodIdx = 0;
    private LinkedList<int[]> snake;
    private boolean[][] visited;
    int width;
    int height;
    int score;

    public SnakeGame(int width, int height, List<List<Integer>> foods) {
        this.foodList = foods;
        this.width = width;
        this.height = height;
        this.visited = new boolean[height][width];
        this.snake = new LinkedList<>();
        this.score = 0;

        int[] initialHead = {0, 0};
        this.snake.addFirst(initialHead);
        this.visited[0][0] = true;
    }

    public int move(String direction) {
        int[] currentHead = snake.getFirst();
        int newHeadRow = currentHead[0];
        int newHeadCol = currentHead[1];

        if (direction.equals("R")) {
            newHeadCol += 1;
        } else if (direction.equals("D")) {
            newHeadRow += 1;
        } else if (direction.equals("U")) {
            newHeadRow -= 1;
        } else if (direction.equals("L")) {
            newHeadCol -= 1;
        }

        if (newHeadRow < 0 || newHeadRow >= height || newHeadCol < 0 || newHeadCol >= width) {
            return -1;
        }

        int[] tail = snake.getLast();

        if (visited[newHeadRow][newHeadCol] && !(newHeadRow == tail[0] && newHeadCol == tail[1])) {
            return -1;
        }

        int[] newHead = new int[]{newHeadRow, newHeadCol};

        boolean ateFood = false;
        if (foodIdx < foodList.size()) {
            List<Integer> currFood = foodList.get(foodIdx);
            if (newHeadRow == currFood.get(0) && newHeadCol == currFood.get(1)) {
                ateFood = true;
                foodIdx++;
                score++;
            }
        }

        if (!ateFood) {
            int[] oldTail = snake.removeLast();
            visited[oldTail[0]][oldTail[1]] = false;
        }
        snake.addFirst(newHead);
        visited[newHeadRow][newHeadCol] = true;

        return score;
    }
}
