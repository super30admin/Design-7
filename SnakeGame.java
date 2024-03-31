// TC: O(1)

// Approach: Used a linked list to make add to head and remove tail
// in O(1). Kept a grid height * width and kept track of snake's body.
// It's tail will always be marked unvisited

import java.util.LinkedList;

class SnakeGame {
    private int foodIndex;
    private LinkedList<int[]> snake;
    private boolean[][] grid;
    private int[][] food;

    public SnakeGame(int width, int height, int[][] food) {
        this.foodIndex = 0;
        this.snake = new LinkedList<>();
        this.snake.addFirst(new int[] { 0, 0 });
        this.food = food;
        this.grid = new boolean[height][width];
    }

    public int move(String direction) {
        int[] currentHead = snake.getFirst();
        int r = currentHead[0];
        int c = currentHead[1];

        switch (direction) {
            case "U":
                r--;
                break;
            case "D":
                r++;
                break;
            case "L":
                c--;
                break;
            case "R":
                c++;
                break;
        }

        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
            return -1;
        }

        // food found
        if (foodIndex < food.length && r == food[foodIndex][0] && c == food[foodIndex][1]) {
            // dont take tail out
            foodIndex++;
        } else {
            // take tail out
            snake.removeLast();
        }

        // snake ate it's own body
        if (grid[r][c]) {
            return -1;
        }

        // add head
        snake.addFirst(new int[] { r, c });
        // mark as visited
        grid[r][c] = true;

        int[] last = snake.getLast();
        // mark new tail as not visited
        grid[last[0]][last[1]] = false;

        return foodIndex;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */