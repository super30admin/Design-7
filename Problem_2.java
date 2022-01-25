// Time Complexity : O(1)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : Yes
// Three line explanation of solution in plain english
// Take a linkedList to store the snake size, when given the direction move the head of the snake towards that direction, and first check if the head is not hitting the walls, then check if the snack is not heating the body, then check if the snack is having the food or not, if haivng then add the head to the snake list and return size, if not then remove the last value from the snake list and add the head and retune the size;
// Your code here along with comments explaining your approach
class SnakeGame {

    int height; int width;
    LinkedList<int []> food;
    LinkedList<int []> snake;
    int [] sHead;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        sHead = new int [] {0,0};
        snake = new LinkedList<>();
        snake.add(sHead);
        this.food = new LinkedList<>(Arrays.asList(food));
    }

    public int move(String direction) {
        if(direction.equals("R")){
            sHead[1] = sHead[1] + 1;
        }else if(direction.equals("D")){
            sHead[0] = sHead[0] + 1;
        }else if(direction.equals("L")){
            sHead[1] = sHead[1] - 1;
        }else if(direction.equals("U")){
            sHead[0] = sHead[0] - 1;
        }
        // check for wall
        if((sHead[0] < 0 || sHead[0] == height)
                || (sHead[1] < 0 || sHead[1] == width)){
            return -1;
        }
        // if body
        for(int i = 1; i < snake.size(); i++){
            int [] curr = snake.get(i);
            if(curr[0] == sHead[0] && curr[1] == sHead[1]){
                return -1;
            }
        }
        //if food
        if(!food.isEmpty()){
            int [] currF = food.get(0);
            if(currF[0] == sHead[0] && currF[1] == sHead[1]){
                snake.add(food.remove());
                return snake.size()-1;
            }
        }
        snake.remove();
        snake.add(new int [] {sHead[0], sHead[1]});
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */