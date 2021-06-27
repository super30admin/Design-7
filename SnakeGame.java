//TC : move - O(n)
//SC : O(n)
class SnakeGame {
    int width,height;
    int[] snakeHead ;
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList ;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[]{0,0};
        snake = new LinkedList<>();
        snake.add(snakeHead);
        foodList = new LinkedList<>(Arrays.asList(food));
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        
        if(direction.equals("U")) snakeHead[0]-=1;
        else if(direction.equals("D")) snakeHead[0]+=1;
        else if(direction.equals("L")) snakeHead[1]-=1;
        else if(direction.equals("R")) snakeHead[1]+=1;
        
       //Snake hits borders
        if(snakeHead[0]<0 || snakeHead[0]>=height || snakeHead[1]<0 || snakeHead[1]>=width)
            return -1;
        
        //Snake hits itself
        for(int i=1;i<snake.size();i++){
            int[] temp = snake.get(i);
            if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1])
                return -1;
       
        }
        
        //Snake eats food
        if(!foodList.isEmpty()){
            int[] food = foodList.get(0);
            if(food[0] == snakeHead[0] && food[1] == snakeHead[1]){
                snake.add(foodList.remove());
                return snake.size()-1;
            }
                
        }
        
        //Normal move
        snake.remove();
        snake.add(new int[]{snakeHead[0],snakeHead[1]});
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */