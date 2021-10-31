package Design7;

import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {
    LinkedList<int[]> snake = new LinkedList<>();
    int width, height;
    int[][] food;
    int foodIdx = 0;
    int score = 0;
    int newHead[] = new int[]{0,0};
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        snake.add(new int[]{0, 0});
    }

    public int move(String direction) {
        int[] dirArr;
        if(direction.equals("U"))
            dirArr = new int[]{-1, 0};
        else if(direction.equals("D"))
            dirArr = new int[]{1, 0};
        else if(direction.equals("L"))
            dirArr = new int[]{0, -1};
        else
            dirArr = new int[]{0, 1};
        int newR = dirArr[0] + newHead[0];
        int newC = dirArr[1] + newHead[1];
        newHead = new int[]{newR, newC};
        if(newR >= height || newR < 0 || newC >= width || newC < 0)
            return -1;
        for(int i=1; i<snake.size()-1;i++){
            int[] curr = snake.get(i);
            if(curr[0] == newR && curr[1] == newC)
                return -1;
        }
        snake.add(new int[]{newR, newC});
        if(foodIdx >= food.length || food[foodIdx][0] != newR || food[foodIdx][1] != newC){
            snake.remove();
        } else{
            ++score;
            ++foodIdx;
        }
        return score;
    }

    public static void main(String[] args) {
        SnakeGame s = new SnakeGame(3, 3, new int[][]{{2,0},{0,0},{0,2},{2,2}});
        System.out.println(s.move("D"));
        System.out.println(s.move("D"));
        System.out.println(s.move("R"));
        System.out.println(s.move("U"));

        System.out.println(s.move("U"));
        System.out.println(s.move("L"));
        System.out.println(s.move("D"));
        System.out.println(s.move("R"));

        System.out.println(s.move("R"));
        System.out.println(s.move("U"));
        System.out.println(s.move("L"));
        System.out.println(s.move("D"));
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
