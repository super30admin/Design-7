
// Time - O(1)
// Spacee - O(S + F)


class SnakeGame {

    LinkedList< int []> snake;
    LinkedList< int []> foodlist;
    int width;
    int height;
    int size;
    int [] snakeHead;

    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        this.width = width;
        this.height = height;
        foodlist = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);

    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {

        if(direction.equals("U")) snakeHead[0]--;
        if(direction.equals("L")) snakeHead[1]--;
        if(direction.equals("R")) snakeHead[1]++;
        if(direction.equals("D")) snakeHead[0]++;

        // border hitting
        if(snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) return -1; // boundary conditions

        // snake hitting itself

        for(int i = 1; i < snake.size(); i++) {
            int [] curr = snake.get(i); // check the current position

            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) {
                return -1; // if it hits
            }

        }


        // snake eats food
        if(!foodlist.isEmpty()) {

            int [] foodAppear = foodlist.get(0); // get the food from the first item of foodlist element

            if(snakeHead[0] == foodAppear[0] && snakeHead[1] == foodAppear[1]) {
                snake.add(foodlist.remove()); // remove from the foodlist and add to the snake head
                return snake.size() - 1;
            }

        }

        // normal move

        int [] newCell = new int[]{snakeHead[0],snakeHead[1]};
        snake.add(newCell);
        snake.remove();
        return snake.size() - 1;

    }
}

