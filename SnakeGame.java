// This solution uses linkedlist to track the exact path of the snake
// We use a visited snakepath to check the body block of the snake
// At any movement if snake goes out of bounds or into the path, we return -1
// If at any point it enters a block without food we remove tail and add head of the linkedlist to track moving without food
// Otherwise we just add head and return the existing size
// One catch here is not maintaining the tail in the visited array to handle scenario of moving to tails position
class SnakeGame {
    int width;
    int height;
    int[][] food;
    LinkedList<int[]> path;
    boolean[][] snakePath;
    int foodIndex = 0;

    public SnakeGame(int width, int height, int[][] food) {
        this.width=width;
        this.height=height;
        this.food=food;
        snakePath=new boolean[height][width];
        for(int i=0;i<height;i++) Arrays.fill(snakePath[i], false);
        path=new LinkedList();
        path.add(new int[]{0,0});
    }
    
    public int move(String direction) {
        
        int headRowIndex = path.getFirst()[0];
        int headColIndex = path.getFirst()[1];

        if(direction.equals("R")) {
            headColIndex++;
        } else if(direction.equals("D")) {
            headRowIndex++;
        } else if(direction.equals("L")) {
            headColIndex--;
        } else if(direction.equals("U")) {
            headRowIndex--;
        }

        if(headColIndex<0 || headColIndex==width || headRowIndex<0 || headRowIndex==height || snakePath[headRowIndex][headColIndex]) {          
            return -1;
        }


        if(foodIndex<food.length) {
            if(food[foodIndex][0]==headRowIndex && food[foodIndex][1]==headColIndex) {
            path.addFirst(new int[]{headRowIndex, headColIndex});
            foodIndex++;
            snakePath[headRowIndex][headColIndex] = true;
            return path.size()-1;
            }
        }

        path.addFirst(new int[]{headRowIndex, headColIndex});
        snakePath[headRowIndex][headColIndex] = true;
        path.removeLast();
        int[] last = path.getLast();
        snakePath[last[0]][last[1]] = false;
        return path.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
