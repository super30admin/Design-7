// Time Complexity :O(n) where n is length of snake
// Space Complexity :O(1)
// Did this code successfully run on Leetcode :yes

class SnakeGame {

    private LinkedList<int[]> snakeBody;
    private int index;
    private int[][] food;
    private int width;
    private int height;
    private int[] head;
    public SnakeGame(int width, int height, int[][] food) {
        this.index=0;
        this.food=food;
        this.snakeBody=new LinkedList<>();
        this.head=new int[2];
        this.snakeBody.add(head);
        this.width=width;
        this.height=height;
    }

    public int move(String direction) {
        //calculate new head
        int snakeSize=this.snakeBody.size();
        int[] newHead=new int[]{head[0],head[1]};
        //left move
        if(direction.equals("L")){
            newHead[1]--;
        }
        //right move
        else if(direction.equals("R")){
            newHead[1]++;
        }
        //up move
        else if(direction.equals("U")){
            newHead[0]--;
        }//down move
        else if(direction.equals("D")){
            newHead[0]++;
        }
        //dies border
        if(newHead[0]<0 || newHead[0]>=height ||newHead[1]<0 ||newHead[1]>=width){
            // System.out.println("snake dies border"+newHead[0]+"  "+newHead[1]);
            return -1;
        }
        //dies body
        for(int i=1; i<snakeSize;i++){

            if(this.snakeBody.get(i)[0]==newHead[0] && this.snakeBody.get(i)[1]==newHead[1]){
                return -1;
            }
        }
        //eats food

        if(index<food.length){

            int[] currFood=this.food[index];

            if(newHead[0]==currFood[0] && newHead[1]==currFood[1]){

                this.snakeBody.addLast(newHead);
                snakeSize++;
                this.index++;
                this.head=newHead;
                return  this.snakeBody.size()-1;
            }

        }

        this.snakeBody.addLast(newHead);
        this.snakeBody.remove(0);
        this.head=newHead;

        //normal move

        return  this.snakeBody.size()-1;
    }
}