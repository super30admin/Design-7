import java.util.LinkedList;

//L=Length of Snake
//Time Complexity=O(l)
//Space Complexity=O(l)
public class SnakeGame {

    int h,w;
    int[][] foodArr;
    LinkedList<int[]> snake;
    int[] head;
    int idx;
    public SnakeGame(int width, int height, int[][] food) {
        this.h=height;
        this.w=width;
        this.foodArr=food;

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
        for(int i=1;i<snake.size();i++){
            int[] curr=snake.get(i);
            if(curr[0]==head[0] && curr[1]==head[1]){
                return -1;
            }
        }

        //food
        if(idx<foodArr.length){
            int[] currFood=foodArr[idx];
            if(currFood[0]==head[0] && currFood[1]==head[1]){
                snake.addLast(new int[]{head[0],head[1]});
                idx++;
                return snake.size()-1;
            }

        }

        snake.addLast(new int[]{head[0],head[1]});
        snake.removeFirst();
        return snake.size()-1;
    }
}
