import java.util.Arrays;
import java.util.LinkedList;

class SnakeGame{

    LinkedList<int[]> snakeBody;
    int width, height;
    LinkedList<int[]> foodItems;
    int[] snakeHead;

    public SnakeGame(int width, int height, int[][] food){
        snakeBody = new LinkedList<>();
        snakeHead = new snakeHead(0, 0);
        snakeBody.add(snakeHead);
        foodItems = new LinkedList<>(Arrays.asList(food));
        this.width = width;
        this.height = height;
    }

    public int move(String direction){
        if(direction.equals("U")){
            snakeHead[0]--;
        }
        else if(direction.equals("D")){
            snakeHead[0]++;
        }
        else if(direction.equals("L")){
            snakeHead[1]--;
        }
        else if(direction.equals("R")){
            snakeHead[1]++;
        }

        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width){
            return -1;
        }

        for(int i = 1; i < snakeBody.size(); i++){
            int[] curr = snakeBody.get(i);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]){
                return -1;
            }
        }

        if(foodItems.size() > 0){
            int[] food = foodItems.get(0);
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]){
                snakeBody.add(snakeHead[0], snakeHead[1]);
                foodItems.remove();
                return snakeBody.size() - 1;
            }
        }

        snakeBody.add(new int[] {snakeHead[0], snakeHead[1]});
        snakeBody.remove();
        return snakeBody.size() - 1;
    }
}

//time complexity O(m * n) where m is height and n is width
//space comlexity O(m * n)