class SnakeGame {
    //Time Complexity:O(1)
    //Space Complexity:O(m*n)
    
    int[][] food;
    int width, height;
    int idx;
    LinkedList<int[]> snakeBody;
    int[] snakeHead;
    boolean[][] visited;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeBody = new LinkedList<>();
        snakeHead = new int[]{0,0};
        this.food = food;
        this.idx = 0;
        snakeBody.addFirst(new int[]{0,0});
        this.visited = new boolean[height][width];
        
    }
    
    public int move(String direction) {
        if (direction.equals("U")){
            snakeHead[0]--;
        }else if (direction.equals("D")){
            snakeHead[0]++;
        }else if (direction.equals("L")){
            snakeHead[1]--;
        }else if (direction.equals("R")){
            snakeHead[1]++;
        }
        // if snake hits the boundary
        if (snakeHead[0] < 0|| snakeHead[0] == height || 
            snakeHead[1] < 0 || snakeHead[1] == width){
                return -1;
            }
        // snake touches its own body
         if (visited[snakeHead[0]][snakeHead[1]] == true){
            return -1;
         }
         //snake eats food 
         if (idx < food.length){
            if (snakeHead[0] == food[idx][0] &&
             snakeHead[1] == food[idx][1]){
                int[] head = new int[] {snakeHead[0], snakeHead[1]};
                snakeBody.addFirst(head);
                idx++;
                visited[snakeHead[0]][snakeHead[1]] =true;
                return snakeBody.size()-1;
             }
         }
         int[] head = new int[] {snakeHead[0], snakeHead[1]};
         snakeBody.addFirst(head);
         visited[snakeHead[0]][snakeHead[1]] = true;
         snakeBody.removeLast();
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
