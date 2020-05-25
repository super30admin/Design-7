// 353.
class SnakeGame {

    List<int[]> snake; //tracks snake's body
    Queue<int[]> food; //tracks position of food in order in which it occurs
    int[] snakeHead; //snake head
    int width; //tracks screen size
    int height;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.snake = new LinkedList<>();
        this.snakeHead = new int[]{0, 0}; //game starts with snake at (0,0) and score 0
        snake.add(snakeHead); //snake head is at tail of snake linked list
        this.width = width;
        this.height = height;
        this.food = new LinkedList<>(Arrays.asList(food));
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        
        //change sanke head to next cell based on direction
        if(direction.equals("U"))
        {
            snakeHead[0] -= 1; //up -> prev row, same col
        }
        else if(direction.equals("D"))
        {
            snakeHead[0] += 1; //up -> next row, same col
        }
        else if(direction.equals("L"))
        {
            snakeHead[1] -= 1; //up -> same row, prev col
        }
        else //direction.equals("R")
        {
            snakeHead[1] += 1; //up -> same row, next col
        }
        
        //check game over
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width)
        {
            //snake goes out of bounds 
            return -1;
        }
        for(int i = 1; i < snake.size(); i++)
        {
            if(snakeHead[0] == snake.get(i)[0] && snakeHead[1] == snake.get(i)[1])
            {
                return -1; //snake collides with itself
            }
        }
        
        //check food
        if(food.size() > 0 && food.peek()[0] == snakeHead[0] && food.peek()[1] == snakeHead[1])
        {
            //food consumed - add pos to end of list where snake head is present
            snake.add(food.poll());
            return snake.size() - 1;
        }
        
        //normal move
        snake.remove(0); //remove 1st element
        snake.add(new int[]{snakeHead[0], snakeHead[1]}); //add snake head -> new pos to last
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
