// Time Complexity : move(String direction) - O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class SnakeGame {
    LinkedList<int[]> snake;
    int i; // pointer on the food;
    int[] snakeHead;
    boolean[][] visited;
    int width;
    int height;
    int[][] foodList;

    public SnakeGame(int width, int height, int[][] food) {
        this.snake = new LinkedList<>();
        this.snakeHead = new int[] {0,0};
        this.visited = new boolean[height][width];
        this.width = width;
        this.height = height;
        this.foodList = food;
        // snake head is tail of linkedList;
        // snake tail is head of linkedList;
        this.snake.addLast(snakeHead);
    }

    public int move(String direction) { //O(1)
        if (direction.equals("R")) {
            snakeHead[1]++;
        } else if (direction.equals("L")) {
            snakeHead[1]--;
        } else if (direction.equals("U")) {
            snakeHead[0]--;
        } else if (direction.equals("D")) {
            snakeHead[0]++;
        }
        // bounds check
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }
        // if snake hits itself
        // never mark tail inside visited matrix
        if (visited[snakeHead[0]][snakeHead[1]]) {
            return -1;
        }
        // move when food is there
        if (i < foodList.length) {
            int[] foodLocation = foodList[i];
            if (snakeHead[0] == foodLocation[0] && snakeHead[1] == foodLocation[1]) {
                // eat food
                i++;
                int[] head = new int[] {snakeHead[0], snakeHead[1]};
                snake.addLast(head);
                visited[snakeHead[0]][snakeHead[1]] = true;
                return snake.size() - 1;
            }
        }
        // move when food is not there
        int[] head = new int[] {snakeHead[0], snakeHead[1]};
        snake.addLast(head);
        visited[head[0]][head[1]] = true;
        snake.removeFirst();
        int[] tail = snake.peek();
        visited[tail[0]][tail[1]] = false;
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */