class SnakeGame {

        LinkedList<int[]> snake;
        LinkedList<int[]> foodl;
        int[] snakehead;
        int width;
        int height;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
      this.width = width;
      this.height = height;
      snake = new LinkedList<>();
      foodl = new LinkedList<>(Arrays.asList(food));
      snakehead = new int[]{0,0};
      snake.add(snakehead);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U"))
        {
          snakehead[0]--;
        }
      if(direction.equals("D"))
        {
                    snakehead[0]++;

        }
      if(direction.equals("L"))
        {
                    snakehead[1]--;

        }
      if(direction.equals("R"))
        {
                    snakehead[1]++;

        }
      if(snakehead[1]<0 || snakehead[1] >=width || snakehead[0] < 0 || snakehead[0]>= height)
      {
        return -1;
      }
      for(int i=1;i<snake.size();i++)
      {
        int[] curr = snake.get(i);
        if(snakehead[0] == curr[0] && snakehead[1]==curr[1])
        {
          return -1;
        }
      }
      
      if(!foodl.isEmpty())
      {
        int[] curr = foodl.peek();
        if(snakehead[0] == curr[0] && snakehead[1]==curr[1])
        {
          snake.add(foodl.remove());
          return snake.size()-1;
        }
      }
      snake.remove();
      snake.add(new int[]{snakehead[0],snakehead[1]});
      return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */