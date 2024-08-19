/*
    LC - 353. Design Snake Game
    TC - O(1)
    SC - O(m * n)
*/

class SnakeGame {

    List<int[]> snakeBody;
    int[] snakeHead;
    int[][] food;
    boolean[][] visited;
    int idx; // for food
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        this.snakeBody = new LinkedList<>();
        this.snakeBody.addFirst(new int[] {0, 0});
        this.width = width;
        this.height = height;
        this.snakeHead = new int[] {0, 0};
        this.food = food;
        this.visited = new boolean[height][width];
        this.idx = 0;
    }
    
    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        }
        else if(direction.equals("D")) {
            snakeHead[0]++;
        } 
        else if(direction.equals("L")) {
            snakeHead[1]--;
        }
        else if(direction.equals("R")) {
            snakeHead[1]++;
        }
        // check if the snake is touching the walls
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }
        if (visited[snakeHead[0]][snakeHead[1]]) {
            return -1;
        }
        // eat the food
        if(idx < food.length) {
            if(food[idx][0] == snakeHead[0] && food[idx][1] == snakeHead[1]) {
                snakeBody.addFirst(new int[] {snakeHead[0], snakeHead[1]});
                idx++;
                visited[snakeHead[0]][snakeHead[1]] = true;
                return snakeBody.size() -1;
            }
        }
        snakeBody.addFirst(new int[]{snakeHead[0], snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        snakeBody.removeLast();
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */