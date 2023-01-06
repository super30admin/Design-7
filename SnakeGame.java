import java.util.Arrays;
import java.util.LinkedList;

// Time Complexity : O(n) , n- length of snake body
// Space Complexity :O(w*h) , w- width, h- height
class SnakeGame {
    int width;
    int height;
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    int[] snakehead;
    public SnakeGame(int width, int height, int[][] food) {
        this.width=width;
        this.height=height;
        snakeBody=new LinkedList<>();
        foodItems=new LinkedList<>(Arrays.asList(food));
        snakehead=new int[]{0,0};
        snakeBody.addLast(new int[] {snakehead[0],snakehead[1]});
    }

    public int move(String direction) {
        if(direction.equals("U")){
            snakehead[0]--;
        }
        else if(direction.equals("D")){
            snakehead[0]++;
        }
        else if(direction.equals("R")){
            snakehead[1]++;
        }
        else if(direction.equals("L")){
            snakehead[1]--;
        }

        //check if game over
        if(snakehead[0]<0 || snakehead[1]==width || snakehead[0]==height || snakehead[1]<0){
            return -1;
        }


        for(int i=1;i<snakeBody.size();i++){
            int[] curr=snakeBody.get(i);
            if(snakehead[0]==curr[0] && snakehead[1]==curr[1]){
                return -1;
            }
        }


        //check if snakehead is on food item
        if(foodItems.size()>0){
            int[] curr=foodItems.get(0);
            if(snakehead[0]==curr[0] && snakehead[1]==curr[1]){
                snakeBody.addLast(new int[]{snakehead[0],snakehead[1]});
                foodItems.removeFirst();
                return snakeBody.size()-1;
            }
        }

        snakeBody.addLast(new int[]{snakehead[0],snakehead[1]});
        snakeBody.removeFirst();
        return snakeBody.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */