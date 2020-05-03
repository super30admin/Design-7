//Time Complexity: O(n)
//Space Complexity: O(m + n) Food and Snake Lists
import java.util.Arrays;
import java.util.LinkedList;

public class DesignSnakeGame {
	int width; int height; int[] snakeHead;
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public DesignSnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[]{0, 0};
        snake = new LinkedList<>();
        snake.add(snakeHead);
        foodList = new LinkedList<>(Arrays.asList(food));
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0] -= 1;
        if(direction.equals("L")) snakeHead[1] -= 1;
        if(direction.equals("D")) snakeHead[0] += 1;
        if(direction.equals("R")) snakeHead[1] += 1;
        
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) return -1;
        
        //snake hits self
        for(int i = 1; i < snake.size(); i++){
            int[] tail = snake.get(i);
            if(tail[0] == snakeHead[0] && tail[1] == snakeHead[1]) return -1;
        }
        
        //snake eats food
        if(!foodList.isEmpty()){
            int[] appeared = foodList.get(0);
            if(appeared[0] == snakeHead[0] && appeared[1] == snakeHead[1]){
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }
        
        //normal move
        snake.remove();
        snake.add(new int[]{snakeHead[0], snakeHead[1]});
        return snake.size() - 1;
    }
}
