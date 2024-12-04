class SnakeGame {
    int m;
    int n;
    LinkedList<int[]> snake;
    int[][] food;
    int[] head;
    int idx;
    public SnakeGame(int width, int height, int[][] food) {
        idx=0;
        this.m=width;
        this.n=height;
        head=new int[]{0,0};
        snake=new LinkedList<>();
        snake.add(new int[]{0,0});
        this.food=food;
    }
    
    public int move(String direction) {
        String s=direction;
        if(s.equals("U")) head[0]--;
        else if(s.equals("D")) head[0]++;
        else if(s.equals("R")) head[1]++;
        else if(s.equals("L")) head[1]--;

        if(head[0]<0 || head[1]<0 || head[0]>=n || head[1]>=m) return -1;
        
        for(int i=1;i<snake.size()-3;i++){
            int[]curr = snake.get(i);
            if(curr[0]==head[0] && curr[1]==head[1]) return -1;
        }
        if(idx<food.length){
            if(head[0]==food[idx][0] && head[1]==food[idx][1]){
                idx++;
                snake.add(new int[]{head[0],head[1]});
                return idx;
            }
        }

        snake.removeFirst();
        snake.add(new int[]{head[0],head[1]});
        return idx;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
