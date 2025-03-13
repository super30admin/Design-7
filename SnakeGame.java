//TC: O(n) where n is snake length for collision detection,
// O(F) for initiation, where f is number of food items
//rest are O(1) time operations
//SC: O(n+f)
//approach: using linkedlist for snakes body, and queue for food items

import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {
    int width;
    int height;
    int [] head;
    LinkedList<int[]> snake = new LinkedList<>();   //to track cells snake's body is in currently
    LinkedList<int []>foodList;

    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        head = new int[] {0,0};
        snake.add(head);
        foodList = new LinkedList<>(Arrays.asList(food));
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) head[0] -= 1;
        if(direction.equals("D")) head[0] += 1;
        if(direction.equals("L")) head[1] -= 1;
        if(direction.equals("R")) head[1] += 1;

        // Hit borders
        if(head[0] < 0 || head[0] > height - 1 || head[1] < 0 || head[1] > width - 1 ) return -1;

        // Snake hits itself
        for(int i = 1; i < snake.size(); i++){
            int [] curr = snake.get(i);
            if(curr[0] == head[0] && curr[1] == head[1]) return -1;
        }

        // Eat food
        if(foodList.size() != 0){
            int [] appearedFood = foodList.get(0);
            if(appearedFood[0] == head[0] && appearedFood[1] == head[1]){
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }

        // Move without eating food
        snake.remove();
        snake.add(new int [] {head[0], head[1]});
        return snake.size() - 1;

    }
}
