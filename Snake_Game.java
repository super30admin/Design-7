// Time Complexity : O(1)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SnakeGame {
    class Pair{
        int x;
        int y;
        public Pair(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
HashSet<Integer> set;
Queue<Pair> queue;
int currRow;
int currCol;
int currFood;
int width;
int height;
int[][] food;
int score;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        set=new HashSet<>();
        queue=new LinkedList<>();
        queue.add(new Pair(0,0));
        currRow=0;
        currCol=0;
        currFood=0;
        this.width=width;
        this.height=height;
        this.food=food;
        this.score=0;
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int newRow=currRow;
        int newCol=currCol;
        
        if(direction.equals("U")){
            newRow--;   
        }else if(direction.equals("L")){
            newCol--;
        }else if(direction.equals("D")){
            newRow++;   
        }else if(direction.equals("R")){
            newCol++;
        }
        
        if(newRow<0 || newRow==height || newCol<0 || newCol==width){
            return -1;
        }
        
        Pair tail=queue.peek();
        int tailKey=tail.x*width+tail.y;
        int newKey=newRow*width+newCol;
        if(set.contains(newKey) && newKey!=tailKey){
            return -1;
        }
        
        if(currFood<food.length && newRow==food[currFood][0] && newCol==food[currFood][1]){
            currFood++;
            score++;
        }else{
            queue.poll();
            set.remove(tailKey);
 
        }
            queue.add(new Pair(newRow,newCol));
            set.add(newKey); 
        currRow=newRow;
        currCol=newCol;
        return score;    
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */