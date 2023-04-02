import java.util.LinkedList;
/*
Snake Game
approach: keep track of snake path, size, food pointer
time: O(1)
space: O(snake size)
 */
public class Snake {
    int size, width, height;
    LinkedList<int[]> snakePath;
    int[] snakeHead;
    int[][] food;
    int foodPointer;
    boolean[][] visited;
    public Snake(int width, int height, int[][] food) {
        this.foodPointer = 0;
        this.food = food;
        this.width = width;
        this.height = height;
        this.snakePath = new LinkedList<>();
        this.size = 1;
        this.visited = new boolean[this.height][this.width];
        visited[0][0] = true;
        this.snakeHead = (new int[]{0,0});
        this.snakePath.addLast(new int[]{0,0});
    }

    private int move(String direction) {
        if (direction.equals("U")) {
            snakeHead[0]--;
        } else if (direction.equals("L")) {
            snakeHead[1]--;
        } else if (direction.equals("R")) {
            snakeHead[1]++;
        } else if (direction.equals("D")) {
            snakeHead[0]++;
        }

        if (snakeHead[0]<0 || snakeHead[1]<0 || snakeHead[0]==height || snakeHead[1]==width || visited[snakeHead[0]][snakeHead[1]]) {
            return -1;
        }
        if (foodPointer<food.length)
        if (snakeHead[0]==food[foodPointer][0] && snakeHead[1]==food[foodPointer][1]) {
            snakePath.addLast(new int[]{snakeHead[0], snakeHead[1]});
            visited[snakeHead[0]][snakeHead[1]] = true;
            size++;
            foodPointer++;
            return size-1;
        }
        snakePath.addLast(new int[]{snakeHead[0], snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        int[] tail = snakePath.get(0);
        snakePath.removeFirst();
        visited[tail[0]][tail[1]] = false;
        return size-1;
    }

    public static void main(String[] args) {
        Snake snake = new Snake(3, 2, new int[][]{{1,2},{0,1}});
        System.out.println(snake.move("R"));
        System.out.println(snake.move("D"));
        System.out.println(snake.move("R"));
        System.out.println(snake.move("U"));
        System.out.println(snake.move("L"));
        System.out.println(snake.move("U"));
    }
}
