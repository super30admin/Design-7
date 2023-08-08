//TC : O(n)
//SC : O(n)

class SnakeGame {
    LinkedList<int[]> snakeBody;
    int[] snakeHead;
    LinkedList<int[]> foodItems;
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[] { 0, 0 };
        snakeBody = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeBody.add(snakeHead);
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
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            return -1;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }
        if (!foodItems.isEmpty()) {
            int[] appearedFood = foodItems.get(0);
            if (snakeHead[0] == appearedFood[0] && snakeHead[1] == appearedFood[1]) {
                snakeBody.add(new int[] { snakeHead[0], snakeHead[1] });
                foodItems.remove();
                return snakeBody.size() - 1;
            }

        }
        snakeBody.remove();
        snakeBody.add(new int[] { snakeHead[0], snakeHead[1] });
        return snakeBody.size() - 1;

    }
}