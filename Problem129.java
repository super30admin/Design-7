//TC: O(1)
//SC: O(w*h)
class SnakeGame {
    private int width, height, score;
    private Deque<int[]> snake; // stores the body of the snake
    private Set<String> snakeSet; // for quick lookup of the snake body
    private int[][] food;
    private int foodIndex;
    private int[] snakeHead; // stores the head of the snake

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.snake = new LinkedList<>();
        this.snakeSet = new HashSet<>();
        this.snake.add(new int[]{0, 0}); // initial position
        this.snakeSet.add("0,0"); // add initial position to the set
        this.snakeHead = new int[]{0, 0};
        this.foodIndex = 0;
        this.score = 0;
    }

    public int move(String direction) {
        int row = snakeHead[0];
        int col = snakeHead[1];

        // Move the snake's head based on the direction
        switch (direction) {
            case "U":
                row--;
                break;
            case "D":
                row++;
                break;
            case "L":
                col--;
                break;
            case "R":
                col++;
                break;
        }

        // Check if the new head position is out of bounds
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return -1;
        }

        // Move the snake's head
        int[] newHead = new int[]{row, col};
        String newHeadPosition = row + "," + col;

        // Check if the snake eats food
        if (foodIndex < food.length && row == food[foodIndex][0] && col == food[foodIndex][1]) {
            // Eat the food and increase the score
            score++;
            foodIndex++;
        } else {
            // Move the snake: remove the tail (if no food is eaten)
            int[] tail = snake.removeLast();
            snakeSet.remove(tail[0] + "," + tail[1]);
        }

        // Check if the new head position collides with the body (after moving the tail)
        if (snakeSet.contains(newHeadPosition)) {
            return -1;
        }

        // Update the snake's head position
        snake.addFirst(newHead);
        snakeSet.add(newHeadPosition);
        snakeHead[0] = row;
        snakeHead[1] = col;

        return score;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
