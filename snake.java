// Time Complexity : 0(1)
// Space Complexity : O(len x width)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SnakeGame {

    LinkedList<int[]> snakeBody;
    int[] snakeHead;
    int[][] food;
    boolean[][] visited;
    int idx;
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.snakeBody = new LinkedList<>();
        this.snakeHead = new int[2];
        this.visited = new boolean[height][width];
        this.food = food;
        this.idx = 0;
        this.snakeBody.addFirst(new int[]{0, 0});
    }

    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--; 
        }
        if(direction.equals("D")) {
            snakeHead[0]++; 
        }
        if(direction.equals("L")) {
            snakeHead[1]--; 
        }
        if(direction.equals("R")) {
            snakeHead[1]++; 
        }


        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1; // Wall check
        }

        if(visited[snakeHead[0]][snakeHead[1]] == true)
            return -1;

        snakeBody.addFirst(new int[]{snakeHead[0], snakeHead[1]});

        visited[snakeHead[0]][snakeHead[1]] = true;

        // eat food
        if(idx < food.length) {
            int row = food[idx][0];
            int col = food[idx][1];

            if(snakeHead[0] == row && snakeHead[1] == col) {
                idx++;
                return snakeBody.size()-1;
            }
        }


        snakeBody.removeLast();
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size()-1;

    }
}
