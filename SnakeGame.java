//Time Complexity : O(width * height)
//Space Complexity : O(width * height)


class SnakeGame {
    LinkedList<int[]> snake;
    boolean[][] visited;
    int idx; //index on food
    int[] sneakHead;
    int w;int h;
    int[][] foodList;

    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.visited = new boolean[height][width];
        this.foodList = food;
        this.snake = new LinkedList<>();
        this.sneakHead = new int[]{0,0};
        this.snake.addLast(this.sneakHead);    
    }
    
    public int move(String direction) {
        if(direction.equals("L")){
            sneakHead[1]--;
        }else if(direction.equals("R")){
            sneakHead[1]++;
        }else if(direction.equals("D")){
            sneakHead[0]++;
        }else{
            sneakHead[0]--;
        }
        
        //border hit
        if(sneakHead[0] < 0 || sneakHead[0] == h || sneakHead[1] < 0 || sneakHead[1] == w){
            return -1;
        }
        
        //if it hits itself
        if(visited[sneakHead[0]][sneakHead[1]]) return -1;
        
        //if it eats the food
        if(idx < foodList.length){
            int[] currFood = foodList[idx];
            if(currFood[0] == sneakHead[0] && currFood[1] == sneakHead[1] ){
                idx++;
                snake.addLast(new int[] {sneakHead[0], sneakHead[1]});
                visited[sneakHead[0]][sneakHead[1]] = true;
                return snake.size()-1;         
            }
        }
           
        //if normal move
       snake.addLast(new int[] {sneakHead[0], sneakHead[1]});
        visited[sneakHead[0]][sneakHead[1]] = true;
        snake.removeFirst();
        int[] newTail  = snake.get(0);
        visited[newTail[0]][newTail[1]] = false;          
        return snake.size()-1;         
                        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */

        
        
