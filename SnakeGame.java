class SnakeGame {

    int height, width;
    Queue<String> foodSet; 
    Set<String>snake;
    int[] snakeHead;
    int score;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {

        this.height = height;
        this.width = width;
        /*Each food appears one by one on the screen. 
        The second food will not appear until the first food was eaten by the snake.
        Using Queue to maintain the linear order and to get the food in O(1) time
        */
        foodSet = new LinkedList<>(); 
        snake = new HashSet<>(); // to store coordinates of snake.
        //snake head will start at (0,0)
        snakeHead = new int[]{0,0};
        //saving coordinates of head of the snake in the set
        snake.add(Arrays.toString(snakeHead));
        //adding food in the linked hashset in the given order
        for(int [] f : food) {
            foodSet.add(Arrays.toString(f));
        }
        score =0;
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        String oldHead = Arrays.toString(snakeHead);
        if(direction.equals("U")) snakeHead[0] -= 1;
        else if(direction.equals("D")) snakeHead[0] += 1;
        else if(direction.equals("L")) snakeHead[1] -= 1;
        else if(direction.equals("R")) snakeHead[1] += 1;
        snake.remove(oldHead);
        //snake moved in the corresponding direction so removing old head of the snake from the set
        return process(snakeHead);
    }
    private boolean hitsBoundary( int[] snakeHead){
        //checking if snake has gona out of boundary
        return snakeHead[0] >= height || snakeHead[0] < 0 || snakeHead[1] >= width || snakeHead[1] < 0;
    }
    private boolean biteSelf(int[] snakeHead){
        /*checking if snake has bitten itself.  We can check this in the hashset where we have stored snake's
        coordinates. if the current coordinates is present in the set means snake has bitten itself.*/
        return snake.contains(Arrays.toString(snakeHead)); //can check in O(1) as I am using set
    }
    private String getNextFood(){
        if(foodSet.isEmpty()) return null;
        return foodSet.peek();
    }
    private int process(int[] snakeHead){
        String newSnakeHead = Arrays.toString(snakeHead);
        if( biteSelf(snakeHead) || hitsBoundary(snakeHead)) 
             return -1;
        //snake still have food left to eat
        String nextFood = getNextFood();
        if(nextFood != null){
            //no food at this location, return 
            if(!nextFood.equals(newSnakeHead)) 
                return score;
            else{
                //snake finds food at the location it moved and eat that
                 // increase snake length
                snake.add(nextFood);
                //removing the food from the queue as snake has eaten that
                foodSet.remove();
                //incrementing score as snake got the food.
                score++;
                //System.out.println(score);
                return score;
            }
        }
        
        return (score >0) ? score : -1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
