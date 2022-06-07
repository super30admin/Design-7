// O(n) time and space

class SnakeGame {

    int maxRow;
    int maxCol; 
    int x=0;
    int y=0; 
    int[][] food= null;  
    int length=0; 
    int foodPosn=0; 
    Deque<Integer> path= new LinkedList<>(); 
    Set<Integer> pathHash= new HashSet<Integer>(); 
  
 
     public SnakeGame(int width, int height, int[][] food) {
        maxRow=height;
        maxCol=width; 
        this.food=food ; 
        int currentPosn= getPositionHash();
        path.addFirst(currentPosn); 
        pathHash.add(currentPosn); 
        System.out.println(path);
        System.out.println(pathHash);
     }
     
     public int move(String direction) {
        System.out.println(direction);
 
         if(direction.equals("R"))
             y+=1;
         else if(direction.equals("L"))
             y-=1;
         else if(direction.equals("D"))
             x+=1;
         else if(direction.equals("U"))
             x-=1;
         System.out.println(x+" "+y);
         boolean OffLimitsBoundaryForHead = (x<0 || x>=maxRow|| y<0 || y>=maxCol);
         if(OffLimitsBoundaryForHead)
             return -1;
         int currentPosition=getPositionHash();
         System.out.println("Hash for current position " +currentPosition);
         //tail shouldis disappear as per logic required..it should move first before head comes
         boolean snakeSelfBiteCheck= pathHash.contains(currentPosition) && path.peekLast()!=currentPosition;
         if(snakeSelfBiteCheck)
             return -1;
         
        
         //find food
         if(isFoodFound()){
             length+=1;
         }
         else{
          int lastPosition= path.removeLast();
          pathHash.remove(lastPosition);   
         }
         //move tail and then add head
         path.addFirst(currentPosition);        
         pathHash.add(currentPosition);
         
         System.out.println("length "+length);
         System.out.println("Queue:");
         System.out.println(path);
         System.out.println("Hash:");
         System.out.println(pathHash);
         return length;    
     }
     
     public boolean isFoodFound(){
        if(foodPosn<food.length && x==food[foodPosn][0] && y==food[foodPosn][1]){
           foodPosn++;   
           return true; 
         }
            return false; 
         }
 
 
 public int getPositionHash(){
        return (23*x+37*y);
 }
}