/*
time complexity: O(n)
space complexity : O(n), snake size
*/
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    
    LinkedList<int[]> snakeList;
    LinkedList<int[]> foodList; // to maintain food instead of traversing food array
    int width;
    int ht;
    int[]snakeHead;//initally (0,0) -> row,col
    
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.ht = height;
        this.snakeList = new LinkedList<>();
        this.foodList = new LinkedList<>(Arrays.asList(food));
        this.snakeHead = new int[]{0,0};
        this.snakeList.add(snakeHead);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")){
            this.snakeHead[0]--;
        }
        if(direction.equals("D")){
            this.snakeHead[0]++;
        }
        if(direction.equals("L")){
            this.snakeHead[1]--;
        }
        if(direction.equals("R")){
            this.snakeHead[1]++;
        }
        //check if snake touching borders
        if(this.snakeHead[0] < 0 || this.snakeHead[1] < 0 || this.snakeHead[0] == this.ht || this.snakeHead[1] == this.width){
            return -1; //dead
        }
        
        //if touching itself
        for(int i = 1; i < this.snakeList.size();i++){
            int[]curr = this.snakeList.get(i);
            if(this.snakeHead[0] == curr[0] && this.snakeHead[1] == curr[1]){
                return -1;
            }
        }
        
        //when snake eats food
        if(!foodList.isEmpty()){
            int[]curr = foodList.peek();
            
            if(this.snakeHead[0] == curr[0] && this.snakeHead[1] == curr[1]){
                snakeList.add(foodList.remove());
                
                return this.snakeList.size()-1;
            }
        }
        
        snakeList.remove();
        snakeList.add(new int[]{snakeHead[0],snakeHead[1]});
       
        return this.snakeList.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
