//Time Complexity :o(N)
//Space Complexity : o(N)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int width;
    int height;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
    }

    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) {
            snakeHead[0]--;
        }
        if(direction.equals("D")) {
            snakeHead[0]++;
        }
        if(direction.equals("L")) {
            snakeHead[1]--;
        }
        if(direction.equals("R")) {
            snakeHead[1]++;
        }

        //Hits wall
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) {
            return -1;
        }

        //Hits itself
        for(int i = 1; i < snake.size(); i++) {
            int[] current = snake.get(i);
            if(current[0] == snakeHead[0] && current[1] == snakeHead[1]) {
                return -1;
            }
        }

        //Eats Food
        if(!foodList.isEmpty()) {
            int[] appearedFood = foodList.get(0);
            if(snakeHead[0] == appearedFood[0] && snakeHead[1] == appearedFood[1]) {
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }

        //Normal Move
        int[] newCell = new int[]{snakeHead[0],snakeHead[1]};
        snake.add(newCell);
        snake.remove();
        return snake.size() - 1;
    }

}