//https://leetcode.com/problems/design-snake-game/
/*
Time: O(height*width)
Space: O(height*width) 
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : None
*/
public class SnakeGame {
    Queue<Integer> snake;
    boolean[][] visited;
    int[][] food; // position of a piece of food
    int foodIndex; // 1d index
    int height, width;
    int row, col;
    int score;

    /**
     * Initialize your data structure here.
     * 
     * @param width  - screen width
     * @param height - screen height
     * @param food   - A list of food positions E.g food = [[1,1], [1,0]] means the
     *               first food is positioned at [1,1], the second is at [1,0].
     */
    public SnakeGame(int width, int height, int[][] food) {
        this.visited = new boolean[height][width];
        this.food = food;
        this.foodIndex = 0;
        this.snake = new LinkedList<>();
        this.snake.offer(0); // Initially snake is at 0th (1D position)
        this.visited[0][0] = true;

        this.width = width;
        this.height = height;

        this.row = 0;
        this.col = 0;

        this.score = 0;
    }

    /**
     * Moves the snake.
     * 
     * @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     * @return The game's score after the move. Return -1 if game over. Game over
     *         when snake crosses the screen boundary or bites its body.
     */
    public int move(String direction) {
        if (score == -1)
            return score;
        if (direction.equals("U"))
            row--;
        else if (direction.equals("L"))
            col--;
        else if (direction.equals("R"))
            col++;
        else if (direction.equals("D"))
            row++;

        // cross boundary
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return score = -1;
        }
        // not food
        if (foodIndex == food.length || !(row == food[foodIndex][0] && col == food[foodIndex][1])) // Note
        {
            int idx = snake.poll();
            visited[idx / width][idx % width] = false;
        } else // found food. Eat it.
        {
            score++; // inc score
            foodIndex++; // move to the next food
        }

        if (visited[row][col]) // already visited place
        { // bite itself
            return score = -1;
        } else // not visited
        {
            snake.offer(row * width + col); // snake has moved to next pos. Add this 1D pos to Queue.
            visited[row][col] = true;
        }
        return score;
    }

}
