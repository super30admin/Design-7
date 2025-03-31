// Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

// Time Complexity : O(1)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach in three sentences only
/*
 * Here, take a linked list to keep track of snake movement and a boolean matrix to keep track of visited. When moved if snakehead id equal
 * to food then add that to snake list and make visited true and return snake.size-1. If its normal movement add that to list and make true
 * and removw tail i.e., remove last element and make visited false and return snake.size-1.
 */
class SnakeGame {
    private int [][]foodList;
    int i; // index on foodlist
    private LinkedList<int []> snake;
    private int[] snakeHead;
    private boolean [][]visited;
    int w; int h;
    public SnakeGame(int width, int height, int [][]food){
        this.foodList = food;
        this.w = width;
        this.h = height;
        this.visited = new boolean[height][width];
        this.snake = new LinkedList<>();
        this.snakeHead = new int[]{0,0};
        this.snake.addFirst(snakeHead);  // tail of linkedlist is head of snake
    }

    public int move(String direction){
        if(direction.equals("R")){
            snakeHead[1]++;
        }
        else if(direction.equals("L")){
            snakeHead[1]--;
        }
        else if(direction.equals("D")){
            snakeHead[0]++;
        }
        else{
            snakeHead[0]--;
        }
        //border hit
        if(snakeHead[0]< 0 || snakeHead[0]> h || snakeHead[1]< 0 || snakeHead[1]> w){
            return -1;
        }
        // hits itself
        if(visited[snakeHead[0]][snakeHead[1]]){
            return -1;
        }
        // if eats food
        if(i<foodList.length){
            int []currFood = foodList[i];
            if(currFood[0] == snakeHead[0] && currFood[1] == snakeHead[1]){
                i++;
                this.snake.addFirst(new int[]{snakeHead[0], snakeHead[1]});
                visited[snakeHead[0]][snakeHead[1]] = true;
                return snake.size()-1;
            }
        }
        //normal move
        //head
        this.snake.addFirst(new int[]{snakeHead[0], snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        // tail
        this.snake.revomeLast();
        Node newTail = snake.getLast();
        visited[newTail[0]][newTail[1]] = false;
        return snake.size()-1;
    }
}
