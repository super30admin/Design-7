// "static void main" must be defined in a public class.
public class SnakeGame {
    LinkedList<int []> snake;
    LinkedList<int []> foodList;
    int[] snakeHead;
    int width, height;
    
    public SnakeGame(int width, int height, int[][] food){
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0,0};
        snake.add(snakeHead);
    }
    
    public int move(String direction){
        if(direction.equals("U")){
            snakeHead[0]--;
        }
        if(direction.equals("D")){
            snakeHead[0]++;
        }
        if(direction.equals("L")){
            snakeHead[1]--;
        }
        if(direction.equals("R")){
            snakeHead[1]++;
        }
        
        // Not touching border
        if(snakeHead[1] < 0 || snakeHead[1] >= width || snakeHead[0] < 0 || snakeHead[0] >= height)
            return -1;
        
        // Not touching body
        for(int i = 1; i < snake.size(); i++){
            int[] curr = snake.get(i);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1])
                return -1;
        }
        
        // If it eats food
        if(!foodList.isEmpty()){
            int[] curr = foodList.peek();
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]){
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }
        
        // Just moving ahead - removing the tail, adding the updated head to the linkedlist
        snake.remove();
        snake.add(new int[]{snakeHead[0], snakeHead[1]});
        return snake.size() - 1;
    }
}