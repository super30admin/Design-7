class SnakeGame {
    LinkedList<int[]> snake; // we take one linkedlist for snakes body
    LinkedList<int[]> foodItems; // we take one linkedlist for food items
    int[] snakeHead; // we take an integer array which is snakes head;
    int width, height; //we initialize variables for width and height;

    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>(); // we are initializing the snake linkedlist.
        foodItems = new LinkedList<>(Arrays.asList(food)); //we are initializing the food linkedlist to which we pass the integer array as the food
        snakeHead = new int[] {0, 0}; //we initialize the snakehead to 0,0 initially.
        snake.add(snakeHead); // we are adding the snake's head to the snake
        this.width = width; // we bring the value of width to the constructor
        this.height = height; // we bring the value of height to the constructor
    }

    public int move(String direction) {
        if(direction.equals("U")) { // if the snake is going in upward direction
            snakeHead[0]--; //row is decreased
        }
        else if(direction.equals("D")) { //if the snake is going in downward direction
            snakeHead[0]++; //row is increased
        }
        else if(direction.equals("L")) { //if the snake is going in left direction
            snakeHead[1]--; //col is decreased
        }
        else if(direction.equals("R")) { //if the snake is going in right direction
            snakeHead[1]++; //col is increased
        }

        // we are checking if snake touches the boundary
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width){
            return -1; //then we return -1
        }
        //we are checking if snake touches itself
        for(int i = 1; i < snake.size(); i++) { // we go through the size of the snake starting from 1 index as 0 is considered as the tail
            int[] curr = snake.get(i); //we add the value that we get at i to the curr int array
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) { //if snake's head is touching its body
                return -1; //then we return -1
            }
        }

        //we are checking if snake touches a food item;
        if(foodItems.size() > 0) { // if size of fooditems is greater than 0 that means if there are any food items present in the grid
            int[] food = foodItems.peek(); //we store the location of the foodItems in the integer array with name food
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]) { // if the snake eats the food
                snake.add(new int[] {snakeHead[0], snakeHead[1]}); // we add a newhead to the snake as to where the food is eaten
                foodItems.remove(); // and we remove the fooditem
                return snake.size() - 1; //we return the snake size
            }
        }
        //if we dont find food;
        snake.add(new int[] {snakeHead[0], snakeHead[1]}); //we move the snake
        snake.remove(); //we remove the tail
        return snake.size() - 1; //we return the size of the snake
    }
}
//tc and sc - O(m*n)