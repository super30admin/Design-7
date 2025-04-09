// TC: O(food.length + snake.length)
// SC: O(food.length + snake.length)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SnakeGame {
    int width;
    int height;
    int[] head;
    LinkedList<int[]> snake = new LinkedList<>();
    LinkedList<int[]> foodList;

    /**
     * Initialize your data structure here.
     *
     * @param width  - screen width
     * @param height - screen height 
     * @param food   - A list of food positions.
     *               E.g. food = [[1,1], [1,0]] means the first food is at [1,1], the second at [1,0].
     */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        head = new int[]{0, 0};
        snake.add(head);
        foodList = new LinkedList<>(Arrays.asList(food));
    }

    /**
     * Moves the snake.
     *
     * @param direction - "U" = Up, "L" = Left, "R" = Right, "D" = Down.
     * @return The game's score after the move. Return -1 if game over.
     *         Game over when snake crosses the screen boundary or bites its body.
     */
    public int move(String direction) {
        if (direction.equals("U")) {
            head[0] -= 1;
        }
        if (direction.equals("D")) {
            head[0] += 1;
        }
        if (direction.equals("L")) {
            head[1] -= 1;
        }
        if (direction.equals("R")) {
            head[1] += 1;
        }

        // Check boundaries.
        if (head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width) {
            return -1;
        }

        // Check self-collision (ignore the head at index 0).
        for (int i = 1; i < snake.size(); i++) {
            int[] curr = snake.get(i);
            if (curr[0] == head[0] && curr[1] == head[1]) {
                return -1;
            }
        }

        // Eat food.
        if (!foodList.isEmpty()) {
            int[] appearedFood = foodList.get(0);
            if (appearedFood[0] == head[0] && appearedFood[1] == head[1]) {
                // When eating food, extend the snake.
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }

        // Move: remove tail and add new head position.
        snake.remove();
        snake.add(new int[]{head[0], head[1]});
        return snake.size() - 1;
    }
}
