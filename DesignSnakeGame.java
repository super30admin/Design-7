//Time Complexity: O(1)
//Space Complexity: O(n)

/*
 * maintian visited array, snake linked list and move in the given 
 * direction and check the 4 conditions of hitting border, hitting
 * body, eats food, normal move add and remove elements in visited
 * array and linkedlist accordingly.
 */

class SnakeGame{
    LinkedList<int []> snake;
    int idx;
    int[] snakeHead;
    int w; int h;
    int[][] foodArr;
    boolean[][] visited;
    
    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.foodArr = food;
        this.snakeHead = new int[] {0,0};
        this.visited = new boolean[h][w];
        this.snake.addLast(snakeHead);
    }
    
    public int move(String direction) {
        if(direction.equals("R")){
            this.snakeHead[1]++;
        }
        if(direction.equals("L")){
            this.snakeHead[1]++;
        }
        if(direction.equals("U")){
            this.snakeHead[0]--;
        }
        if(direction.equals("D")){
            this.snakeHead[0]++;
        }
        //if it hits border
        if(this.snakeHead[0] < 0 || this.snakeHead[0] == h || this.snakeHead[1] < 0 || this.snakeHead[1] == w) return -1;
        
        //if snake hits itself
        if(visited[snakeHead[0]][snakeHead[1]]) return -1;
        
        //snake eats food
        if(idx < foodArr.length){
            int[] currFood = foodArr[idx];
            if(currFood[0] == snakeHead[0] && currFood[1] == snakeHead[1]){
                visited[snakeHead[0]][snakeHead[1]] = true;
                snake.addLast(new int[] {snakeHead[0], snakeHead[1]});
                idx++;
                return snake.size()-1;
            }
        }

        //normal move
        snake.addLast(new int[] {snakeHead[0], snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        snake.removeFirst();
        int[] newTail = snake.get(0);
        visited[newTail[0]][newTail[1]] = false;
        return snake.size()-1;
    }
}