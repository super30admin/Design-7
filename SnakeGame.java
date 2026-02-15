// Time Complexity : O(1)
// Space Complexity : O(W*H)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
// LinkedList to store the snake body and boolean array to check for self collision in O(1)
// When we move -> 1. check for border collision and self collision
// 2. check if we eat food
// 3. if we eat food -> add to head and mark head as visited and do not remove the tail
// 4. if we don't eat food -> remove from tail and add to head , mark new tail as not visited

public class SnakeGame {

    LinkedList<int[]> snake;
    int w, h;
    boolean[][] visited;
    int[][] food;
    int foodIdx;

    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.food = food;
        snake = new LinkedList<>();
        visited = new boolean[h][w];
        snake.addFirst(new int[] { 0, 0 });
        visited[0][0] = true;
    }

    public int move(String direction) {

        int[] head = snake.getFirst();
        int[] newHead = new int[] { head[0], head[1] };

        if (direction.equals("U"))
            newHead[0]--;
        else if (direction.equals("D"))
            newHead[0]++;
        else if (direction.equals("L"))
            newHead[1]--;
        else if (direction.equals("R"))
            newHead[1]++;

        if (newHead[0] < 0 || newHead[0] >= h || newHead[1] < 0 || newHead[0] >= w // border
                || visited[newHead[0]][newHead[1]]) // eats itself
            return -1;

        if (foodIdx < food.length) {
            if (newHead[0] == food[foodIdx][0] && newHead[1] == food[foodIdx][1]) {
                foodIdx++;
                snake.addFirst(newHead);
                visited[newHead[0]][newHead[1]] = true;
                return snake.size() - 1;
            }
        }

        snake.removeLast();
        int[] newTail = snake.getLast();
        visited[newTail[0]][newTail[1]] = false; // for async movement and out check

        snake.addFirst(newHead);
        visited[newHead[0]][newHead[1]] = true;
        return snake.size() - 1;
    }
}
