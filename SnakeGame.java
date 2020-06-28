// Time Complexity : O(number of move() calls) * O(snake length) + O(food array length)
// Space Complexity : O(n) for maintaining n nodes as linked list as snake's size
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : Hard to think such approaches
/* Algorithm: Maintain the linked list to store the length of the snake that increases once it gets the food. If food is found, add it to the linked list
and according to the direction entered in move(), move the snake accrodingly. The head of the snake is updated once it either gets food or it moves to the next
position updating its row and column number. If the snake head moves to out of the bounds or it hits itself, return -1. Else update the snake head
at each move() call. Once the food is eaten, food Linked list size decreases as the food starts disappearing.
*/
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    int h, w;
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        this.h = height;
        this.w = width;
        foodList = new LinkedList<>(Arrays.asList(food));                                               // Food array to food list linked list
        snakeHead = new int[] {0,0};
        snake.add(snakeHead);                                                                       // Add the inital snake head position
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;                                                                                   // Move the snakehead according to the direction
        if(direction.equals("D")) snakeHead[0]++;
        if(direction.equals("R")) snakeHead[1]++;
        if(direction.equals("L")) snakeHead[1]--;
        if(snakeHead[0] == h || snakeHead[1] == w || snakeHead[0] < 0 || snakeHead[1] < 0){                                         // Snake collides with boundaries
            return -1;
        }
        for(int i = 1; i < snake.size(); i++){                                                          // apart from head, the remaining body
            int[] node = snake.get(i);
            if(snakeHead[0] == node[0] && snakeHead[1] == node[1]) return -1;                             // if snake collides with itself, game over
        }
        if(foodList.size() > 0){
            int[] food = foodList.peek();
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]){                                     // snake found the food
                snake.add(foodList.remove());                                                       // Update the snake head position 
                return snake.size()-1;                                                                          // Return the total score till now
            }
        }
        snake.remove();
        snake.add(new int[]{snakeHead[0], snakeHead[1]});                                   // snake is simply moving without food, update the head position every time
        return snake.size()-1;                                                              // Return the same score till now
    }
}