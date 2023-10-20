//tc = O(1)
//sc = O(1)

class SnakeGame {
    LinkedList<int[]> snake;
    int[] headPtr;
    boolean[][] visited;
    int w;
    int h;
    int i;
    int[][] foodList;

    public SnakeGame(int width, int height, int[][] food) {
        this.snake = new LinkedList<>();
        this.headPtr = new int[] { 0, 0 };
        this.snake.addLast(this.headPtr);
        this.visited = new boolean[height][width];
        this.w = width;
        this.h = height;
        this.foodList = food;
    }

    public int move(String direction) {
        if (direction.equals("u")) {
            headPtr[0]--;
        }
        if (direction.equals("D")) {
            headPtr[0]++;
        }
        if (direction.equals("R")) {
            headPtr[1]++;
        } else {
            headPtr[1]--;
        }
        // boundary
        if (headPtr[0] < 0 || headPtr[0] == h || headPtr[1] < 0 || headPtr[1] == w) {
            return -1;
        }
        // hit yourself
        if (visited[headPtr[0]][headPtr[1]]) {
            return -1;
        }
        // eats food
        if (i < foodList.length) {
            int[] currFood = foodList[i];
            if (currFood[0] == headPtr[0] && currFood[1] == headPtr[1]) {
                this.snake.addLast(new int[] { headPtr[0], headPtr[1] });
                this.visited[headPtr[0]][headPtr[1]] = true;
                i++;
                return snake.size() - 1;
            }
        }
        // Move this line here to add the new position to the snake.
        this.snake.addLast(new int[] { headPtr[0], headPtr[1] });
        this.visited[headPtr[0]][headPtr[1]] = true;
        this.snake.removeFirst();
        int[] newTail = this.snake.getFirst();
        this.visited[newTail[0]][newTail[1]] = false;
        return this.snake.size() - 1;
    }
}
