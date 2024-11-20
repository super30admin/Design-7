import java.util.*;

class SnakeGame {


  int[] currPosition;
  LinkedList<int[]> snake;
  LinkedList<int[]> foodList;
  int width, height;
  public SnakeGame(int width, int height, int[][] food) {
      this.width = width;
      this.height = height;
      currPosition = new int[] {0, 0};
      snake = new LinkedList<>();
      // snake.add(currPosition)
      foodList = new LinkedList<>(Arrays.asList(food));
  }
  
  public int move(String direction) {
      int new_x = currPosition[0];
      int new_y = currPosition[1];
      
      if(direction.equals("U")){
          new_x -= 1;
      } else if(direction.equals("R")) {
          new_y += 1;
      } else if(direction.equals("D")) {
          new_x += 1;
      } else{
          new_y -= 1;
      }
      currPosition = new int[]{new_x, new_y};
      // System.out.println("heyo");
      // snake hitting the wall
      if(new_x < 0 || new_y < 0 || new_x >= height || new_y >= width){
           // System.out.println("yo");
          return -1;
      }
      
      // snake eating food
      if(!foodList.isEmpty()){
          int[] currFood = foodList.getFirst();
          if(currFood[0] == new_x && currFood[1] == new_y){
              // add position to snake
              snake.addFirst(new int[] {new_x, new_y});

              // remove the food from list.
              foodList.removeFirst();
              // currPosition = new int[]{new_x, new_y};
              return snake.size();
          }
      }
      
      
      
      // snake hitting itself
      for(int i = 0; i < snake.size() ; i++){
          
          int[] snakePos = snake.get(i);
          if(snakePos[0] == new_x && snakePos[1] == new_y){
              // System.out.println("heyo");
              return -1;
          }
      }
      
      // snake moving into a blank spot
      
      snake.addFirst(new int[]{new_x, new_y});
      snake.removeLast();
      // System.out.println(snake.size());
      return snake.size();
  }
}
