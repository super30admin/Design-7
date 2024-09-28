// Problem 353. Design Snake Game
// Time Complexity : O(1)
// Space Complexity : O(w*h + f)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
class SnakeGame {
    boolean[][] visited;   // To track snake positions
    int w, h;              // Width and height of the grid
    int[][] food;          // Food positions
    int i;                 // Current index of the food
    LinkedList<int[]> snake;  // To represent the snake as a LinkedList of coordinates
    int[] snakeHead;       // Snake head's position

    public SnakeGame(int width, int height, int[][] food) {
        this.snakeHead = new int[]{0, 0};  // Snake starts at position (0, 0)
        this.food = food;
        this.w = width;
        this.h = height;
        this.snake = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.snake.add(new int[]{0, 0});  // Add initial snake head to the LinkedList
        this.visited[0][0] = true;  // Mark snake's starting position as visited
        this.i = 0;  // Initialize the food index
    }

    public int move(String direction) {
        // Get the current head position
        int row = snakeHead[0];
        int col = snakeHead[1];
        // Update snake head position based on the direction
        if (direction.equals("L")) {
            col--;
        } else if (direction.equals("R")) {
            col++;
        } else if (direction.equals("D")) {
            row++;
        } else if (direction.equals("U")) {
            row--;
        }
        // Check if snake head hits borders
        if (row < 0 || row >= h || col < 0 || col >= w) {
            return -1;  // Game over
        }
        // Check if snake runs into itself (ignore the tail which will move)
        if (visited[row][col] && !(row == snake.getLast()[0] && col == snake.getLast()[1])) {
            return -1;  // Game over
        }
        // Move the head to the new position
        snakeHead[0] = row;
        snakeHead[1] = col;
        // Check if the snake eats food
        if (i < food.length && row == food[i][0] && col == food[i][1]) {
            i++;
        } else {
            int[] tail = snake.removeLast();
            visited[tail[0]][tail[1]] = false;
        }
        snake.addFirst(new int[]{row, col});
        visited[row][col] = true;
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */