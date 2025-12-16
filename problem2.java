// Time Complexity :O(n)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :no

/*
Approach
We are using a linkedlist to update snake body,
a boolean array "visited" check for sake body
so when we move we update the snake head, check bounds of game, check if it hits itself, update snake size if at foodmove, so update head
and tail for normal move
*/
class SnakeGame {
    int idx;
    int w;
    int h;
    LinkedList<int[]> snake;
    int[] snakehead;
    boolean[][] visited;
    int[][] foodList;

    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.foodList = food;
        this.snake = new LinkedList<>();
        this.snakehead = new int[] { 0, 0 };
        this.visited = new boolean[height][width];
        snake.addFirst(this.snakehead);
    }

    public int move(String direction) {
        if (direction.equals("U")) {
            snakehead[0]--;
        } else if (direction.equals("L")) {
            snakehead[1]--;
        } else if (direction.equals("R")) {
            snakehead[1]++;
        } else {
            // down
            snakehead[0]++;
        }

        // bound check
        if (snakehead[0] < 0 || snakehead[0] == h || snakehead[1] < 0 || snakehead[1] == w) {
            return -1;
        }
        // hit itself
        if (visited[snakehead[0]][snakehead[1]]) {
            return -1;
        }

        // foodmove
        if (idx < foodList.length) {
            int[] currfood = foodList[idx];
            if (currfood[0] == snakehead[0] && currfood[1] == snakehead[1]) {
                idx++;
                snake.addFirst(new int[] { snakehead[0], snakehead[1] });
                visited[snakehead[0]][snakehead[1]] = true;
                return snake.size() - 1;
            }
        }
        // normal
        snake.addFirst(new int[] { snakehead[0], snakehead[1] });
        visited[snakehead[0]][snakehead[1]] = true;
        // remove the tail
        snake.removeLast();
        int[] curtail = snake.getLast();
        visited[curtail[0]][curtail[1]] = false;
        return snake.size() - 1;
    }
}
