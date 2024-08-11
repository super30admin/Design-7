import java.util.LinkedList;

/***
 1. Use LinkedList to store body of the snake
 2. Use array to store position of snakeHead
 3. Keep visited matrix to mark cells occupied by snake body and Keep visited cell for curr tail as false
 TC - O(1)
 SC - O(width*height)
 */
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
        //this.visited[0][0] = true; //1st position is occupied initially by snake
    }

    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--; // update snake head row
        }
        if(direction.equals("D")) {
            snakeHead[0]++; // update snake head row
        }
        if(direction.equals("L")) {
            snakeHead[1]--; // update snake head col
        }
        if(direction.equals("R")) {
            snakeHead[1]++; // update snake head col
        }

        //check wall hit condition for the new snake head position
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1; //game over
        }

        //check if new head position collided with its own body
        if(visited[snakeHead[0]][snakeHead[1]] == true)
            return -1;

        //update new head position in snake body
        snakeBody.addFirst(new int[]{snakeHead[0], snakeHead[1]});

        //mark cell as visited
        visited[snakeHead[0]][snakeHead[1]] = true;

        if(idx < food.length) {
            //food position
            int row = food[idx][0];
            int col = food[idx][1];

            //check if food is present in the new snake head position
            if(snakeHead[0] == row && snakeHead[1] == col) {
                //food is consumed; update idx
                idx++;
                return snakeBody.size()-1;
            }
        }

        //remove tail cell
        snakeBody.removeLast();
        //mark curr tail as unvisited
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        //food is not present in the new snake head position; update tail position of the body
        return snakeBody.size()-1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */