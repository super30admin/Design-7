class SnakeGame {
    // public class DLL{
        LinkedList<int[]> snakeBody;
        LinkedList<int[]> foodItems;
        int[] snakeHead;
        int width, height;    
    // }
    
    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        snakeHead = new int[]{0,0};
        snakeBody = new LinkedList<>();
        snakeBody.add(snakeHead);
        foodItems = new LinkedList<>(Arrays.asList(food));
    }
    
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;
        else if(direction.equals("D")) snakeHead[0]++;
        else if(direction.equals("L")) snakeHead[1]--;
        else if(direction.equals("R")) snakeHead[1]++;
        
        //Boundary met
        if(snakeHead[0] == height || snakeHead[0]<0 || snakeHead[1]== width || snakeHead[1] < 0){
            return -1;
        }
        
        //Snake end overlapping/touching itself
        for(int i=1; i<snakeBody.size(); i++){
            int[] curr = snakeBody.get(i);
            if(curr[0] == snakeHead[0] && curr[1] ==snakeHead[1]){
                return -1;
            }
        }
        
        //Food consumption
        if(!foodItems.isEmpty()){
            int[] foodItem = foodItems.get(0);
            if(snakeHead[0] == foodItem[0] && snakeHead[1] == foodItem[1]){
                snakeBody.add(foodItems.remove());
                return snakeBody.size() -1;
            }
        }
        
        snakeBody.remove();
        snakeBody.add( new int[] {snakeHead[0], snakeHead[1]});
        return snakeBody.size() -1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
