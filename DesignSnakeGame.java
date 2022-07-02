// Time: O(Length of the snake) | Space: O(1)

class SnakeGame {
    Queue<int[]> snake;
    int[][] food;
    int width;
    int height;
    int[] head;
    int foodIndx;
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        head = new int[]{0,0};
        this.food = food;
        this.width = width;
        this.height = height;
        snake.add(head);
        foodIndx = 0;
    }

    public int move(String direction) {
        if(direction.equals("R")) head[1]++;
        else if(direction.equals("L")) head[1]--;
        else if(direction.equals("U")) head[0]--;
        else head[0]++;
        // if snake goes out of grid, it dies
        if(head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width){
            return -1;
        }
        // if the next moving pointer has food in its place,
        // we only eat the food, and tail will not be removed as snake will grow
        if(foodIndx < food.length) {
            int[] curr = food[foodIndx];
            if(head[0] == curr[0] && head[1] == curr[1]) {
                snake.add(new int[]{head[0], head[1]});
                foodIndx++;

                return snake.size()-1;
            }
        }
        boolean tailOfSnake = true;
        // checking if snake likely to touch its own body during the journey
        // if so, it dies
        for(int[] s: snake) {
            if(!tailOfSnake && head[0] == s[0] && head[1] == s[1])
                return -1;
            else
                tailOfSnake = false;
        }
        //used queue to remove tail and add head as and when the snake just moves without eating food
        snake.poll();
        snake.add(new int[]{head[0], head[1]});
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */