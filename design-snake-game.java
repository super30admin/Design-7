//Time Complexity: O(n)
//Space Complexity: O(1)
//running on leetcode:yes
class SnakeGame {
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    int[] snakeHead;
    int width, height;
    
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[] {0, 0};
        snakeBody = new LinkedList<>();
        snakeBody.add(snakeHead);
        foodItems = new LinkedList<>(Arrays.asList(food));
    }
    
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;
        else if(direction.equals("D")) snakeHead[0]++;
        else if(direction.equals("L")) snakeHead[1]--;
        else if(direction.equals("R")) snakeHead[1]++;
        
        //is it touching the boundary
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) {
            return -1;
        }
        //is the snake touching itself
        for(int i = 1; i<snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }
        //snake eats food
        if(foodItems.isEmpty()) {
            int[] foodItem = foodItems.get(0);
            if(snakeHead[0] == foodItem[0] && snakeHead[1] == foodItem[1]) {
                snakeBody.add(foodItems.remove());
                return snakeBody.size() - 1;
            }
        }
        //snake did not eat food
        snakeBody.remove();
        snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
        return snakeBody.size() - 1;
    }
}
