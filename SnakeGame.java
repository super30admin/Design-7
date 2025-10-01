// Time Complexity: O(1) per move()
// Space Complexity: O(N) where N is max snake length. + O(w * h) for visited

// Approach : We track the snake's body using a deque and mark visited cells except the tail.

public class SnakeGame {
     boolean[][] visited;
    LinkedList<int[]> snakeBody;
    int w, h;
    int[][] food;
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.visited = new boolean[h][w];
        this.food = food;
        this.idx = 0;
        this.snakeBody = new LinkedList<>();
        snakeBody.addFirst(new int[]{0,0});
    }

    public int move(String direction) {
        int[] head = snakeBody.getFirst();
        int r = head[0], c = head[1];
        if(direction.equals("L")){
            c--;
        }else if(direction.equals("R")){
            c++;
        }else if(direction.equals("D")){
            r++;
        }else if(direction.equals("U")){
            r--;
        }

        if(r < 0 || c < 0 || r == h || c == w || visited[r][c]) return -1;

        if(idx < food.length){
            if(food[idx][0] == r && food[idx][1] == c){
                snakeBody.addFirst(new int[]{r, c});
                visited[r][c] = true;
                idx++;
                return snakeBody.size() - 1;
            }
        }

        snakeBody.addFirst(new int[]{r, c});
        visited[r][c] = true;
        snakeBody.removeLast();
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size() - 1;
    }
}