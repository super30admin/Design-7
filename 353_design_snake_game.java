    /*  Explanation
    # Leetcode problem link : https://leetcode.com/problems/design-snake-game/
    Time Complexity for operators : o(n2) .. n is the length of the array
    Extra Space Complexity for operators : o(n)
    Did this code successfully run on Leetcode : NA
    Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
        # Basic approach : 
        # Optimized approach: 
                              
            # 1. 
                    A) Initialize the variables width, height, food and snake.
                    B) Also, intialize head with (0,0) value and add it to snake list. Snake is tarting from this point.
                    C) Now in move function, move the head according to the input string 
                    D) Then, check if move is out of bound. If it is then return -1;
                    E) After that check if it is colliding with itself. traverse through snake linkedlist and check the
                       head pointer matches it if it matches then it is colliding and return -1.
                    F) If food is eaten, then add it to the snake list and remove from food list. Return size()-1.
                    G) At the regular move, remove from snake start and add tp end with head pointers.
                    H) At the ed, return size()-1
    */
class SnakeGame {

    
    private int width;
    private int height;
    private LinkedList<int[]> food;
    private LinkedList<int[]> snake;
    
    private int[]  head;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        
        this.snake = new LinkedList<>(); 
        this.food = new LinkedList<>(Arrays.asList(food));
        
        this.head = new int[]{0, 0};
        this.snake.add(this.head);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        
        // move head
        if(direction.equals("U")){
            head[0] -= 1;
        }else if(direction.equals("D")){
            head[0] += 1;
        }else if(direction.equals("R")){
            head[1] += 1;
        }else{
            head[1] -= 1;
        }
        
        // check if head is out of bound
        if(head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width){
            return -1;
        }
        // self loop
        for(int i = 1;i < snake.size();i++){
            int curr[] = snake.get(i);
            
            if(curr[0] == head[0] && curr[1] == head[1]){
                return -1;
            }
        }
        
        // food is eaten
        if(!food.isEmpty()){
            int curr_food[] = food.get(0);
            
            if(curr_food[0] == head[0] && curr_food[1] == head[1]){
                snake.add(food.remove());
                
                return snake.size() - 1;
            }
        }
        
        // regualerly move of snake
        snake.remove();
        snake.add(new int[]{head[0], head[1]});
        
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */