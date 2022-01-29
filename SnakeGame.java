// Time Complexity : O(n)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Approach

// We design this using LinkedList
// We go in the directions given by moving the snakehead and adding to the snake lisst
// We check if we have reached the edge or if we are in the snake's body
// and when we find food we increase the snake length
// we return snake's length as result

class SnakeGame {
    LinkedList<int[]> snake;
    LinkedList<int[]> foodItems;
    int[] snakeHead;
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[] { 0, 0 };
        snake.add(snakeHead);
        this.width = width;
        this.height = height;
    }

    public int move(String direction) {
        if (direction.equals("U")) {
            snakeHead[0]--;
        } else if (direction.equals("D")) {
            snakeHead[0]++;
        } else if (direction.equals("L")) {
            snakeHead[1]--;
        } else if (direction.equals("R")) {
            snakeHead[1]++;
        }
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }

        for (int i = 1; i < snake.size(); i++) {
            int[] curr = snake.get(i);
            if (snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) {
                return -1;
            }
        }
        if (foodItems.size() > 0) {
            int[] food = foodItems.peek();
            if (snakeHead[0] == food[0] && snakeHead[1] == food[1]) {
                snake.add(new int[] { snakeHead[0], snakeHead[1] });
                foodItems.remove();
                return snake.size() - 1;
            }
        }
        snake.add(new int[] { snakeHead[0], snakeHead[1] });
        snake.remove();
        return snake.size() - 1;
    }
}
