class SnakeGame {
    
    class Node{
        int x;
        int y;
        Node(int x,int y){
            
            this.x=x;
            this.y=y;
        }
        
    }
    
    int size=0;
    int width; int height;
    boolean board[][];
    Node head; 
    Queue<Integer> fx;
    Queue<Integer> fy;
    Deque<Node> q= new LinkedList<>();
    public SnakeGame(int width, int height, int[][] food) {
        this.width=width;
        this.height=height;
        this.head= new Node(0,0);
        q.addFirst(head);
        board=new boolean[height][width];
        
        
       fx= new LinkedList<>();
       fy= new LinkedList<>();
        for(int i=0;i<food.length;i++){
            fx.add(food[i][0]);
            fy.add(food[i][1]);
        }
        board[0][0]=true;
    }
    
    public int move(String direction) {
        
        int x=this.head.x;
        int y=this.head.y;
      
        if (direction.equals("L")){
           y--;
           }else if(direction.equals("R")){
            y++;
            }else if(direction.equals("U")){
            x--;
        }else{
            x++;
            
        }
       
        
                     if(x>=height || x<0 ||y<0||y>=width){return -1;}  
        
                     if(fx.size()!=0 && fx.peek()==x && fy.peek()==y){
                        
                         board[x][y]=true;
                         size++;
                         q.addFirst(new Node(x,y));
                         head=q.getFirst();
                         fx.poll();
                         fy.poll();
                         return size;
                     }else{
                        
                         q.addFirst(new Node(x,y));
                         board[q.getLast().x][q.getLast().y]=false;
                         q.removeLast();
                         head=q.getFirst();
                        if(board[x][y]){return -1;}
                         board[x][y]=true;
                         return size;
                     }
        
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
