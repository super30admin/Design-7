// Snake game (https://leetcode.com/problems/design-snake-game/)


// Time Complexity : O(1)
// Space Complexity : O(w*h + f)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
// we use a map to store the locations the snake is except for the tail
// to check if it will eat itself
// we store the snake's movement in a doubly linkedlist to keep track of head and tail
// for every move, we check if it is going out of bound, eating itself, eating food 
// or the move is a normal move

class SnakeGame {
    LinkedList<int[]>snake;
    int[] head;
    boolean[][]visited;
    int w; int h; int[][] foodList; int foodIndex;
    public SnakeGame(int width, int height, int[][] food) {
        this.foodList=food;
        this.w =width;
        this.h= height;
        this.visited = new boolean[h][w];
        this.snake= new LinkedList<>();
        this.head= new int[]{0,0};
        this.snake.addFirst(this.head);
    }
    
    public int move(String direction) {
        if(direction.equals("U")) head[0] -= 1;
        else if(direction.equals("D")) head[0] += 1;
        else if(direction.equals("R")) head[1] += 1;
        else head[1] -= 1;
        //out of bound
        if(head[0]<0 || head[1]<0 || head[0]==h || head[1]==w) return -1;
        //hits itself
        if(visited[head[0]][head[1]]) return -1;
        //eats food
        if(foodIndex < foodList.length){
            int [] foodCell = foodList[foodIndex];
            if(foodCell[0]==head[0] && foodCell[1]==head[1]){
                foodIndex++;
                visited[head[0]][head[1]] = true;
                snake.addFirst(new int[] {head[0],head[1]});
                return snake.size()-1;
            }
        }
        //normal move
        snake.addFirst(new int[]{head[0],head[1]});
        visited[head[0]][head[1]] = true;
        snake.removeLast(); //move the snake by one stepm ahead
        int [] toRemove = snake.peekLast();
        visited[toRemove[0]][toRemove[1]] = false;
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */