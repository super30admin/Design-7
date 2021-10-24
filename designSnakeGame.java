//TC and SC is not needed for design problems
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

public class designSnakeGame {
    class Position{
        int x;
        int y;
        public Position(int x,int y){
            this.x = x;
            this.y = y;
        }
        public boolean isEqual(Position p){
            return this.x==p.x && this.y == p.y ;
        }
    }
    int len;
    int rows ,cols;
    
    int[][] food;
    LinkedList<Position> snake;
   
    public SnakeGame(int width, int height, int[][] food) {
        this.rows = height;
        this.cols = width;
        this.food = food;
   
        snake = new LinkedList<Position>();
        snake.add(new Position(0,0));
        len = 0;
    }
    
    public int move(String direction) {
    
        Position cur = new Position(snake.get(0).x,snake.get(0).y);
        
        switch(direction){
        case "U": 
            cur.x--;  break;
        case "L": 
            cur.y--; break;
        case "R": 
            cur.y++;   break;
        case "D": 
            cur.x++;   break;
        }
        
        if(cur.x<0 || cur.x>= rows || cur.y<0 || cur.y>=cols) return -1;
        

        for(int i=1;i<snake.size()-1;i++){
            Position next = snake.get(i);
            if(next.isEqual(cur)) return -1;	       
        }
        snake.addFirst(cur);     
        if(len<food.length){
            Position p = new Position(food[len][0],food[len][1]);	        
            if(cur.isEqual(p)){	            
                len++;
            }
        }
        while(snake.size()>len+1) snake.removeLast();
       
        return len;
    }
}
