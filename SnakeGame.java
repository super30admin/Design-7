import java.util.*;

// TC: O(1) for each move
// SC: O(N + Width * Height) 
// where n is the number of food items and width and height are the dimensions of the grid

public class SnakeGame {

    int score;
    int height;
    int width;
    int food[][];
    int foodPtr;
    HashSet<Integer> visited;
    Deque<Integer> snake;

    int dir[][] = {{0,-1},{-1,0},{0,1},{1,0}};

    public SnakeGame(int width, int height, int[][] food) {
        this.score = 0;
        this.width = width;
        this.height = height;
        this.visited = new HashSet<>();
        this.food = food;
        this.foodPtr = 0;
        this.snake = new LinkedList<>();
        snake.offerFirst(0);
        visited.add(0);
    }
    
    public int move(String direction) {
        int x = snake.peekFirst();
        int[] head = new int[]{x/width, x%width};
        if(direction.equals("L")){
            head[0]+=dir[0][0];
            head[1]+=dir[0][1];
        } else if(direction.equals("U")){
            head[0]+=dir[1][0];
            head[1]+=dir[1][1];
        } else if(direction.equals("R")){
            head[0]+=dir[2][0];
            head[1]+=dir[2][1];
        } else {
            head[0]+=dir[3][0];
            head[1]+=dir[3][1];
        }
        if(head[0]<0 || head[1]<0 || head[0]>=height || head[1]>=width){
            return -1;
        }
        snake.offerFirst(head[0] * width + head[1]);
        int y = snake.peekFirst();
        int newHead[] = new int[]{y/width, y%width};
        if(foodPtr<food.length && newHead[0]==food[foodPtr][0] && newHead[1]==food[foodPtr][1]){
            score++;
            foodPtr++;
        } else {
            int z = snake.pollLast();
            int[] polledTail = new int[]{z/width, z%width};
            visited.remove(polledTail[0] * width + polledTail[1]);
            if(visited.contains(newHead[0] * width + newHead[1])){
                return -1;
            }
        }
        visited.add(newHead[0] * width + newHead[1]);
        return score;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
