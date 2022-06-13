// Time Complexity: O(m * n) where m is height and n is width.
// SC: O(m * n) where m is height and n is width.

class SnakeGame {
    LinkedList<int[]> snakeBody;    // [int, int] -> [int, int]
    int width, height;
    LinkedList<int[]> foodItems;
    int[] snakeHead;
    
    public SnakeGame(int width, int height, int[][] food) {
        snakeBody = new LinkedList<>();
        snakeHead = new int[] {0, 0};
        snakeBody.add(snakeHead);
        foodItems = new LinkedList<>(Arrays.asList(food));
        this.width = width;
        this.height = height;
    }
    
    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        }
        else if(direction.equals("D")) {
            snakeHead[0]++;
        }
        else if(direction.equals("L")) {
            snakeHead[1]--;
        }
        else if(direction.equals("R")) {
            snakeHead[1]++;
        }
        // Check if snake touches the edges.
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) {
            return -1;
        }
        // Does the snake touch itself.
        for(int i = 1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) {    // Head and body at the same coordinates.
                return -1;
            }
        }
        // Snake eats food.
        if(foodItems.size() > 0) {
            int[] food = foodItems.get(0);   // Food item at the head of the list.
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]) {
                snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
                foodItems.remove();
                return snakeBody.size() - 1;        // Score
            }
        }
        // Snake moved ahead
        snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
        snakeBody.remove();
        return snakeBody.size() - 1;
    }
}