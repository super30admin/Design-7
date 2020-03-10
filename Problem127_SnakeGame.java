class SnakeGame {
    int width; 
    int height;
    int [] snakeHead;
    LinkedList<int []> snake;
    LinkedList<int []> foodList;
    HashSet<int []> set; 
    
    public SnakeGame(int width, int height, int[][] food) {
        //constructor 
        this.width = width;
        this.height = height;
        //initial head of snake
        snakeHead = new int [] {0,0}; 
        //to iterate over the snake  
        snake = new LinkedList<>();     
        set = new HashSet<>();
        //initially add snakehead to snake LL
        snake.add(snakeHead);
        set.add(snakeHead);
        foodList = new LinkedList<>(Arrays.asList(food));
    }
    //moving snake Down, Up, Left or Right
    public int move(String direction) {
        if(direction.equals("D")) snakeHead[0] += 1; //row+1
        if(direction.equals("U")) snakeHead[0] -= 1; //row-1
        if(direction.equals("L")) snakeHead[1] -= 1; //col-1
        if(direction.equals("R")) snakeHead[1] += 1; //col+1
        //edge case to find if snake hits the borders
        if(snakeHead[0] < 0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) 
            return -1;
        //when snake hits itself
         for(int i = 1; i < snake.size(); i++){
             int [] temp = snake.get(i);
             if (temp[0] == snakeHead[0] &&  temp[1] == snakeHead[1]) 
                    return -1;
         }
        //when snake eats food it increases in size
        //so adds element in stack
        //returning size-1 cz initially had a size of 0
        if(!foodList.isEmpty()){
            int [] appearedFood = foodList.get(0);
            if (appearedFood[0] == snakeHead[0] &&  appearedFood[1] == snakeHead[1]){
                set.add(appearedFood);
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }
        //normal movement of snake
        //returning size-1 cz initially had a size of 0
        int [] tobeRemoved = snake.get(0); // head of list and tail of my snake
        snake.remove();
        set.remove(tobeRemoved);
        int [] newSnakeHead = new int [] {snakeHead[0], snakeHead[1]};
        snake.add(newSnakeHead);
        set.add(newSnakeHead);
        return snake.size() - 1;
    }
}