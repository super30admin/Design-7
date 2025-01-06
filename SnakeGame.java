// Approach : Utilizing a separte boolean matrix to track snakes body positions and as snake moves track its positions with idea of linked list.
// Process each move by determining the new head position based on the direction, check for boundary collision or self-collision, and update the snake's body.
// If the snake eats food, extend its body. Otherwise, remove its tail to maintain its current length.
// Return the snakeBody size, or -1 if the game is over.
// Time : O(1) per move
// Space : O(n), where n is the maximum length of the snake.
class SnakeGame {
    boolean[][] visited; // 2D matrix
    int[][] food;
    int h ,w;
    LinkedList<int []> snakeBody;
    int idx;
    public SnakeGame(int width, int height, int[][] food) {
       this.h = height; 
       this.w = width;
       this.snakeBody = new LinkedList<>();
       this.food = food;
       this.visited = new boolean[h][w]; // we dont mark snake's tail
       this.idx = 0;
       // take the initial position of the snake - head
       snakeBody.addFirst(new int[]{0,0});
    }
    
    public int move(String direction) {
        // when user enters any direction - up , down, right , left then snake has to move
        // fetch the snake's head direction first before you start to move
        int[] head = snakeBody.getFirst(); // [0,0]
        // extract the row and column position
        int r = head[0];
        int c = head[1];
        if(direction.equals("R")){
            // move to right
            c++;
        }else if (direction.equals("L")){
            c--;
        }else if (direction.equals("U")){
            r--;
        }else if (direction.equals("D")){
            r++;
        }

        // as we moved one or other direcion we need to check for boundary conditions
        // hit wall - 1
        // snake eatself - 2
        if(r<0 || c<0 || r == h || c == w || visited[r][c]) return -1;

        // if new position lands on a row and col besides the above 
        // 1. check if we have food or not - (idx iterating over the food)
        // 2. if food exists then increase the size of snake
        if(idx < food.length){
            // food exists now check where we have food
            if(food[idx][0] == r && food[idx][1] == c){
                // eat the food 
                idx++;
                // now snake will have a new head. so add it to the snake body
                snakeBody.addFirst(new int[]{r,c});
                // now mark it as visited
                visited[r][c] = true;
                return snakeBody.size()-1;
            }
        }

        // now if we don't have food - idx is out of bounds of food matrix
        // 1. Add new Head first 
        snakeBody.addFirst(new int[]{r,c});
        // 2. mark the new moved position 
        visited[r][c] =true;
        // 3. *** remove the already existing tail at the very end
        snakeBody.removeLast();
        // 4. Now get the snakes latest tail and mark it as unvisited
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */