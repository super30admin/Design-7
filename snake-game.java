import java.util.*;;

//tc of move method is O(h) which is the loop for the size of the body that can be max height or width of grid 
//sc is O(h) as we are storing the body

class SnakeGame {

    int width;
    int height;
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    int[] snakeHead;

    public SnakeGame(int width, int height, int[][] food) {

        this.width = width;
        this.height = height;
        snakeBody = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));

        snakeHead = new int[] { 0, 0 };
        snakeBody.addLast(new int[] { snakeHead[0], snakeHead[1] });
    }

    public int move(String direction) {

        if (direction.equals("U")) {
            snakeHead[0]--;
        } else if (direction.equals("D")) {
            snakeHead[0]++;
        } else if (direction.equals("L")) {
            snakeHead[1]--;
        } else if (direction.equals("R")) {
            snakeHead[1]++;
        }

        // check if game is over as snake is out of bounds
        if (snakeHead[0] < 0 || snakeHead[0] == height ||
                snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }

        // check if game is over because snake was touching its body

        for (int i = 1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }

        // check if head of the snake lies on the food item

        if (foodItems.size() > 0) {

            int[] foodItem = foodItems.get(0);
            if (foodItem[0] == snakeHead[0] && foodItem[1] == snakeHead[1]) {
                snakeBody.addLast(new int[] { snakeHead[0], snakeHead[1] });
                foodItems.removeFirst();
                return snakeBody.size() - 1;
            }

        }

        snakeBody.addLast(new int[] { snakeHead[0], snakeHead[1] });
        snakeBody.removeFirst();
        return snakeBody.size() - 1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */