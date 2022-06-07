import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SnakeGame {

    LinkedList<int[]> snakeBody;
    int width, height;
    LinkedList<int[]> foodItems;
    int[] snakeHead;

    public SnakeGame(int width, int height, int[][] food){
        snakeBody = new LinkedList<>();
        snakeHead = new int[]{0,0}; // initially the head is at 0,0
        snakeBody.add(snakeHead);
        foodItems = new LinkedList<>(Arrays.asList(food));
        this.width = width;
        this.height = height;
    }

    // TC : O(m * n) -> snake can cover all the cells of the board
    // SC : O(M * n)
    public int move(String direction){
        if(direction.equals("U")) snakeHead[0]--;
        else if(direction.equals("D")) snakeHead[0]++;
        else if(direction.equals("L")) snakeHead[1]--;
        else if(direction.equals("R")) snakeHead[1]++;

        // if snake touches the boundaries
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width)
            return -1;

        // Does snake touch itself
        // we're checking starting from the i=1 node because snake's head move one position
            // so the previous tail will also move ahead(get removed from the linked list)
        for(int i=1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(0);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]) return -1;
        }

        if(foodItems.size() > 0) {
            int[] food = foodItems.get(0);
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]){
                snakeBody.add(new int[]{snakeHead[0], snakeHead[1]});
                foodItems.remove();
                return snakeBody.size() - 1; // we started with 1 node as a head. Whatever left after that node will be considered to calculate the score
            }

        }

        //snake has moved ahead
        snakeBody.add(new int[]{snakeHead[0], snakeHead[1]});
        snakeBody.remove();
        return snakeBody.size() - 1;
    }
}
