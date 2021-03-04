// Did it run sucessfullt on Leetcode? : Yes
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int height;
    int width;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        snake = new LinkedList();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);   //[0,0];
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    // TC : O(1)
    // SC : O(N + M) -> N: foodlist size , M: snake size
    public int move(String direction) {
        if (direction.equals("U"))
        {
            snakeHead[0]--;
        }
        if (direction.equals("D"))
        {
             snakeHead[0]++;
        }
        if (direction.equals("L"))
        {
             snakeHead[1]--;
        }
        if (direction.equals("R"))
        {
             snakeHead[1]++;
        }
        // check if snake touches the borders
        if ( snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width)
            return -1;
        for ( int i = 1; i < snake.size(); i++ )  // start from 1 because 0 would have already moved 
        {
            int[] curr = snake.get(i);
            if (snakeHead[0] == curr[0] && snakeHead[1] == curr[1])
                return -1;
        }
        // when snake eats food
        if (!foodList.isEmpty())
        {
            int[] foodPixel = foodList.peek();
            if (snakeHead[0] == foodPixel[0] && snakeHead[1] == foodPixel[1])
            {
                snake.add(foodList.remove());
                return snake.size() - 1 ;
            }
        }
        // when snake moves
        snake.remove();   //remove the tail of the snake. It is basuically head of our list ( because when snake moves, tail of snake moves)
        snake.add(new int[]{snakeHead[0],snakeHead[1]});
        return snake.size() - 1 ;
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
