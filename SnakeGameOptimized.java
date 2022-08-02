import java.util.LinkedList;

//Time Complexity=O(1)
//Space Complexity=(L)
public class SnakeGameOptimized {

    int h,w;
    int[][] foodArr;
    LinkedList<int[]> snake;
    int[] head;
    int idx;
    boolean[][] board;
    public SnakeGameOptimized(int width, int height, int[][] food) {
        this.h=height;
        this.w=width;
        this.foodArr=food;
        this.board=new boolean[height][width];
        this.snake=new LinkedList<>();
        this.head=new int[]{0,0};
        this.snake.addLast(this.head);
    }

    public int move(String direction) {
        if(direction.equals("L")){head[1]--;}
        else if(direction.equals("R")){head[1]++;}
        else if(direction.equals("U")){head[0]--;}
        else if(direction.equals("D")){head[0]++;}

        //Border
        if(head[0]<0 || head[1]<0 || head[0]==h || head[1]==w) return -1;

        //Self hit
        if(board[head[0]][head[1]]) return -1;

        //food
        if(idx<foodArr.length){
            int[] currFood=foodArr[idx];
            if(currFood[0]==head[0] && currFood[1]==head[1]){
                snake.addLast(new int[]{head[0],head[1]});
                board[head[0]][head[1]]=true;
                idx++;
                return snake.size()-1;
            }

        }

        snake.addLast(new int[]{head[0],head[1]});
        board[head[0]][head[1]]=true;
        snake.removeFirst();
        int[] curr=snake.get(0);
        board[curr[0]][curr[1]]=false;
        return snake.size()-1;
    }
}
