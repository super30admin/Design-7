/**
 * Time Complexity : O(number of move() calls) * O(snake length) + O(food array length)
 * Space Complexity : O(n)
 */
import java.util.Arrays;
import java.util.LinkedList;

class SnakeGame {
    int h, w;
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        this.h = height;
        this.w = width;
        foodList = new LinkedList<>(Arrays.asList(food));                                               
        snakeHead = new int[] {0,0};
        snake.add(snakeHead);                                                                       
    }

    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;                                                                                   
        if(direction.equals("D")) snakeHead[0]++;
        if(direction.equals("R")) snakeHead[1]++;
        if(direction.equals("L")) snakeHead[1]--;
        if(snakeHead[0] == h || snakeHead[1] == w || snakeHead[0] < 0 || snakeHead[1] < 0){                                         
            return -1;
        }
        for(int i = 1; i < snake.size(); i++){                                                          
            int[] node = snake.get(i);
            if(snakeHead[0] == node[0] && snakeHead[1] == node[1]) return -1;                             
        }
        if(foodList.size() > 0){
            int[] food = foodList.peek();
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]){                                     
                snake.add(foodList.remove());                                                       
                return snake.size()-1;                                                                          
            }
        }
        snake.remove();
        snake.add(new int[]{snakeHead[0], snakeHead[1]});                                   
        return snake.size()-1;                                                              
    }
}