import java.util.Arrays;
import java.util.LinkedList;

//Time Complexity : O(k); where k is snake length
//Space Complexity : O(W*H + N) where W= width, H= Height and N=size of food List
public class SnakeGame {	
	/**Approach: Linked List**/
	LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] head;
    int width;
    int height;
    public SnakeGame(int width, int height, int[][] food) {
        this.height= height;
        this.width= width;
        this.head= new int[]{0,0};
        this.snake= new LinkedList<>();        
        this.foodList= new LinkedList<>(Arrays.asList(food));    
        snake.addLast(head);
    }
    
    public int move(String direction) {
        if(direction.equals("L")) head[1]--;
        else if(direction.equals("R")) head[1]++;
        else if(direction.equals("U")) head[0]--;
        else head[0]++;
        
        //if hits boundary
        if(head[0] < 0 || head[0] == height || head[1] < 0 || head[1] == width)
            return -1;
        
        //if hits itself
        for(int i=1; i<snake.size(); i++){
            int[] curr= snake.get(i);            
            if(head[0] == curr[0] && head[1] == curr[1]) return -1;
        }
        
        //eat food
        if(!foodList.isEmpty()){
            int[] food= foodList.get(0);           
            if(head[0] == food[0] && head[1] == food[1]){
                foodList.removeFirst();
                snake.addLast(new int[] {head[0], head[1]}); 
                return snake.size()-1;
            }
        }
        //normal move
        snake.removeFirst();
        snake.addLast(new int[] {head[0], head[1]});         
        
        return snake.size()-1;
    }
	
	// Driver code to test above
	public static void main (String[] args) {	
		SnakeGame snakeGame = new SnakeGame(3,2, new int[][] {{1,2},{0,1}});		
		System.out.println("Snake size after move R: "+snakeGame.move("R")); // return 0
		System.out.println("Snake size after move D: "+snakeGame.move("D")); // return 0
		System.out.println("Snake size after move R: "+snakeGame.move("R")); // return 1, snake eats the first piece of food. The second piece of food appears at (0, 1).
		System.out.println("Snake size after move U: "+snakeGame.move("U")); // return 1
		System.out.println("Snake size after move L: "+snakeGame.move("L")); // return 2, snake eats the second food. No more food appears.
		System.out.println("Snake size after move U: "+snakeGame.move("U")); // return -1, game over because snake collides with border		
		
	}
}
