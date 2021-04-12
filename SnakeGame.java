package Design

import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int width;
    int height;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
        
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    // Time Complexity: O(n) where n is length of the snake

    public int move(String directions) {
        
        if(directions.equals("U"))
        {
            snakeHead[0]-=1;
        }else if(directions.equals("D"))
        {
            snakeHead[0]+=1;
        }else if(directions.equals("L"))
        {
            snakeHead[1]-=1;
        }else if(directions.equals("R"))
        {
            snakeHead[1]+=1;
        } 
        
        if(snakeHead[0]<0 || snakeHead[0]>=height || snakeHead[1]<0 || snakeHead[1]>=width ) return -1;
        
        // check if snake touches itself
        for(int i=1;i<snake.size();i++)
        {
            if(snakeHead[0]==snake.get(i)[0]  && snakeHead[1]==snake.get(i)[1] )
               return -1;
        }
        
        // head touches food
        if(!foodList.isEmpty())
        {
            int [] food = foodList.get(0);
            
            if(snakeHead[0] == food[0] && snakeHead[1]==food[1])
            {
                snake.add(foodList.removeFirst());
                return snake.size() -1;
            }
        }
        
        snake.remove();
        snake.add(new int[]{snakeHead[0],snakeHead[1]});
        return snake.size() -1;
    }
}
    
}
