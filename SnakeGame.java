class SnakeGame {
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. 
*/
    LinkedList<int []> snake;
    LinkedList<int []> foodList;
    int[] snakeHead;
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
    }

    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body. */

    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;
        if(direction.equals("D")) snakeHead[0]++;
        if(direction.equals("L")) snakeHead[1]--;
        if(direction.equals("R")) snakeHead[1]++;

        //snake hits wall
        if(snakeHead[1] < 0 || snakeHead[1] == width || snakeHead[0] < 0 || snakeHead[0] == height)
            return -1;

        //snake hits itself
         for(int i = 1; i < snake.size(); i++){
            int[] currCell = snake.get(i);
            if(currCell[0] == snakeHead[0] && currCell[1] == snakeHead[1])
                return -1;
        }

        //eats food
        if(!foodList.isEmpty()){
            int[] appearedFood = foodList.getfirst();
            if(snakeHead[0] == appearedFood[0] && snakeHead[1] == appearedFood[1]){
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }

        //normal move in the grid
        snake.add(new int[]{snakeHead[0], snakeHead[1]});
        snake.remove();
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */

