// Time, Space - O(N), O(N)

class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    int width, height;
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));        
        snakeHead = new int[] { 0,0};
        this.width = width;
        this.height = height;
        snake.add(snakeHead);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        }
        else if (direction.equals("L")) {
            snakeHead[1]--;
        }
        else if (direction.equals("R")) {
            snakeHead[1]++;
        }
        else if (direction.equals("D")) {
            snakeHead[0]++;
        }
        
        
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] >= width || snakeHead[1] < 0) { 
          return -1;
        }
        
        for(int i=1;i<snake.size();i++) {
            int[] cur = snake.get(i);
            if(cur[0] == snakeHead[0] && cur[1] == snakeHead[1]) {
                return -1;
            }
        }
        
        if(!foodList.isEmpty()) {
            int[] cur = foodList.get(0);
            if(cur[0] == snakeHead[0] && cur[1] == snakeHead[1]) { 
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }
        
        snake.remove();
        snake.add(new int[] {snakeHead[0], snakeHead[1]});
        return snake.size() - 1;
    }
}
