class SnakeGame{
  LinkedList<int []> snake;
  LinkedList<int []> foodList;
  int [] snakeHead;
  int width;
  int height;
  
  public SnakeGame(int width, int height, int[][] food){
    this.snake = new LinkedList<>();
    this.width = width;
    this.height = height;
    foodList = new LinkedList<>(Arrays.asList(food));
    snakeHead = new int []{0, 0};
    this.snake.add(snakeHead);
  }
  
  public int move(String direction){
    if(direction.equals("U")){
        snakeHead[0]--;
    }
    
    if(direction.equals("L")){
        snakeHead[1]--;
    }
    
    if(direction.equals("D")){
        snakehead[0]++;
    }
    
    if(direction.equals("R")){
        snakehead[1]++;
    }
    
    //hits the borders
    if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) return -1;
    
    //hit itself
    for(int i = 1; i < snake.size(); i++){
        int [] curr = snake.get(i);
        if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1]){
            return -1;
        }
    }
    
    //eats food
    if(foodList.size() > 0){
        int[] currfood = foodList.peek();
        if(currFood[0] == snakeHead[0] && currFood[i] == snakeHead[1]){
            snake.add(foodList.remove());
            return snake.size() - 1;
        }
    }
    //normal, when not eating food
    snake.remove();
    snake.add(new int[] {snakeHead[0], snakeHead[1]});
    return snake.size() - 1;
  }
}
    
    
