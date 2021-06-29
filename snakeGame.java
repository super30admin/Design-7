// Time Complexity : O(N) N = number of moves
// Space Complexity : O(Width * Height)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    int width;
    int height;
    int[] snakeHead;
    LinkedList<int []> snake;
    
    LinkedList<int[]> foodItems;
    
    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        this.snakeHead = new int[]{0,0};
        this.snake = new LinkedList<>();
        //this.snakeSet = new HashSet<>();
        this.foodItems = new LinkedList<>(Arrays.asList(food));
        this.snake.addLast(snakeHead);
        //this.snakeSet.add(snakeHead);
        
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        
        
        switch(direction){
            case "U": snakeHead[0]--; break;
            case "D": snakeHead[0]++; break;
            case "L": snakeHead[1]--; break;
            case "R": snakeHead[1]++; break;
        }
        // Snake touches the boundaries
        if(snakeHead[0]<0 || snakeHead[0]>=height || snakeHead[1]<0 || snakeHead[1]>=width){
            return -1;
        }
        // Snake touches itself
        for(int i = 1 ; i < snake.size() ; i++){
            int[] temp = snake.get(i);
            if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]) return -1;;
        }
      
        // Snake eats food item;
        if(!foodItems.isEmpty() && Arrays.equals(new int[]{snakeHead[0],snakeHead[1]},foodItems.getFirst())){
                foodItems.removeFirst();
        }else{
            snake.removeFirst();    
        }
        snake.addLast(new int[]{snakeHead[0],snakeHead[1]});
        
                      
        
        return snake.size()-1;
        
    }
}

