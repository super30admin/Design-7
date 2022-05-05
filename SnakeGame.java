//Time Complexity O(1)
//Space Complexity O(N)
//Leetcode tested

import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int head[];
    int width,height;
    public SnakeGame(int width,int height,int[][] food){
        this.width = width;
        this.height = height;
        this.head = new int[]{0,0};
        this.snake = new LinkedList<>();
        this.foodList = new LinkedList<>(Arrays.asList(food));
        this.snake.addLast(this.head);
    }

    public int move(String direction){
        if(direction.equals("L")) head[1]--;
        else if(direction.equals("R")) head[1]++;
        else if(direction.equals("U")) head[0]--;
        else head[0]++;

        if(head[0] < 0 || head[0] == height || head[1] < 0 || head[1] == width) return -1;

        for (int i = 1; i < snake.size(); i++) {
            int[] curr = snake.get(i);
            if(curr[0] == head[0] || curr[1] == curr[1]) return -1;
        }

        if(!foodList.isEmpty()){
            int[] curr = foodList.get(0);
            if(curr[0] == head[0] || curr[1] == curr[1]){
                foodList.removeFirst();
                snake.addLast(new int[]{head[0],head[1]});
                return snake.size()-1;
            }
        }

        snake.removeFirst();
        snake.addLast(new int[]{head[0],head[1]});
        return snake.size()-1;
    }
}
