// Time Complexity : O(N)
// Space Complexity :O(N) where N is max snake length.
// Did this code successfully run on Leetcode : Yes 
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
//We keep track of the snake’s body using a linked list and update its head on every move.
//We check if the new head hits the wall or the body (excluding the last tail, which may move).
//If the new head is at a food location, we grow the snake, otherwise, we move forward by adding head and removing tail.
class SnakeGame {
    int w; int h; int idx;
    LinkedList<int []> snake;
    int[] snakeHead;
    int[][] foodList;
    boolean[][] visited;

    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.idx = 0;
        this.foodList = food;
        this.snakeHead = new int[]{0,0};
        this.snake = new LinkedList<>();
        snake.addFirst(this.snakeHead);
        this.visited = new boolean[height][width];
    }
    
    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        } else if(direction.equals("L")) {
            snakeHead[1]--;
        } else if(direction.equals("R")) {
            snakeHead[1]++;
        } else {
            //down
            snakeHead[0]++;
        }
        //out of bounds check
        if(snakeHead[0] < 0 || snakeHead[0] == h || snakeHead[1] < 0 || snakeHead[1] == w) return -1;
        //hits itself
        if(visited[snakeHead[0]][snakeHead[1]]) return -1;

        //food move
        if(idx < foodList.length) {
            int[] curFood = foodList[idx];
            if(curFood[0] == snakeHead[0] && curFood[1] == snakeHead[1]) {
                //eat food
                idx++;
                snake.addFirst(new int[]{snakeHead[0],snakeHead[1]});
                visited[snakeHead[0]][snakeHead[1]] = true;
                return snake.size() - 1;
            }
        }

        //normal move
        snake.addFirst(new int[] {snakeHead[0],snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        snake.removeLast(); //remove the tail
        //make the current tail unvisted 
        int[] curTail = snake.getLast();
        visited[curTail[0]][curTail[1]] = false;
        return snake.size() - 1;
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
