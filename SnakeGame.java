// Time Complexity : O(n) --> where n is the number of given moves
// Space Complexity : O(n)
// Did this code successfully run on Leetcode (353): Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class SnakeGame {
    int height; int width;
    int snakeHead[];
    LinkedList<int []> snake;
    LinkedList<int []> foodList;

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
   
    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        this.snakeHead = new int[] {0,0};
        this.snake = new LinkedList<>();
        this.foodList = new LinkedList<>(Arrays.asList(food));
        snake.add(snakeHead); 
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if (direction.equals("U")) snakeHead[0] -= 1;
        if (direction.equals("D")) snakeHead[0] += 1;
        if (direction.equals("L")) snakeHead[1] -= 1;
        if (direction.equals("R")) snakeHead[1] += 1;
        
        // if the snake hits border
        if (snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) return -1;
        
        // if it eats food
        if (!foodList.isEmpty()) {
            int appearedFood[] = foodList.get(0);
            if (appearedFood[0] == snakeHead[0] && appearedFood [1] == snakeHead[1]) {
                int temp[] = foodList.remove();
                snake.add(temp); // head of snake but tail of LList
                return snake.size() - 1;                
            }
        }
        
        // what if snake hits itself
        for (int i = 1; i < snake.size(); i++) {
            int temp[] = snake.get(i);
            if (temp[0] == snakeHead[0] && temp[1] == snakeHead[1]) return -1;
        }
        
        // normal move // remove tail
        int rmTail[] = snake.remove(); // removes the tail of my snake and head of my LList
        snake.add(new int[] {snakeHead[0], snakeHead[1]}); // add to head of my snake but to tail of the list
        return snake.size() - 1;
    }
}