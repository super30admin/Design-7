// Time complexity: O(n) - where n is the length of the snake - to check if snake head is hitting with body
// Space complexity: O(n) - snake length for snake body list
// Approach: create an array of list for snake body to check if the new head is already in snakebody.
// whenever there is food that matches with current snake head, add the indices to snake body.
// If no food matching with indices, add this to snake body and remove the tail, which is at index 0 of snake body to move the snake forward
// Check for boundary cases to return -1 and check if snake touches its own body and return -1

class SnakeGame {

    int width, height;
    LinkedList<int[]> foodItems;
    LinkedList<int[]> snakeBody;
    int[] snakeHead;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;

        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0, 0};
        snakeBody = new LinkedList<>();
        snakeBody.add(snakeHead);
    }

    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        } else if(direction.equals("D")) {
            snakeHead[0]++;
        } else if(direction.equals("R")) {
            snakeHead[1]++;
        } else if(direction.equals("L")) {
            snakeHead[1]--;
        }

        //check if it's touching the boundaries
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }

        //Check if the snake is touching itself
        for(int i = 1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }

        //if the food is present, the snake should eat it and return score
        if(!foodItems.isEmpty()) {
            int[] appearedFood = foodItems.get(0);
            if(snakeHead[0] == appearedFood[0] && snakeHead[1] == appearedFood[1]) {
                snakeBody.add(new int[]{snakeHead[0], snakeHead[1]});
                foodItems.remove();
                return snakeBody.size()-1;
            }
        }

        //remove the tail from the snake body as it has moved ahead
        snakeBody.remove();
        //add the snake head to the snake body
        snakeBody.add(new int[]{snakeHead[0], snakeHead[1]});
        //return the score
        return snakeBody.size()-1;
    }

}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */