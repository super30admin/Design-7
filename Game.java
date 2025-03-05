import java.util.LinkedList;

// TC: O(1) as based on the given direction, the snake
//  is just navigating to its adjacent direction if valid and making changes accordingly to the visited and list
// SC: O(n) as visited array and LinkedList for storing snakebody is used 
public class Game {

    // Declaring varibles and data structure
    int height;
    int width;
    int[][] food;
    int idx;
    boolean visited[][];
    int[] snakeHead;
    LinkedList<int[]> snakeBody;

    public Game(int height, int width, int[][] food) {
        // Initializing variables and data structure
        this.height = height;
        this.width = width;
        this.food = food;
        idx = 0;
        this.visited = new boolean[height][width];
        this.snakeHead = new int[] { 0, 0 };
        this.snakeBody = new LinkedList<>();
        this.snakeBody.addFirst(new int[] { 0, 0 });
    }

    public int move(String direction) {
        // U D L R
        // Navigate directions
        if (direction.equals("U"))
            snakeHead[0]--;
        else if (direction.equals("D"))
            snakeHead[0]++;
        else if (direction.equals("L"))
            snakeHead[1]--;
        else if (direction.equals("R"))
            snakeHead[1]++;

        // check for edge case like self body touching and boundaries
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width)
            return -1;
        if (visited[snakeHead[0]][snakeHead[1]])
            return -1;
        // if idx < food.length handle the food cell
        // else handle the normal cell
        if (idx < food.length) {
            if (snakeHead[0] == food[idx][0] && snakeHead[1] == food[idx][1]) {
                int head[] = new int[] { snakeHead[0], snakeHead[1] };
                snakeBody.addFirst(head);
                visited[head[0]][head[1]] = true;
                idx++;
                return snakeBody.size() - 1;
            }
        }
        int head[] = new int[] { snakeHead[0], snakeHead[1] };
        visited[snakeHead[0]][snakeHead[1]] = true;
        snakeBody.addFirst(head);
        snakeBody.removeLast();
        int tail[] = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size() - 1;
    }
}

class SnakeGame {
    public static void main(String[] args) {
        // [[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
        Game obj = new Game(2, 3, new int[][] { { 1, 2 }, { 0, 1 } });
        System.out.println(obj.move("R")); // 0
        System.out.println(obj.move("D")); // 0
        System.out.println(obj.move("R")); // 1
        System.out.println(obj.move("U")); // 1
        System.out.println(obj.move("L")); // 2
        System.out.println(obj.move("U")); // -1
    }

}
