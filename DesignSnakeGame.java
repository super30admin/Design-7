
//TC: O(1) all
//SC: O(n)
public class DesignSnakeGame {
  private LinkedList<int[]> snake;
  private int[][] foodlist;
  private boolean[][] visited;
  int[] snakehead;
  int w;
  int h;
  int i=0;

  public DesignSnakeGame(int width, int height, int[][] food) {
      this.w=width;
      this.h=height;
      this.foodlist=food;
      this.visited=new boolean[height][width];
      this.snakehead=new int[]{0,0};
      this.snake=new LinkedList<>();
      this.snake.addFirst(snakehead);
  }
  
  public int move(String direction) {
      if(direction.equals("R")){
          snakehead[1]=snakehead[1]+1;
      }else if(direction.equals("L")){
          snakehead[1]=snakehead[1]-1;
      }else if(direction.equals("U")){
          snakehead[0]=snakehead[0]-1;
      }else{
          snakehead[0]=snakehead[0]+1;
      }
      //bordder hit
      if(snakehead[0]<0 || snakehead[0]==h || snakehead[1]<0 || snakehead[1]==w){
          return -1;
      }
      //hit itself
      if(visited[snakehead[0]][snakehead[1]]){
          return -1;
      }
      if(i<foodlist.length){
          int[] currfood=foodlist[i];
          if(currfood[0]==snakehead[0] && currfood[1]==snakehead[1]){
              i++;
              visited[snakehead[0]][snakehead[1]]=true;
              this.snake.addFirst(new int[]{snakehead[0],snakehead[1]});
              return snake.size()-1;
          }
      }
      //normal move
      this.snake.addFirst(new int[]{snakehead[0],snakehead[1]});
      visited[snakehead[0]][snakehead[1]]=true;
      this.snake.removeLast();
      int[] ntail=snake.getLast();
      visited[ntail[0]][ntail[1]]=false;
      return snake.size()-1;

  }
}

/**
* Your SnakeGame object will be instantiated and called as such:
* SnakeGame obj = new SnakeGame(width, height, food);
* int param_1 = obj.move(direction);
*/