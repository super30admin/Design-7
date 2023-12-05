// Time Complexity : O(1)
// Space Complexity :O(1)
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :
class SnakeGame {
    LinkedList<int[]> snake;
    int h; int w;
    boolean[][] visited;
    int[] head;
    int[][] foodList;
    int i;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.snake = new LinkedList<>();
        this.visited = new boolean[h][w];
        this.head = new int[]{0,0};
        this.snake.addFirst(new int[]{head[0],head[1]});
        this.foodList = food;
    }
    
    public int move(String direction) {
        if(direction.equals("U"))
            head[0]--;
        else if(direction.equals("D"))
            head[0]++;
        else if(direction.equals("R"))
            head[1]++;
        else 
            head[1]--;
        if(head[0] < 0 || head[0] >= h || head[1] < 0|| head[1] >=w||visited[head[0]][head[1]])
            return -1;
        if(i < foodList.length){
            if(head[0] == foodList[i][0] && head[1] == foodList[i][1]){
                snake.addFirst(new int[]{head[0],head[1]});
                visited[head[0]][head[1]] = true;
                i++;
                return snake.size()-1;
            }
        }
        snake.addFirst(new int[]{head[0],head[1]});
        visited[head[0]][head[1]] = true;
        snake.pollLast();
        int[] curr = snake.peekLast();
        visited[curr[0]][curr[1]] = false;
        return snake.size()-1;
        
        
    }
}