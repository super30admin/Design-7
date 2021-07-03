//time complexity not sure if we discuss this in design problems
//space complexity not sure

class SnakeGame {
    int width; int height;
    LinkedList<int[]> snake;//linked list contains array of row and column eg. [1,1]
    LinkedList<int[]> foodList;//same as above
    int[] snakeHead;//this is what moves and is appended to the tail of the linkedlist

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {//initializing the game constructor
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        snakeHead = new int[]{0,0};//starting point of snake when the game starts
        snake.add(snakeHead);//add snakeHead to the end of the list and snake size is 0 currently because no food eaten
        foodList = new LinkedList<>(Arrays.asList(food));
    }

    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0] -= 1;
        if(direction.equals("D")) snakeHead[0] += 1;
        if(direction.equals("L")) snakeHead[1] -= 1;
        if(direction.equals("R")) snakeHead[1] += 1;
        //check is the snake has hit the border
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) return -1;

        //if snake has hit itself
        for(int i = 1; i < snake.size(); i++){
            int[] temp = snake.get(i);
            if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]) return -1;
        }

        //if it eats food
        if(!foodList.isEmpty()){
            int[] temp = foodList.get(0);
            if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]) {
                snake.add(foodList.remove());
                return snake.size() - 1;
            };
        }

        //normal moving
        snake.remove();//remove from head of the linkedList which actually is the tail of snake
        snake.add(new int [] {snakeHead[0], snakeHead[1]});//now as the head moves and changes direction, update
        return snake.size() - 1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
