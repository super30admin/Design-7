// Time Complexity : O(1) for each move operation
// Space Complexity : O(width * height) for visited tracking and snake body
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach in three sentences only
// We use a LinkedList to maintain the snake body, where the head is added at the end and the tail is removed from the front.
// A visited grid helps us check self-collision in O(1) time.
// On each move, we update the head position, handle food consumption, and detect collisions.

class SnakeGame {
    LinkedList<int[]> snake;
    boolean[][] visited;
    int[][] foodList;
    int i;
    int[] snakeHead;
    int W, H;

    public SnakeGame(int width, int height, int[][] food) {
        this.snake = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.foodList = food;
        this.W = width;
        this.H = height;
        this.i = 0;

        snakeHead = new int[]{0, 0};
        snake.addLast(snakeHead);
        visited[0][0] = true;
    }

    public int move(String direction) {
        int newRow = snakeHead[0];
        int newCol = snakeHead[1];

        // compute next head position
        if(direction.equals("U")) newRow--;
        else if(direction.equals("D")) newRow++;
        else if(direction.equals("L")) newCol--;
        else newCol++;

        // wall collision
        if(newRow < 0 || newRow == H || newCol < 0 || newCol == W) {
            return -1;
        }

        boolean eatFood = false;

        // check if food is eaten
        if(i < foodList.length) {
            int[] currFood = foodList[i];
            if(currFood[0] == newRow && currFood[1] == newCol) {
                eatFood = true;
                i++;
            }
        }

        // move tail only if no food eaten
        if(!eatFood) {
            int[] tail = snake.removeFirst();
            visited[tail[0]][tail[1]] = false;
        }

        // self collision
        if(visited[newRow][newCol]) {
            return -1;
        }

        // add new head
        snakeHead = new int[]{newRow, newCol};
        snake.addLast(snakeHead);
        visited[newRow][newCol] = true;

        return snake.size() - 1;
    }
}