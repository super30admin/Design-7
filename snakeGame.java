// Time Complexity: O(1) for each move operation
// Space Complexity: O(width * height) for the grid and snake storage
class SnakeGame {
    int[][] food;             // Array to store the positions of the food
    int foodIdx;             // Index to keep track of which piece of food is currently available
    boolean[][] grid;        // Grid to keep track of which cells are occupied by the snake
    LinkedList<int[]> snake; // Linked list representing the snake where each node is a cell position

    public SnakeGame(int width, int height, int[][] food) {
        // Initialize the snake game with the given width, height, and food positions
        this.snake = new LinkedList<>();
        this.grid = new boolean[height][width];
        this.food = food;
        this.foodIdx = 0;
        // Start the snake with a length of 1 at position (0, 0)
        this.snake.add(new int[] { 0, 0 });
        this.grid[0][0] = true; // Mark the initial position as occupied
    }

    private boolean isSafe(int row, int col) {
        // Check if the new position is out of bounds
        if (row >= grid.length || row < 0 || col >= grid[0].length || col < 0)
            return false;

        // Check if the new position is occupied by the snake
        if (grid[row][col]) {
            // If the new position is occupied, ensure it is not the tail
            int tailRow = snake.getLast()[0];
            int tailCol = snake.getLast()[1];
            if (row == tailRow && col == tailCol) {
                // If it's the tail, it's safe to move
                return true;
            }
            // If it's not the tail, it's a collision (snake runs into itself)
            return false;
        }

        // If the cell is not occupied by the snake, it's safe to move
        return true;
    }

    public int move(String direction) {
        // Get the current position of the snake's head
        int[] foodPair = (foodIdx < food.length) ? food[foodIdx] : null;
        int snakeRow = snake.getFirst()[0];
        int snakeCol = snake.getFirst()[1];

        // Update the snake's head position based on the direction
        if (direction.equals("R")) {
            snakeCol += 1;
        } else if (direction.equals("U")) {
            snakeRow -= 1;
        } else if (direction.equals("D")) {
            snakeRow += 1;
        } else if (direction.equals("L")) {
            snakeCol -= 1;
        }

        // Check if the new position is safe to move to
        if (!isSafe(snakeRow, snakeCol))
            return -1; // Return -1 if the move results in a collision or going out of bounds

        // Check if the new position is where the food is located
        if (foodPair!=null && snakeRow == foodPair[0] && snakeCol == foodPair[1]) {
            // Move to the next piece of food
            foodIdx++;
            // Do not remove the tail since the snake is growing
        } else {
            // Remove the tail if not eating food
            int[] tail = snake.removeLast();
            grid[tail[0]][tail[1]] = false; // Mark the tail's position as unoccupied
        }

        // Add the new head position to the snake
        snake.addFirst(new int[] { snakeRow, snakeCol });
        grid[snakeRow][snakeCol] = true; // Mark the new head's position as occupied

        // Return the current score, which is the length of the snake minus 1 (initial length not counted)
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
