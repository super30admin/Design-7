//Time Complexity : O(n)
//Space Complexity : O(n)
//Did this code successfully run on Leetcode :yes
//Any problem you faced while coding this : no
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    LinkedList<int[]> snake;
    LinkedList<int[]> food;
    int width;
    int height;
    int[] snakeHead;
    
    public SnakeGame(int width, int height, int[][] food) {
        
        this.width = width;
        this.height = height;
        this.food = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        this.snake = new LinkedList<>();
        snake.add(snakeHead);
        
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        
        //checking for directions
        
        if(direction.equals("U")){
            
           snakeHead[0]--; 
        }
        if(direction.equals("L")){
            
           snakeHead[1]--; 
        }
        if(direction.equals("R")){
            
           snakeHead[1]++; 
        }
        if(direction.equals("D")){
            
           snakeHead[0]++; 
        }
        
        //checking for boundary conditions
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width ){
            
            return -1;
        }
        //checking if hitting self
        for(int i = 1; i < snake.size(); i ++){
            
            int[] temp = snake.get(i);
            if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]  ){
                
                return -1;
            }
        }
        
        
        //checking for food
        if(food.size() > 0){
            
            int[] currFood = food.get(0);
            if(currFood[0] == snakeHead[0] && currFood[1] == snakeHead[1]){
                
                snake.add(food.remove());
                   return snake.size() - 1;
            }
            
            
        }
 
        
        //checking if no food
            snake.remove();
            snake.add(new int[]{snakeHead[0],snakeHead[1]});
            
        
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */