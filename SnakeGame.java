// Time Complexity: O(1) for each move
// Space Complexity: O(n) for the snake
import java.util.LinkedList;

class SnakeGame {

    private int[][] foodList;
    int i;
    private LinkedList<int[]> snake;
    private int[] snakeHead;
    private boolean[][] visited;
    int w, h;
    public SnakeGame(int width, int height, int[][] food) {
        this.foodList = food;
        this.w = width;
        this.h = height;
        this.snake = new LinkedList<>();
        this.snakeHead = new int[]{0, 0};
        this.snake.addFirst(snakeHead);
        this.visited = new boolean[height][width];
    }

    public int move(String direction) {
        if(direction.equals("R")){
            snakeHead[1]++;
        }else if(direction.equals("L")){
            snakeHead[1]--;
        }else if(direction.equals("D")){
            snakeHead[0]++;
        }else{
            snakeHead[0]--;
        }

        // border hit
        if(snakeHead[0] < 0 || snakeHead[0] == h || snakeHead[1] < 0 || snakeHead[1] == w){
            return -1;
        }
        // hits itself
        if(visited[snakeHead[0]][snakeHead[1]]){
            return -1;
        }

        // if food move
        if(i < foodList.length){
            int[] currFood = foodList[i];
            if(currFood[0] == snakeHead[0] && currFood[1] == snakeHead[1]){
                i++;
                this.snake.addFirst(new int[]{snakeHead[0], snakeHead[1]});
                visited[snakeHead[0]][snakeHead[1]] = true;
                return snake.size() - 1;
            }
        }

        // normal move - remove the tail
        // head
        this.snake.addFirst(new int[]{snakeHead[0], snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;

        // tail
        this.snake.removeLast();
        int[] newTail = snake.getLast();
        visited[newTail[0]][newTail[1]] = false;
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
