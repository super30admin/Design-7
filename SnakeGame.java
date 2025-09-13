
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class SnakeGame {
    int w, h;
    int index;
    List<int[]> food;
    LinkedList<int[]> snakeBody;
    int[] snakeHead;
    boolean[][] visited;
    
    public SnakeGame(int weight, int height, int[][] food) {
        this.w = weight;
        this.h = height;
        this.food = Arrays.asList(food);
        snakeHead = new int[] {0,0};
        snakeBody = new LinkedList<>();
        snakeBody.addFirst(snakeHead);
        index = 0;
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
        if (snakeHead[0] < 0 || snakeHead[0] == h || snakeHead[1] < 0 || snakeHead[1] == w) {
            return -1;
        }
        if(visited[snakeHead[0]][snakeHead[1]]) {
            return -1;
        }

        if (index < food.size()) {
            int[] foodItem  = food.get(index);
            if (snakeHead[0] == foodItem[0] && snakeHead[1] == foodItem[1]) {
                index++; // move to next foodItem only if meeting current food.
                snakeBody.addFirst(new int[] {snakeHead[0], snakeHead[1]});
                visited[snakeHead[0]][snakeHead[1]] = true;
                // do not remove anything form snakebody since meetign food, will just increase its length
                return snakeBody.size() - 1; // -1 because nitially we start with 0,0 but the score should have been 0
            }
        }
        snakeBody.addFirst(new int[] {snakeHead[0], snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        int[] last = snakeBody.getLast();
        visited[last[0]][last[1]] = false;
        snakeBody.removeLast();
        return snakeBody.size() - 1;
    }
    
}