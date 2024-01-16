//T.C O(m*n) - height and width of the board
//S.C O(m*n) - space for linked lists, max will be m*n
//Sol: Use a linked list to save the body of snake, and food list. Traverse through the dir based on dir map,
// check  boundary and other conditions
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodList;
    int[] head;
    int height;
    int width;
    /*
    Initialize the global variables in the constructor
     */
    public SnakeGame(int width, int height, int[][] foodList){
        this.width = width;
        this.height = height;
        this.foodList = new LinkedList<>(Arrays.asList(foodList));
        snakeBody.add(head);
    }

    /**
     * Move the snake
     * @param dir - 'U','R','D','L'
     * @return the game's score after the move. return -1
     */
    public int move(String dir){
        //New head position based on dir
        if(dir.equals("U")) head[0] -= 1;
        if(dir.equals("D")) head[0] += 1;
        if(dir.equals("L")) head[1] -= 1;
        if(dir.equals("R")) head[1] += 1;

        //check possible hit borders
        if(head[0]<0 || head[0]> height -1 || head[1]<0 && head[1]> width -1)
            return -1;
        //check hitting itself
        for(int i=1;i<snakeBody.size(); i++){
            int[] body = snakeBody.get(i);
            if(head[0] == body[0] && head[1] == body[1])
                    return -1;
        }
        //check food
        if(foodList.size()!=0){
            int[] food = foodList.get(0);
            if(food[0] == head[0] && food[1] == head[1]) {
                snakeBody.add(foodList.remove());
                return snakeBody.size() -1;
            }
        }

        //just move forward without hitting or eating
        snakeBody.remove();
        snakeBody.add(new int[]{head[0],head[1]});
        //return final snake length
        return snakeBody.size() -1;

    }
}
