// Time Complexity : O(n) // n size of the food
// Space Complexity : O(n)

class SnakeGame {

    LinkedList<int []> snake;
    LinkedList<int []> foodPath; 
    int size;
    int[] snakeHead;
    int width;
    int height;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        foodPath = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
        this.width = width; 
        this.height = height; 
    }

    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;
        if(direction.equals("L")) snakeHead[1]--;
        if(direction.equals("R")) snakeHead[1]++;
        if(direction.equals("D")) snakeHead[0]++;

        //boundary check
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0){
            return -1; 
        }

        //snake hitting itself
        for(int i = 1; i < snake.size(); i++){
            int[] curr = snake.get(i); 
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1]){
                return -1;
            }
        }

        //snake eating food
        if(!foodPath.isEmpty()){
            int[] food = foodPath.get(0); 
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]){
                snake.add(foodPath.remove());
                return snake.size() - 1;
            }
        }

        //normal move
        int[] cell = new int[]{snakeHead[0], snakeHead[1]}; 
        snake.add(cell);
        snake.remove();
        return snake.size() - 1;
    }
}