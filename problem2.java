//TC:O(m*n)
//SC:O(m*n)

class SnakeGame {
    int width;
    int height;
    LinkedList<int[]> snakebody;
    LinkedList<int[]> fooditems;
    int[] snakehead;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakebody = new LinkedList<>();
        fooditems = new LinkedList<>(Arrays.asList(food));
        snakehead = new int[]{0, 0};
        snakebody.addLast(snakehead);
    }
    
    public int move(String direction) {
        if(direction.equals("U")){
            snakehead[0]--;
        }else if(direction.equals("D")){
            snakehead[0]++;
        }else if(direction.equals("L")){
            snakehead[1]--;
        }else if(direction.equals("R")){
            snakehead[1]++;
        }
        
        if(snakehead[0] <0 || snakehead[0] == height || snakehead[1] < 0 || snakehead[1] == width){
            return -1;
        }
        
        for(int i = 1; i<snakebody.size(); i++){
            int[] curr = snakebody.get(i);
            if(curr[0] == snakehead[0] && curr[1] == snakehead[1]) {
                return -1;
            }
        }
        
        if(fooditems.size() > 0){
           // for(int i = 0; i < fooditems.size(); i++){
                int[] fooditem = fooditems.get(0);
                if(fooditem[0] == snakehead[0] && fooditem[1] == snakehead[1]){
                    snakebody.addLast(new int[] {snakehead[0], snakehead[1]});
                    fooditems.removeFirst();
                    return snakebody.size() - 1;
                }
           // }
        }
        snakebody.addLast(new int[] {snakehead[0], snakehead[1]});
        snakebody.removeFirst();
        return snakebody.size() -1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
