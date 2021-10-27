public class SnakeGame {

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int height; int width;
    int[] snakeHead;

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.snakeHead = new int[]{0,0};
        this.snake = new LinkedList<>();
        snake.add(snakeHead);
        this.foodList = new LinkedList<>(Arrays.asList(food));
    }

    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0] = snakeHead[0]-1;
        else if(direction.equals("D")) snakeHead[0] = snakeHead[0]+1;
        else if(direction.equals("R")) snakeHead[0] = snakeHead[1]+1;
        else if(direction.equals("L")) snakeHead[0] = snakeHead[1]-1;

        if(snakeHead[0]<0 || snakeHead[0]==height || snakeHead[1]<0 || snakeHead[1]==width) return -1;
        for(int i = 0; i< snake.size(); i++) {
            int[] curr = snake.get(i);
            if(curr[0]==snakeHead[0] && curr[1]==snakeHead[1]) return -1;
        }

        // eat food
        if(foodList.size()>0) {
            int[] food = foodList.peek();
            if(food[0]==snakeHead[0] && food[1]==snakeHead[1]){
                foodList.remove();
                snake.add(new int[]{snakeHead[0], snakeHead[1]});
                return snake.size();
            }
        }

        // not eat food
        snake.remove();
        snake.add(new int[] {snakeHead[0], snakeHead[1]});
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */