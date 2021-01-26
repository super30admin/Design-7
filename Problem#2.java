// #LC 353 Design Snake Game   #S30 128

class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    
    LinkedList <int[]> snake;
    LinkedList <int[]> foodList;
    int width, height;
    int[] snakeHead;
    
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        this.width = width;
        this.height = height;
        snakeHead = new int[] {0, 0};
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
        if(direction.equals("D")){
            snakeHead[0]++;
        }
        if(direction.equals("L")){
            snakeHead[1]--;
        }
        if(direction.equals("R")){
            snakeHead[1]++;
        }
        
        //when snake touches the border 
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) {
            return -1;
        }
        
        // touch itself
        for(int i = 1; i < snake.size(); i++) {
            int[] curr = snake.get(i); 
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1] ) {
                return -1;
            }
        }
        
        if(!foodList.isEmpty()) {
            int[] curr = foodList.get(0);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) {
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }
        snake.remove();
        snake.add(new int[] {snakeHead[0], snakeHead[1]});
        return snake.size() -1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */