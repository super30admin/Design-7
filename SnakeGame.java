import java.util.*;

class SnakeGame {
    //everything is o(1) 
    LinkedList<int[]> snakeBody;
    boolean[][] visited;
    int[][] food;
    int h, w;
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w= width;
        this.food = food;
        this.visited = new boolean[h][w];
        this.snakeBody = new LinkedList<>();
        this.idx = 0;
        snakeBody.addFirst(new int[]{0,0});    
    }
    
    public int move(String direction) {
        int head[] = snakeBody.getFirst();
        int r = head[0], c = head[1];
        if(direction.equals("U")){
            r--;
        }else if(direction.equals("D")){
            r++;
        }else if(direction.equals("L")){
            c--;
        }else if(direction.equals("R")){
            c++;
        }

        if(r < 0 || c < 0 || r == h || c == w || visited[r][c]){
            //hit the wall or itself 
            return -1;
        }
        //check if food is there at this point 
        if(idx < food.length){
            if(food[idx][0] == r && food[idx][1] == c ){
                //food is there 
                idx++;
                snakeBody.addFirst(new int[]{r,c});
                visited[r][c] = true;
                return snakeBody.size()-1;
            }
        }
        
        snakeBody.addFirst(new int[]{r,c});
        visited[r][c] = true;
        snakeBody.removeLast();
        int tail[] = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */