public class SnakeGame {


    /**
     * Time complexity: O(N) where N is size of snake
     * Space complexity: O(N) where N is size of snake
     * 
     * Approach:
     * 1. Maintain snake as linkedlist with it's tail as snake's head;
     * 2. For every move snake makes, check if it crosses the boundary or eats itself and return -1.
     * 3. Else if its head position is same as food position, add new head in the snake list and return the score 
     * as snake's size - 1.
     */

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    int width;
    int height;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        snakeHead = new int[]{0, 0};
        snake.add(snakeHead);
        
        foodList = new LinkedList<>(Arrays.asList(food));
     }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        
        if(direction.equals("U")){
            snakeHead[0]--;
        }
        else if(direction.equals("D")){
            snakeHead[0]++;
        }
        else if(direction.equals("L")){
            snakeHead[1]--;
        }
        else if(direction.equals("R")){
            snakeHead[1]++;
        }
        
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 
          || snakeHead[1] >= width){
            return -1;
        }
        
        for(int i=1; i<snake.size(); i++) {
            int[] temp = snake.get(i);
            if(snakeHead[0] == temp[0] && snakeHead[1] == temp[1])
                return -1;
        }
        
        if(foodList.size() > 0) {
            int[] foodPos = foodList.get(0);
            if(foodPos[0] == snakeHead[0] && foodPos[1] == snakeHead[1]){
                snake.add(foodList.remove());
                return snake.size()-1;
            }
            
        }
        
        snake.remove();
        snake.add(new int[]{snakeHead[0], snakeHead[1]});
        return snake.size()-1;
        
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */