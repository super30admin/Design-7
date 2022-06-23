// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

class SnakeGame {

    int width, height;
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    int[] snakeHead;
    
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        
        snakeBody = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[] {0, 0};
        snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
    }
    
    public int move(String direction) {
        
        if(direction.equals("U")) {
            snakeHead[0]--;
        }
        if(direction.equals("R")) {
            snakeHead[1]++;
        }
        if(direction.equals("D")) {
            snakeHead[0]++;
        }
        if(direction.equals("L")) {
            snakeHead[1]--;
        }
        
        // snake touches wall
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) {
            return -1;
        }
        
        // snake touches itself
        for(int i = 1; i < snakeBody.size(); i++) {
            int[] body = snakeBody.get(i);
            
            if(body[0] == snakeHead[0] && body[1] == snakeHead[1]) {
                return -1;
            }
        }
        // snakeBody.remove();
        
        // if there is food at that place
        if(foodItems.size() > 0) {
            int[] food = foodItems.get(0);
            
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]) {
                snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
                foodItems.remove();
                return snakeBody.size() - 1;
            }
        }
        snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
        snakeBody.remove();
        
        return snakeBody.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */