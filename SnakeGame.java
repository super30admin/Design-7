//Time - O(1)
//Space - O(1)
class SnakeGame {
    LinkedList<int []> snake;
    int index;
    int width; int height;
    int[][] food;
    int[] head;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.snake = new LinkedList<>();
        this.head = new int[]{0,0};
        this.snake.addLast(this.head);
        this.food = food;
    }

    public int move(String direction) {
        if(direction.equals("R")) head[1]++;
        else if(direction.equals("L")) head[1]--;
        else if(direction.equals("U")) head[0]--;
        else if(direction.equals("D")) head[0]++;

        if(head[0] < 0 || head[0] >=height || head[1] <0 || head[1] >=width){
            return -1;
        }

        //hhitting itself
        for(int i=1; i<snake.size(); i++){
            if(head[0] == snake.get(i)[0] && head[1] == snake.get(i)[1]) return -1;
        }

        //eat food
       if(index < food.length){
           int[] fd = food[index];
           if(fd[0] == head[0] && fd[1] == head[1]){
               int[] newHead = new int[]{head[0], head[1]};
               snake.add(newHead);
               index++;
               return snake.size()-1;
           }
       }
        //normal move
        //remove tail of snake
         int[] newHead = new int[]{head[0], head[1]};
         snake.addLast(newHead);
        snake.removeFirst();
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */ 