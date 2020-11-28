class SnakeGame {

    HashMap<Pair<Integer,Integer>,Boolean> snakeMap;
    Deque<Pair<Integer,Integer>> snake;
    int[][] food;
    int height;
    int width;
    int foodIndex;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.snakeMap = new HashMap<>();
        this.snakeMap.put(new Pair<Integer,Integer>(0,0),true);//
        this.snake = new LinkedList<>();
        this.snake.offerLast(new Pair<Integer,Integer>(0,0));
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {// O(1)
        //Parse the direction
        Pair<Integer,Integer> snakeCell = this.snake.peekFirst();//
        int newHeadRow = snakeCell.getKey();
        int newHeadCol = snakeCell.getValue();
        
        switch(direction){
            case "U":
                newHeadRow--;
                break;
            case "L":
                newHeadCol--;
                break;
            case "D":
                newHeadRow++;
                break;
            case "R":
                newHeadCol++;
                break;
        }
        Pair<Integer,Integer> currentTail = this.snake.peekLast();//
        Pair<Integer,Integer> newHead = new Pair<Integer,Integer>(newHeadRow,newHeadCol);
        
        //Boundary condition
        boolean cond1 = newHeadRow <0 || newHeadRow >= this.height ;
        boolean cond2 = newHeadCol <0 || newHeadCol >= this.width ;
        
        //Biting condiiton 
        boolean cond3 = this.snakeMap.containsKey(newHead) && !(newHead.getKey() == currentTail.getKey() && newHead.getValue() == currentTail.getValue());
        
        if(cond1 || cond2 || cond3){
            return -1;
        }
        //Food available
        if((this.foodIndex < this.food.length) && (this.food[this.foodIndex][0] == newHeadRow) && (this.food[this.foodIndex][1] == newHeadCol)){
            this.foodIndex++;
        }else{
            this.snake.pollLast();
            this.snakeMap.remove(currentTail);
        }
        //New Head always be added
        this.snake.addFirst(newHead);
        this.snakeMap.put(newHead , true);
        return this.snake.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */