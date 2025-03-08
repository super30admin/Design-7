class SnakeGame {
  
    int width;
    int height;
    int[][] food;
  
    int[] snakeHead;
    
    java.util.LinkedList<int[]> snakeBody;
   
    boolean[][] visited;
    
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        // Initialize them
        this.width = width;
        this.height = height;
        this.food = food;
       
        snakeHead = new int[] { 0, 0 };
       
        snakeBody = new java.util.LinkedList<>();
       
        snakeBody.addFirst(new int[] { 0, 0 });
        
        this.visited = new boolean[height][width];
       
        visited[0][0] = true;
        
        idx = 0;
    }

    public int move(String direction) {
       
        if (direction == "U") {
            snakeHead[0]--;
        }
      
        else if (direction == "D") {
            snakeHead[0]++;
        }
       
        else if (direction == "L") {
            snakeHead[1]--;
        }
       
        else if (direction == "R") {
            snakeHead[1]++;
        }
      
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
           
            return -1;
        }
      
        if (visited[snakeHead[0]][snakeHead[1]] == true) {
          
            return -1;
        }
      
        if (idx < food.length) {
            if (snakeHead[0] == food[idx][0] && snakeHead[1] == food[idx][1]) {
                
                idx++;
              
                int[] head = new int[] { snakeHead[0], snakeHead[1] };
                snakeBody.addFirst(head);
               
                visited[snakeHead[0]][snakeHead[1]] = true;
                
                return snakeBody.size() - 1;
            }
        }
       
        int[] head = new int[] { snakeHead[0], snakeHead[1] };
       
        snakeBody.addFirst(head);
       
        int[] tail = snakeBody.getLast();
       
        visited[tail[0]][tail[1]] = false;
       
        snakeBody.removeLast();
       
        visited[snakeHead[0]][snakeHead[1]] = true;
        
        return snakeBody.size() - 1;
    }

   
}