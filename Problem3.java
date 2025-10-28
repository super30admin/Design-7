/*
353. Design Snake Game
Ran on leetcode: Yes
TC: O(1)
SC: O(W * H + N) W is width, H is height and N is length of the snake
Move snake according to the direction given, and make sure if the snake died because it eats itself or is out of bounds and  if food is found increase the snake length from head and if the food is not found increaase the snake length but decrease length from the tail.
*/
class SnakeGame {

    int width;
    int height;
    int[][] food;
    int foodIdx;
    boolean[][] visited;
    List<int[]> snake;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.foodIdx = 0;
        this.visited = new boolean[height][width];
        snake = new LinkedList<>();
        snake.addFirst(new int[] { 0, 0 });
        this.visited[0][0] = true;
    }

    public int move(String direction) {
        // get the head of the snake
        int[] head = snake.getFirst();

        int r = head[0];
        int c = head[1];
        // update coordinate based on direction
        if (direction.equals("U")) {
            r--;
        } else if (direction.equals("D")) {
            r++;
        } else if (direction.equals("L")) {
            c--;
        } else if (direction.equals("R")) {
            c++;
        }

        // If out of bounds snake is dead and if snake bites itself it is dead unless it is tail
        int[] tail = snake.getLast();
        if (r < 0 || c < 0 || c >= width || r >= height)
            return -1;

        if (visited[r][c] == true && !(tail[0] == r && tail[1] == c)) {
            return -1;
        }

        // if we see food only add to head and dont remove from tail
        if (foodIdx < food.length && food[foodIdx][0] == r && food[foodIdx][1] == c) {
            snake.addFirst(new int[] { r, c });
            visited[r][c] = true;
            foodIdx++;
        } else { // if we dont see food add to head and remove from tail
            visited[tail[0]][tail[1]] = false;
            snake.removeLast();
            snake.addFirst(new int[] { r, c });
            visited[r][c] = true;
        }
        return snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */