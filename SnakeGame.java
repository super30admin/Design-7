import java.util.*;

class SnakeGame {
    LinkedList<int []> snake;
    LinkedList<int []> foodList;
    int width; int height;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        foodList = new LinkedList<>(Arrays.asList(food));
        snake = new LinkedList<>();
        snakeHead = new int[] {0,0};
        snake.add(snakeHead);
        
    }
    
    
    public int move(String direction) {
        //O(n) approach for using linkedlist, can be optimized with hashSet
        
        if(direction.equals("U")){
            snakeHead[0] -=1;
        } else if(direction.equals("D")){
            snakeHead[0]+=1;
        } else if(direction.equals("L")){
            snakeHead[1]-=1;
        } else if(direction.equals("R")){
            snakeHead[1]+=1;
        }
        //boundry check
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] <0 || snakeHead[1] == width ) return -1;
        //check for self bite
        for(int i = 1; i < snake.size(); i++){
            int[] curr = snake.get(i);
            if(curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) return -1;
        }
        //food 
        if(foodList.size() > 0){
            int[] food = foodList.peek();
            if(snakeHead[0] == food[0]&& 
            snakeHead[1] == food[1]){
                foodList.remove();            
                snake.add(new int[] {snakeHead[0],snakeHead[1]});
                return snake.size()-1;
            }
        }
        snake.remove();
        snake.add(new int[] {snakeHead[0],snakeHead[1]});
        return snake.size()-1;
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */