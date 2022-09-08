class SnakeGame {
    
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    int[] snakeHead;
    int height,width;
   
    
    public SnakeGame(int width, int height, int[][] food) {
        
        this.height=height;
        this.width=width;
  
        snakeBody=new LinkedList<>();
        snakeHead=new int[]{0,0};
        snakeBody.add(snakeHead);
    
        foodItems=new LinkedList<>(Arrays.asList(food));
        
    }
    
    public int move(String direction) {
        if(direction.equals("U")){
            snakeHead[0]--;
        }else if(direction.equals("D")){
            snakeHead[0]++;
        }else if(direction.equals("L")){
            snakeHead[1]--;
        }else{
            snakeHead[1]++;  //direction right R
        }
        
        // hitting boundries game over
        if(snakeHead[0]<0 || snakeHead[0] == height || snakeHead[1]<0 || snakeHead[1]==width){
            return -1;
        }
        
        for(int i=1;i<snakeBody.size()-1;i++){
            int[] curr=snakeBody.get(i);
            if(curr[0]==snakeHead[0] && curr[1]==snakeHead[1]){
                return -1;
            }
        }
        
        if(!foodItems.isEmpty()){
            int[] appearedFood=foodItems.get(0);
            if(snakeHead[0]==appearedFood[0] && snakeHead[1]==appearedFood[1]){
                snakeBody.add(new int[]{snakeHead[0],snakeHead[1]});
         
                foodItems.remove();
                return snakeBody.size()-1;
            }
        }
        
     
        snakeBody.remove();
        
        snakeBody.add(new int[]{snakeHead[0],snakeHead[1]});
        return snakeBody.size()-1;
        
    }
}
