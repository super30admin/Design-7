import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

class SnakeGame{
     LinkedList<int[]> snakeBody;
     int[] snakeHead;
     LinkedList<int[]> foodItems;
     int width, height;

     public SnakeGame(int width, int height, int[][] food){
        this.width= width;
        this.height = height;
        snakeHead = new int []{0,0};
        snakeBody = new LinkedList<>();
        snakeBody.add(snakeHead);
        foodItems= new LinkedList<>(Arrays.asList(food));
     }

     public int move(String direction){
       if(direction.equals("U")){
         snakeHead[0]--;
          }
       else if(direction.equals("D")){
        snakeHead[0]++;
        }
       else if(direction.equals("L")){
         snakeHead[1]--;
        }
       else if(direction.equals("R")){
      snakeHead[1]++;
        }
        //Check if the snake is touching the boundaries
        if(snakeHead[0]<0 || snakeHead[0]== height || snakeHead[1]<0 || snakeHead[1]== width){
          return -1;
        }
        //check if the snake is touching itself
        for(int i = 1; i < snakeBody.size(); i++){
          int[] curr = snakeBody.get(i);
          if(snakeHead[0]== curr[0] && snakeHead[1]== curr[1]){
            return -1;
          }
        }
       //if food is present, the snake eats it and return score
       if(!foodItems.isEmpty()){
         int[] appearedFood = foodItems.get(0);
         if(snakeHead[0]== appearedFood[0] && snakeHead[1]== appearedFood[1]){
              snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
              foodItems.remove();
              return snakeBody.size()-1;
         }
         //remove the tail from the snakeBody as it moves ahead
         snakeBody.remove();
         //Add the snake head to the body
         snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
         //return score
         return snakeBody.size()-1;
       }
       return snakeBody.size()-1;
    }
}