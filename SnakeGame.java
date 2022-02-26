import java.util.Arrays;
import java.util.LinkedList;

class SnakeGame {
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    int height;
    int width;

    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        this.snake = new LinkedList<>();
        this.snakeHead = new int[]{0,0};
        this.snake.addLast(snakeHead);
        this.foodList = new LinkedList<>(Arrays.asList(food));

    }

    public int move(String direction) {
        if(direction.equals("L")){
            snakeHead[1]--;
        } else if(direction.equals("R")) {
            snakeHead[1]++;
        } else if(direction.equals("U")) {
            snakeHead[0]--;
        } else {
            snakeHead[0]++;
        }

        // bound check - snake dies
        if(snakeHead[0] < 0 || snakeHead[0] == height ||
                snakeHead[1] < 0 || snakeHead[1] == width)
            return -1;

        // If snake hits its own body, it dies
        for(int i=1; i<snake.size(); i++){
            int[] curr = snake.get(i);
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1])
                return -1;
        }

        //Snake eats food
        if(foodList.size() > 0){
            int[] currFood = foodList.peek();
            if(snakeHead[0] == currFood[0] && snakeHead[1] == currFood[1]){
                int[] firstFood = foodList.removeFirst();
                snake.addLast(firstFood);
                return snake.size()-1;
            }

        }

        //remove tail from snake
        snake.removeFirst();
        //add new head
        snake.addLast(new int[]{snakeHead[0], snakeHead[1]});
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */

